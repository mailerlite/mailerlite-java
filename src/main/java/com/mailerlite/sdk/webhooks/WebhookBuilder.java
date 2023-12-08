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

public class WebhookBuilder {

	private WebhookBuilderBody builderBody = new WebhookBuilderBody();
	
	private MailerLite apiObjectReference;
	
	public WebhookBuilder(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}
	
	
	public WebhookBuilder clear()
	{
		builderBody = new WebhookBuilderBody();
		
		return this;
	}
	
	public WebhookBuilder addEvent(String event)
	{
		builderBody.events.add(event);
		return this;
	}
	
	public WebhookBuilder name(String name)
	{
		builderBody.name = name;
		return this;
	}
	
	public WebhookBuilder enabled(Boolean enabled)
	{
		builderBody.enabled = enabled;
		return this;
	}
	
	public WebhookBuilder url(String url)
	{
		builderBody.url = url;
		return this;
	}
	
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
	
	
	public SingleWebhook update(String webhookId) throws MailerLiteException
	{
		MailerLiteApi api = new MailerLiteApi();
        api.setToken(apiObjectReference.getToken());
        
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();
       
        String json = gson.toJson(builderBody);
        
        SingleWebhook webhook = api.postRequest("/webhooks/".concat(webhookId), json, SingleWebhook.class);
        
        webhook.webhook.parseDates();
        
        return webhook;
	}
}
