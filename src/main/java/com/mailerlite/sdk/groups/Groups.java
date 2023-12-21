/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.groups;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.MailerLiteResponse;
import com.mailerlite.sdk.exceptions.MailerLiteException;

/**
 * The Class Groups.
 */
public class Groups {
	
	/** The api object reference. */
	private MailerLite apiObjectReference;
	
	/**
	 * Instantiates a new groups.
	 *
	 * @param apiRef the api ref
	 */
	public Groups(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}
	
	/**
	 * Retriever.
	 *
	 * @return the group retriever
	 */
	public GroupRetriever retriever()
	{
		return new GroupRetriever(apiObjectReference);
	}

	/**
	 * Builder.
	 *
	 * @return the group builder
	 */
	public GroupBuilder builder()
	{
		return new GroupBuilder(apiObjectReference);
	}
	
	/**
	 * Subscribers.
	 *
	 * @param groupId the group id
	 * @return the group subscribers
	 */
	public GroupSubscribers subscribers(String groupId)
	{
		return new GroupSubscribers(apiObjectReference, groupId);
	}
	
	/**
	 * Delete.
	 *
	 * @param groupId the group id
	 * @return the mailer lite response
	 * @throws MailerLiteException the mailer lite exception
	 */
	public MailerLiteResponse delete(String groupId) throws MailerLiteException
	{
		String endpoint = "/groups/".concat(groupId);
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		MailerLiteResponse response = api.deleteRequest(endpoint, MailerLiteResponse.class);
		
		return response;
	}
}
