/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.groups;

import java.util.Map;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.util.JsonBody;

/**
 * Imports a list of subscribers into a group.
 */
public class GroupImportBuilder {

	/** The api object reference. */
	private MailerLite apiObjectReference;
	
	/** The group id. */
	private String groupId;
	
	/** The builder body. */
	private GroupImportBody builderBody = new GroupImportBody();
	
	/**
	 * Instantiates a new group import builder.
	 *
	 * @param apiRef the api ref
	 * @param forGroupId the group id
	 */
	public GroupImportBuilder(MailerLite apiRef, String forGroupId)
	{
		apiObjectReference = apiRef;
		groupId = forGroupId;
	}
	
	/**
	 * Clear.
	 */
	public void clear()
	{
		builderBody = new GroupImportBody();
	}
	
	/**
	 * Adds a subscriber to import.
	 *
	 * @param email the email
	 * @return the group import builder
	 */
	public GroupImportBuilder addSubscriber(String email)
	{
		return addSubscriber(email, null, null);
	}
	
	/**
	 * Adds a subscriber to import.
	 *
	 * @param email the email
	 * @param name the name, can be null
	 * @param fields the custom field values keyed by field key, can be null
	 * @return the group import builder
	 */
	public GroupImportBuilder addSubscriber(String email, String name, Map<String, Object> fields)
	{
		GroupImportSubscriber subscriber = new GroupImportSubscriber();
		subscriber.email = email;
		subscriber.name = name;
		subscriber.fields = fields;
		
		builderBody.subscribers.add(subscriber);
		return this;
	}
	
	/**
	 * Reactivates unsubscribed subscribers in the list.
	 *
	 * @param resubscribe the resubscribe
	 * @return the group import builder
	 */
	public GroupImportBuilder resubscribe(Boolean resubscribe)
	{
		builderBody.resubscribe = resubscribe;
		return this;
	}
	
	/**
	 * Sets whether imported subscribers enter automations triggered by joining the group.
	 *
	 * @param triggerAutomations the trigger automations
	 * @return the group import builder
	 */
	public GroupImportBuilder triggerAutomations(Boolean triggerAutomations)
	{
		builderBody.triggerAutomations = triggerAutomations;
		return this;
	}
	
	/**
	 * Starts the import.
	 *
	 * @return the group subscriber import
	 * @throws MailerLiteException the mailer lite exception
	 */
	public GroupSubscriberImport start() throws MailerLiteException
	{
		MailerLiteApi api = new MailerLiteApi();
        api.setToken(apiObjectReference.getToken());
        
        String json = JsonBody.toJson(builderBody);
        
        GroupSubscriberImport response = api.postRequest("/groups/".concat(groupId).concat("/import-subscribers"), json, GroupSubscriberImport.class);
        
        if (response.subscriberImport != null) {
        	response.subscriberImport.parseDates();
        }
        
        return response;
	}
}
