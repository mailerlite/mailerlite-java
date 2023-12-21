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

/**
 * The Class SubscriberBuilder.
 */
public class SubscriberBuilder {

	/** The api object reference. */
	private MailerLite apiObjectReference;
	
	/** The builder body. */
	private SubscriberBuilderBody builderBody = new SubscriberBuilderBody();
	
	/**
	 * Instantiates a new subscriber builder.
	 *
	 * @param apiRef the api ref
	 */
	public SubscriberBuilder(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}
	
	
	/**
	 * Creates the.
	 *
	 * @return the single subscriber
	 * @throws MailerLiteException the mailer lite exception
	 */
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
	
	/**
	 * Update.
	 *
	 * @param id the id
	 * @return the single subscriber
	 * @throws MailerLiteException the mailer lite exception
	 */
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
	
	
	/**
	 * Clear.
	 */
	public void clear()
	{
		builderBody = new SubscriberBuilderBody();
	}
	
	/**
	 * Email.
	 *
	 * @param email the email
	 * @return the subscriber builder
	 */
	public SubscriberBuilder email(String email)
	{
		builderBody.email = email;
		return this;
	}
	
	/**
	 * Adds the field.
	 *
	 * @param fieldName the field name
	 * @param fieldValue the field value
	 * @return the subscriber builder
	 */
	public SubscriberBuilder addField(String fieldName, Object fieldValue)
	{
		if (builderBody.fields == null) {
			builderBody.fields = new HashMap<String, Object>();
		}
		builderBody.fields.put(fieldName, fieldValue);
		return this;
	}
	
	/**
	 * Adds the group.
	 *
	 * @param groupId the group id
	 * @return the subscriber builder
	 */
	public SubscriberBuilder addGroup(String groupId)
	{
		if (builderBody.groups == null) {
			builderBody.groups = new ArrayList<String>();
		}
		builderBody.groups.add(groupId);
		return this;
	}
	
	/**
	 * Status.
	 *
	 * @param status the status
	 * @return the subscriber builder
	 */
	public SubscriberBuilder status(String status)
	{
		builderBody.status = status;
		return this;
	}
	
	/**
	 * Subscribed at.
	 *
	 * @param subscribedAt the subscribed at
	 * @return the subscriber builder
	 */
	public SubscriberBuilder subscribedAt(Date subscribedAt)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		builderBody.subscribedAt = formatter.format(subscribedAt);
		return this;
	}
	
	/**
	 * Ip address.
	 *
	 * @param ipAddress the ip address
	 * @return the subscriber builder
	 */
	public SubscriberBuilder ipAddress(String ipAddress)
	{
		builderBody.ipAddress = ipAddress;
		return this;
	}
	
	/**
	 * Opted in at.
	 *
	 * @param optedInAt the opted in at
	 * @return the subscriber builder
	 */
	public SubscriberBuilder optedInAt(Date optedInAt)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		builderBody.optedInAt = formatter.format(optedInAt);
		return this;
	}
	
	/**
	 * Opt in ip.
	 *
	 * @param optInIp the opt in ip
	 * @return the subscriber builder
	 */
	public SubscriberBuilder optInIp(String optInIp)
	{
		builderBody.optinIp = optInIp;
		return this;
	}
	
	/**
	 * Unsubscribed at.
	 *
	 * @param unsubscribedAt the unsubscribed at
	 * @return the subscriber builder
	 */
	public SubscriberBuilder unsubscribedAt(Date unsubscribedAt)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		builderBody.unsubscribedAt = formatter.format(unsubscribedAt);
		return this;
	}
}
