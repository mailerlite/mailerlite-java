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
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.util.CursorPaginatedRequest;

/**
 * The Class SubscriberRetriever.
 */
public class SubscriberRetriever extends CursorPaginatedRequest<SubscriberRetriever> {

	/** The api object reference. */
	private MailerLite apiObjectReference;
	
	/**
	 * Instantiates a new subscriber retriever.
	 *
	 * @param apiRef the api ref
	 */
	public SubscriberRetriever(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}
	
	/**
	 * Filter.
	 *
	 * @param name the name
	 * @param value the value
	 * @return the subscriber retriever
	 */
	public SubscriberRetriever filter(String name, String value)
	{
		this.addQueryParameter("filter[".concat(name).concat("]"), value);
		
		return this;
	}
	
	/**
	 * Gets the.
	 *
	 * @return the subscribers list
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SubscribersList get() throws MailerLiteException
	{
		String endpoint = "/subscribers".concat(this.getQueryParameters());
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		SubscribersList list = api.getRequest(endpoint, SubscribersList.class);
		
		list.postDeserialize();
		
		return list;
	}
	
	/**
	 * Gets the single.
	 *
	 * @param idOrEmail the id or email
	 * @return the single
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SingleSubscriber getSingle(String idOrEmail) throws MailerLiteException
	{
		String endpoint = "/subscribers/".concat(idOrEmail);
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		SingleSubscriber subscriber = api.getRequest(endpoint, SingleSubscriber.class);
		
		subscriber.subscriber.parseDates();
		
		return subscriber;
	}
	
	/**
	 * Count.
	 *
	 * @return the subscriber count
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SubscriberCount count() throws MailerLiteException
	{
		this.limit(0);
		
		String endpoint = "/subscribers".concat(this.getQueryParameters());
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		SubscriberCount count = api.getRequest(endpoint, SubscriberCount.class);
		
		return count;
	}

	/**
	 * Gets the single instance of SubscriberRetriever.
	 *
	 * @return single instance of SubscriberRetriever
	 */
	@Override
	protected SubscriberRetriever getInstance() {
		return this;
	}
}
