/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.mailerlite.sdk.MailerLiteResponse;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.vcr.VcrRecorder;
import com.mailerlite.sdk.webhooks.SingleWebhook;
import com.mailerlite.sdk.webhooks.Webhook;
import com.mailerlite.sdk.webhooks.WebhooksList;

public class WebhooksTest extends TestBase {

	@BeforeEach
	public void setupEach(TestInfo info) throws IOException
	{
		VcrRecorder.useRecording("WebhooksTest_" + info.getDisplayName());
	}
	
	@AfterEach
	public void afterEach() throws IOException
	{
		VcrRecorder.stopRecording();
	}

	@Test
	public void TestGetWebhooks()
	{
		
		try {
			
			WebhooksList webhooks = this.getMailerLite().webhooks().retriever().get();
			
			for (Webhook w : webhooks.webhooks) {
				
				System.out.println(w.id);
				System.out.println(w.name);
				System.out.println(w.url);
			}
			
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
	
	
	@Test
	public void TestGetSingleWebhook()
	{
		
		try {
			
			WebhooksList webhooks = this.getMailerLite().webhooks().retriever().get();
			
			SingleWebhook webhook = this.getMailerLite().webhooks().retriever().getSingle(webhooks.webhooks[0].id);
		
			System.out.println(webhook.webhook.id);
			System.out.println(webhook.webhook.name);
			System.out.println(webhook.webhook.url);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void TestCreateWebhook()
	{
		try {
			
			SingleWebhook webhook = this.getMailerLite()
					.webhooks()
					.builder()
					.addEvent("subscriber.added_to_group")
					.name("Add to group")
					.url("https://example.com")
					.enabled(true)
					.create();
			
			System.out.println(webhook.webhook.id);
			System.out.println(webhook.webhook.name);
			System.out.println(webhook.webhook.url);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
	
	
	@Test
	public void TestUpdateWebhook()
	{
		try {
			
			WebhooksList webhooks = this.getMailerLite().webhooks().retriever().get();
			
			SingleWebhook webhook = this.getMailerLite().webhooks().builder().enabled(false).update(webhooks.webhooks[0].id);
			
			System.out.println(webhook.webhook.id);
			System.out.println(webhook.webhook.name);
			System.out.println(webhook.webhook.enabled);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
	
	
	@Test
	public void TestDeleteWebhook()
	{
		try {
			
			WebhooksList webhooks = this.getMailerLite().webhooks().retriever().get();
			
			MailerLiteResponse deleteResponse = this.getMailerLite().webhooks().delete(webhooks.webhooks[0].id);
			
			System.out.println(deleteResponse.responseStatusCode);
			assertEquals(204, deleteResponse.responseStatusCode);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
}
