package com.mailerlite.sdk.webhooks;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.MailerLiteResponse;
import com.mailerlite.sdk.exceptions.MailerLiteException;

public class Webhooks {
	
	private MailerLite apiObjectReference;
	
	public Webhooks(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}
	
	public WebhookBuilder builder()
	{
		return new WebhookBuilder(apiObjectReference);
	}
	
	public WebhookRetriever retriever()
	{
		return new WebhookRetriever(apiObjectReference);
	}
	
	public MailerLiteResponse delete(String webhookId) throws MailerLiteException
	{
		String endpoint = "/webhooks/".concat(webhookId);
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		MailerLiteResponse response = api.deleteRequest(endpoint, MailerLiteResponse.class);
		
		return response;
	}
}
