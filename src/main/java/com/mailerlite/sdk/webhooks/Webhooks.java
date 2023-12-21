/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.webhooks;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.MailerLiteResponse;
import com.mailerlite.sdk.exceptions.MailerLiteException;

/**
 * The Class Webhooks.
 */
public class Webhooks {
	
	/** The api object reference. */
	private MailerLite apiObjectReference;
	
	/**
	 * Instantiates a new webhooks.
	 *
	 * @param apiRef the api ref
	 */
	public Webhooks(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}
	
	/**
	 * Builder.
	 *
	 * @return the webhook builder
	 */
	public WebhookBuilder builder()
	{
		return new WebhookBuilder(apiObjectReference);
	}
	
	/**
	 * Retriever.
	 *
	 * @return the webhook retriever
	 */
	public WebhookRetriever retriever()
	{
		return new WebhookRetriever(apiObjectReference);
	}
	
	/**
	 * Delete.
	 *
	 * @param webhookId the webhook id
	 * @return the mailer lite response
	 * @throws MailerLiteException the mailer lite exception
	 */
	public MailerLiteResponse delete(String webhookId) throws MailerLiteException
	{
		String endpoint = "/webhooks/".concat(webhookId);
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		MailerLiteResponse response = api.deleteRequest(endpoint, MailerLiteResponse.class);
		
		return response;
	}
}
