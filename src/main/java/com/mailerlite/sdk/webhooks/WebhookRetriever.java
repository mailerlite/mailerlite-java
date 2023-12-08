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
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.util.PaginatedRequest;

public class WebhookRetriever extends PaginatedRequest<WebhookRetriever> {

private MailerLite apiObjectReference;
	
	public WebhookRetriever(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}
	
	public WebhookRetriever nameFilter(String nameFilter)
	{
		this.addQueryParameter("filter[name]", nameFilter);
		
		return this;
	}
	
	public WebhookRetriever sort(String sort)
	{
		this.addQueryParameter("sort", sort);
		return this;
	}
	
	public WebhooksList get() throws MailerLiteException
	{
		String endpoint = "/webhooks".concat(this.getQueryParameters());
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		WebhooksList list = api.getRequest(endpoint, WebhooksList.class);
		
		list.postDeserialize();
		
		return list;
	}
	
	public SingleWebhook getSingle(String webhookId) throws MailerLiteException
	{
		String endpoint = "/webhooks/".concat(webhookId);
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		SingleWebhook webhook = api.getRequest(endpoint, SingleWebhook.class);
		
		webhook.webhook.parseDates();
		
		return webhook;
	}
	

	@Override
	protected WebhookRetriever getInstance() {
		return this;
	}
}
