/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.susbcribers;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.MailerLiteResponse;
import com.mailerlite.sdk.exceptions.MailerLiteException;

/**
 * The Class Subscribers.
 */
public class Subscribers {

	/** The api object reference. */
	private MailerLite apiObjectReference;
	
	/**
	 * Instantiates a new subscribers.
	 *
	 * @param apiRef the api ref
	 */
	public Subscribers(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}
	
	/**
	 * Retriever.
	 *
	 * @return the subscriber retriever
	 */
	public SubscriberRetriever retriever()
	{
		return new SubscriberRetriever(apiObjectReference);
	}
	
	/**
	 * Builder.
	 *
	 * @return the subscriber builder
	 */
	public SubscriberBuilder builder()
	{
		return new SubscriberBuilder(apiObjectReference);
	}
	
	/**
	 * Delete.
	 *
	 * @param subscriberId the subscriber id
	 * @return the mailer lite response
	 * @throws MailerLiteException the mailer lite exception
	 */
	public MailerLiteResponse delete(String subscriberId) throws MailerLiteException
	{
		String endpoint = "/subscribers/".concat(subscriberId);
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		MailerLiteResponse response = api.deleteRequest(endpoint, MailerLiteResponse.class);
		
		return response;
	}
	
	/**
	 * Imports.
	 *
	 * @return the subscriber imports retriever
	 */
	public SubscriberImportsRetriever imports()
	{
		return new SubscriberImportsRetriever(apiObjectReference);
	}
}
