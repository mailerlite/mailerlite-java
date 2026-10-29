/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.campaigns.SingleCampaign;
import com.mailerlite.sdk.emails.Email;
import com.mailerlite.sdk.emails.EmailBase;
import com.mailerlite.sdk.emails.EmailEditors;
import com.mailerlite.sdk.support.Fixtures;
import com.mailerlite.sdk.support.RecordingHttpClient;

/**
 * Tests the campaign create with the simple editor, with the recording HTTP client.
 */
public class CampaignSimpleEditorTest {

	/** The fake HTTP client. */
	private RecordingHttpClient client;

	/** The MailerLite object. */
	private MailerLite ml;

	/**
	 * Sets the fake HTTP client.
	 */
	@BeforeEach
	public void setupEach()
	{
		client = RecordingHttpClient.install();
		client.respond(201, Fixtures.read("campaign_simple_editor.json"));

		ml = new MailerLite();
		ml.setToken("test-token");
	}

	/**
	 * Removes the fake HTTP client.
	 */
	@AfterEach
	public void afterEach()
	{
		RecordingHttpClient.uninstall();
	}

	/**
	 * Makes an email with a sender and a subject.
	 *
	 * @return the email
	 */
	private EmailBase email()
	{
		EmailBase email = new EmailBase();
		email.from = "sender@example.com";
		email.fromName = "Sender";
		email.subject = "Hello";

		return email;
	}

	/**
	 * A create without the new fields sends the same body as before.
	 *
	 * @throws Exception if the request fails
	 */
	@Test
	public void createWithoutEditorSendsTheSameBodyAsBefore() throws Exception
	{
		EmailBase email = email();
		email.content = "<div>Hello</div>";

		ml.campaigns().builder().name("Newsletter").type("regular").email(email).create();

		assertEquals("POST", client.lastRequest().method);
		assertEquals("https://connect.mailerlite.com/api/campaigns", client.lastRequest().uri.toString());
		assertEquals("{\"name\":\"Newsletter\",\"language_id\":4,\"type\":\"regular\",\"emails\":[{\"from\":\"sender@example.com\","
				+ "\"from_name\":\"Sender\",\"content\":\"\\u003cdiv\\u003eHello\\u003c/div\\u003e\",\"subject\":\"Hello\"}],"
				+ "\"groups\":[],\"segments\":[],\"ab_settings\":{},\"resend_settings\":{}}", client.lastRequest().bodyString());
	}

	/**
	 * A create with the simple editor sends editor, template_id and reply_to, and no content.
	 * The response has the email of the simple editor.
	 *
	 * @throws Exception if the request fails
	 */
	@Test
	public void createWithSimpleEditor() throws Exception
	{
		EmailBase email = email();
		email.replyTo = "reply@example.com";
		email.editor = EmailEditors.SIMPLE;
		email.templateId = "77";

		SingleCampaign response = ml.campaigns().builder().name("Simple editor campaign").type("regular").email(email).create();

		assertEquals("{\"name\":\"Simple editor campaign\",\"language_id\":4,\"type\":\"regular\",\"emails\":[{"
				+ "\"from\":\"sender@example.com\",\"from_name\":\"Sender\",\"subject\":\"Hello\","
				+ "\"reply_to\":\"reply@example.com\",\"editor\":\"simple\",\"template_id\":\"77\"}],"
				+ "\"groups\":[],\"segments\":[],\"ab_settings\":{},\"resend_settings\":{}}", client.lastRequest().bodyString());

		Email created = response.campaign.emails[0];
		assertEquals("123", created.id);
		assertEquals("builder_inline_email", created.type);
		assertEquals("reply@example.com", created.replyTo);
		assertEquals("456", response.campaign.id);
	}
}
