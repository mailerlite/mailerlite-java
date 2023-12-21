/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.groups;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.util.JsonSerializationDeserializationStrategy;

/**
 * The Class GroupBuilder.
 */
public class GroupBuilder {

	/** The api object reference. */
	private MailerLite apiObjectReference;
	
	/**
	 * Instantiates a new group builder.
	 *
	 * @param apiRef the api ref
	 */
	public GroupBuilder(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}
	
	/**
	 * Creates the.
	 *
	 * @param groupName the group name
	 * @return the single group
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SingleGroup create(String groupName) throws MailerLiteException
	{
		MailerLiteApi api = new MailerLiteApi();
        api.setToken(apiObjectReference.getToken());
        
        NameCreatorBody requestBody = new NameCreatorBody();
        requestBody.name = groupName;
        
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();
       
        String json = gson.toJson(requestBody);
        
        SingleGroup group = api.postRequest("/groups", json, SingleGroup.class);
        
        group.group.parseDates();
        
        return group;
	}
	
	/**
	 * Update.
	 *
	 * @param groupId the group id
	 * @param newGroupName the new group name
	 * @return the single group
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SingleGroup update(String groupId, String newGroupName) throws MailerLiteException
	{
		MailerLiteApi api = new MailerLiteApi();
        api.setToken(apiObjectReference.getToken());
        
        NameCreatorBody requestBody = new NameCreatorBody();
        requestBody.name = newGroupName;
        
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();
       
        String json = gson.toJson(requestBody);
        
        SingleGroup group = api.putRequest("/groups/".concat(groupId), json, SingleGroup.class);
        
        group.group.parseDates();
        
        return group;
	}
}
