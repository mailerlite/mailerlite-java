/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.emails.EmailBlock;
import com.mailerlite.sdk.emails.EmailDocument;
import com.mailerlite.sdk.emails.EmailSettings;
import com.mailerlite.sdk.emails.EmailSocialLink;
import com.mailerlite.sdk.emails.Emails;
import com.mailerlite.sdk.emails.SingleEmailDocument;
import com.mailerlite.sdk.exceptions.EmptyIdException;
import com.mailerlite.sdk.exceptions.InvalidIdException;
import com.mailerlite.sdk.support.Fixtures;
import com.mailerlite.sdk.support.RecordingHttpClient;
import com.mailerlite.sdk.support.RecordingHttpClient.RecordedRequest;

/**
 * Tests the email endpoints with the recording HTTP client.
 */
public class EmailsTest {

	/** The base URL of the API. */
	static final String BASE = "https://connect.mailerlite.com/api";

	/** The fake HTTP client. */
	private RecordingHttpClient client;

	/** The email service. */
	private Emails emails;

	/**
	 * Sets the fake HTTP client.
	 */
	@BeforeEach
	public void setupEach()
	{
		client = RecordingHttpClient.install();

		MailerLite ml = new MailerLite();
		ml.setToken("test-token");
		emails = ml.emails();
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
	 * Asserts the one request that the client got: the method, the full URL, all the headers and the raw body.
	 *
	 * @param client the fake HTTP client
	 * @param method the HTTP method
	 * @param path the path and the query string, after the base URL
	 * @param body the exact raw body, or null for a request without a body publisher
	 * @param idempotencyKey the expected Idempotency-Key header, or null for no header
	 * @return the request
	 */
	static RecordedRequest assertRequest(RecordingHttpClient client, String method, String path, String body,
			String idempotencyKey)
	{
		assertEquals(1, client.requests().size(), "number of requests");

		RecordedRequest request = client.lastRequest();
		assertEquals(method, request.method);
		assertEquals(BASE + path, request.uri.toString());

		Set<String> expectedHeaders = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
		expectedHeaders.addAll(Arrays.asList("Content-Type", "Accept", "Authorization"));

		if (idempotencyKey != null) {
			expectedHeaders.add("Idempotency-Key");
		}

		Set<String> actualHeaders = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
		actualHeaders.addAll(request.headers.map().keySet());
		assertEquals(expectedHeaders, actualHeaders);

		assertEquals("application/json", request.header("Content-Type"));
		assertEquals("application/json", request.header("Accept"));
		assertEquals("Bearer test-token", request.header("Authorization"));
		assertEquals(idempotencyKey, request.header("Idempotency-Key"));

		if (body == null) {
			assertFalse(request.hasBodyPublisher, "the request must have no body");
		} else {
			assertEquals(body, request.bodyString());
		}

		return request;
	}

	/**
	 * Asserts that each empty and each invalid ID throws the correct exception, and that no request is sent.
	 *
	 * @param client the fake HTTP client
	 * @param call makes the call for an ID
	 */
	static void assertRejectsIds(RecordingHttpClient client, Function<String, Executable> call)
	{
		for (String id : Arrays.asList("", null)) {
			assertThrows(EmptyIdException.class, call.apply(id), "ID " + id);
		}

		for (String id : RequestChecksTest.INVALID_IDS) {
			assertThrows(InvalidIdException.class, call.apply(id), "ID " + id);
		}

		assertTrue(client.requests().isEmpty(), "the SDK must send no request");
	}

	/**
	 * Gets a map value as a map.
	 *
	 * @param map the map
	 * @param key the key
	 * @return the value
	 */
	@SuppressWarnings("unchecked")
	static Map<String, Object> map(Map<String, Object> map, String key)
	{
		return (Map<String, Object>) map.get(key);
	}

	/**
	 * get sends GET /emails/{email} and decodes the email, the settings and the blocks.
	 *
	 * @throws Exception if the request fails
	 */
	@Test
	public void getDecodesTheEmail() throws Exception
	{
		client.respond(200, Fixtures.read("email.json"));

		SingleEmailDocument response = emails.get("123");

		assertRequest(client, "GET", "/emails/123", null, null);
		assertEquals(200, response.responseStatusCode);
		assertEquals("42", response.etag);

		EmailDocument email = response.email;
		assertEquals("123", email.id);
		assertEquals("builder_inline_email", email.type);
		assertEquals("campaigns", email.emailableType);
		assertEquals("456", email.emailableId);

		EmailSettings settings = email.settings;
		assertEquals("account", settings.brand.mode);
		assertNull(settings.brand.style);
		assertEquals("Inter, sans-serif", settings.brand.fontFamily);
		assertEquals("#ffffff", settings.brand.backgroundColor);
		assertEquals("#000000", settings.brand.textColor);
		assertEquals("#e5e7eb", settings.brand.borderColor);
		assertEquals("default", settings.layout.widthMode);
		assertEquals("center", settings.layout.alignment);
		assertEquals("#2cb191", settings.typography.link.color);
		assertEquals("Inter, sans-serif", settings.typography.heading.fontFamily);
		assertEquals("700", settings.typography.heading.fontWeight);
		assertEquals("150%", settings.typography.heading.lineHeight);
		assertEquals("0", settings.typography.heading.letterSpacing);
		assertEquals("32", settings.typography.heading.h1.size);
		assertEquals("24", settings.typography.heading.h2.size);
		assertEquals("20", settings.typography.heading.h3.size);
		assertEquals("#000000", settings.typography.heading.h3.color);
		assertEquals("400", settings.typography.paragraph.fontWeight);
		assertEquals("16", settings.typography.paragraph.p1.size);
		assertEquals("Inter, sans-serif", settings.button.fontFamily);
		assertEquals("600", settings.button.fontWeight);
		assertEquals("0", settings.button.letterSpacing);
		assertEquals("#2cb191", settings.button.backgroundColor);
		assertEquals("#ffffff", settings.button.textColor);
		assertEquals("4", settings.button.borderRadius);
		assertEquals(Boolean.FALSE, settings.footer.showPreferences);
		assertEquals("Unsubscribe", settings.footer.unsubscribeText);
		assertEquals("Update your preferences", settings.footer.preferencesText);

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> companyName = (List<Map<String, Object>>) settings.footer.companyName.editorContent.get("content");
		assertEquals("Acme", companyName.get(0).get("text"));

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> address = (List<Map<String, Object>>) settings.footer.address.editorContent.get("content");
		assertEquals("1 Main Street", address.get(0).get("text"));

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> infoText = (List<Map<String, Object>>) settings.footer.infoText.editorContent.get("content");
		assertEquals("You signed up on our website.", infoText.get(0).get("text"));

		assertEquals(1, settings.footer.socialLinks.size());
		EmailSocialLink link = settings.footer.socialLinks.get(0);
		assertEquals("https://example.com", link.url);
		assertEquals("website", link.type);
		assertEquals("Website", link.label);
		assertEquals(Long.valueOf(7), link.linkId);

		assertEquals(2, email.blocks.length);
		EmailBlock paragraph = email.blocks[0];
		assertEquals("1", paragraph.id);
		assertEquals("paragraph", paragraph.type);
		assertEquals("left", map(paragraph.editorContent, "attrs").get("textAlign"));
		assertNull(paragraph.children);
		assertNull(paragraph.placement);

		EmailBlock list = email.blocks[1];
		assertEquals("2", list.id);
		assertEquals("bulleted_list", list.type);
		assertEquals(Collections.emptyMap(), list.editorContent);
		assertEquals(1, list.children.length);
		assertEquals("3", list.children[0].id);
		assertEquals("paragraph", list.children[0].type);
	}

	/**
	 * get checks the email ID and sends no request for an invalid ID.
	 */
	@Test
	public void getRejectsInvalidIds()
	{
		assertRejectsIds(client, id -> () -> emails.get(id));
	}
}
