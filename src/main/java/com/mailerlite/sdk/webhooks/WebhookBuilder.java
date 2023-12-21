/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.webhooks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.util.JsonSerializationDeserializationStrategy;

/**
 * The Class WebhookBuilder.
 */
public class WebhookBuilder {

	/** The builder body. */
	private WebhookBuilderBody builderBody = new WebhookBuilderBody();
	
	/** The api object reference. */
	private MailerLite apiObjectReference;
	
	/**
	 * Instantiates a new webhook builder.
	 *
	 * @param apiRef the api ref
	 */
	public WebhookBuilder(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}
	
	
	/**
	 * Clear.
	 *
	 * @return the webhook builder
	 */
	public WebhookBuilder clear()
	{
		builderBody = new WebhookBuilderBody();
		
		return this;
	}
	
	/**
	 * Adds the event.
	 *
	 * @param event the event
	 * @return the webhook builder
	 */
	public WebhookBuilder addEvent(String event)
	{
		builderBody.events.add(event);
		return this;
	}
	
	/**
	 * Name.
	 *
	 * @param name the name
	 * @return the webhook builder
	 */
	public WebhookBuilder name(String name)
	{
		builderBody.name = name;
		return this;
	}
	
	/**
	 * Enabled.
	 *
	 * @param enabled the enabled
	 * @return the webhook builder
	 */
	public WebhookBuilder enabled(Boolean enabled)
	{
		builderBody.enabled = enabled;
		return this;
	}
	
	/**
	 * Url.
	 *
	 * @param url the url
	 * @return the webhook builder
	 */
	public WebhookBuilder url(String url)
	{
		builderBody.url = url;
		return this;
	}
	
	/**
	 * Creates the.
	 *
	 * @return the single webhook
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SingleWebhook create() throws MailerLiteException
	{
		MailerLiteApi api = new MailerLiteApi();
        api.setToken(apiObjectReference.getToken());
        
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();
       
        String json = gson.toJson(builderBody);
        
        SingleWebhook webhook = api.postRequest("/webhooks", json, SingleWebhook.class);
        
        webhook.webhook.parseDates();
        
        return webhook;
	}
	
	
	/**
	 * Update.
	 *
	 * @param webhookId the webhook id
	 * @return the single webhook
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SingleWebhook update(String webhookId) throws MailerLiteException
	{
		MailerLiteApi api = new MailerLiteApi();
        api.setToken(apiObjectReference.getToken());
        
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();
       
        String json = gson.toJson(builderBody);
        
        SingleWebhook webhook = api.putRequest("/webhooks/".concat(webhookId), json, SingleWebhook.class);
        
        webhook.webhook.parseDates();
        
        return webhook;
	}
}
