/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.susbcribers;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.util.JsonSerializationDeserializationStrategy;

public class SubscriberBuilder {

	private MailerLite apiObjectReference;
	private SubscriberBuilderBody builderBody = new SubscriberBuilderBody();
	
	public SubscriberBuilder(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}
	
	
	public SingleSubscriber create() throws MailerLiteException
	{
		MailerLiteApi api = new MailerLiteApi();
        api.setToken(apiObjectReference.getToken());
        
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();
        
        String json = gson.toJson(builderBody);
        
        SingleSubscriber subscriber = api.postRequest("/subscribers", json, SingleSubscriber.class);
        
        subscriber.subscriber.parseDates();
        
        return subscriber;
	}
	
	public SingleSubscriber update(String id) throws MailerLiteException
	{
		MailerLiteApi api = new MailerLiteApi();
        api.setToken(apiObjectReference.getToken());
        
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();
        
        String json = gson.toJson(builderBody);
        
        SingleSubscriber subscriber = api.putRequest("/subscribers/".concat(id), json, SingleSubscriber.class);
        
        subscriber.subscriber.parseDates();
        
        return subscriber;
	}
	
	
	public void clear()
	{
		builderBody = new SubscriberBuilderBody();
	}
	
	public SubscriberBuilder email(String email)
	{
		builderBody.email = email;
		return this;
	}
	
	public SubscriberBuilder addField(String fieldName, Object fieldValue)
	{
		if (builderBody.fields == null) {
			builderBody.fields = new HashMap<String, Object>();
		}
		builderBody.fields.put(fieldName, fieldValue);
		return this;
	}
	
	public SubscriberBuilder addGroup(String groupId)
	{
		if (builderBody.groups == null) {
			builderBody.groups = new ArrayList<String>();
		}
		builderBody.groups.add(groupId);
		return this;
	}
	
	public SubscriberBuilder status(String status)
	{
		builderBody.status = status;
		return this;
	}
	
	public SubscriberBuilder subscribedAt(Date subscribedAt)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		builderBody.subscribedAt = formatter.format(subscribedAt);
		return this;
	}
	
	public SubscriberBuilder ipAddress(String ipAddress)
	{
		builderBody.ipAddress = ipAddress;
		return this;
	}
	
	public SubscriberBuilder optedInAt(Date optedInAt)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		builderBody.optedInAt = formatter.format(optedInAt);
		return this;
	}
	
	public SubscriberBuilder optInIp(String optInIp)
	{
		builderBody.optinIp = optInIp;
		return this;
	}
	
	public SubscriberBuilder unsubscribedAt(Date unsubscribedAt)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		builderBody.unsubscribedAt = formatter.format(unsubscribedAt);
		return this;
	}
}
