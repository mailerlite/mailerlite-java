/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.forms;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.susbcribers.SubscribersList;
import com.mailerlite.sdk.util.PaginatedRequest;

/**
 * The Class SubscribersRetriever.
 */
public class SubscribersRetriever  extends PaginatedRequest<SubscribersRetriever> {

	/** The api object reference. */
	private MailerLite apiObjectReference;
	
	/**
	 * Instantiates a new subscribers retriever.
	 *
	 * @param apiRef the api ref
	 */
	public SubscribersRetriever(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}
	
	/**
	 * Status filter.
	 *
	 * @param statusFilter the status filter
	 * @return the subscribers retriever
	 */
	public SubscribersRetriever statusFilter(String statusFilter)
	{
		this.addQueryParameter("filter[status]", statusFilter);
		
		return this;
	}
	
	/**
	 * Cursor.
	 *
	 * @param cursor the cursor
	 * @return the subscribers retriever
	 */
	public SubscribersRetriever cursor(String cursor)
	{
		this.addQueryParameter("cursor", cursor);
		return this;
	}
	
	/**
	 * Limit.
	 *
	 * @param limit the limit
	 * @return the subscribers retriever
	 */
	public SubscribersRetriever limit(int limit)
	{
		this.addQueryParameter("limit", String.valueOf(limit));
		return this;
	}
	
	/**
	 * Gets the.
	 *
	 * @param formId the form id
	 * @return the subscribers list
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SubscribersList get(String formId) throws MailerLiteException
	{
		String endpoint = "/forms/".concat(formId).concat("/subscribers").concat(this.getQueryParameters());
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		SubscribersList list = api.getRequest(endpoint, SubscribersList.class);
		
		list.postDeserialize();
		
		return list;
	}
	

	/**
	 * Gets the single instance of SubscribersRetriever.
	 *
	 * @return single instance of SubscribersRetriever
	 */
	@Override
	protected SubscribersRetriever getInstance() {
		return this;
	}
}
