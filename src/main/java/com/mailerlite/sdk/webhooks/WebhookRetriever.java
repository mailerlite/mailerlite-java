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

/**
 * The Class WebhookRetriever.
 */
public class WebhookRetriever extends PaginatedRequest<WebhookRetriever> {

/** The api object reference. */
private MailerLite apiObjectReference;
	
	/**
	 * Instantiates a new webhook retriever.
	 *
	 * @param apiRef the api ref
	 */
	public WebhookRetriever(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}
	
	/**
	 * Name filter.
	 *
	 * @param nameFilter the name filter
	 * @return the webhook retriever
	 */
	public WebhookRetriever nameFilter(String nameFilter)
	{
		this.addQueryParameter("filter[name]", nameFilter);
		
		return this;
	}
	
	/**
	 * Sort.
	 *
	 * @param sort the sort
	 * @return the webhook retriever
	 */
	public WebhookRetriever sort(String sort)
	{
		this.addQueryParameter("sort", sort);
		return this;
	}
	
	/**
	 * Gets the.
	 *
	 * @return the webhooks list
	 * @throws MailerLiteException the mailer lite exception
	 */
	public WebhooksList get() throws MailerLiteException
	{
		String endpoint = "/webhooks".concat(this.getQueryParameters());
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		WebhooksList list = api.getRequest(endpoint, WebhooksList.class);
		
		list.postDeserialize();
		
		return list;
	}
	
	/**
	 * Gets the single.
	 *
	 * @param webhookId the webhook id
	 * @return the single
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SingleWebhook getSingle(String webhookId) throws MailerLiteException
	{
		String endpoint = "/webhooks/".concat(webhookId);
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		SingleWebhook webhook = api.getRequest(endpoint, SingleWebhook.class);
		
		webhook.webhook.parseDates();
		
		return webhook;
	}
	

	/**
	 * Gets the single instance of WebhookRetriever.
	 *
	 * @return single instance of WebhookRetriever
	 */
	@Override
	protected WebhookRetriever getInstance() {
		return this;
	}
}
