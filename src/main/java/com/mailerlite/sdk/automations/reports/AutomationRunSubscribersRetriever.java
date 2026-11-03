/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.reports;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.util.CursorPaginatedRequest;

/**
 * Lists the subscribers who reached a step or entered through a trigger.
 */
public class AutomationRunSubscribersRetriever extends CursorPaginatedRequest<AutomationRunSubscribersRetriever> {

	/** The api object reference. */
	private MailerLite apiObjectReference;
	
	/** The endpoint. */
	private String endpoint;
	
	/**
	 * Instantiates a new automation run subscribers retriever.
	 *
	 * @param apiRef the api ref
	 * @param forEndpoint the endpoint
	 */
	public AutomationRunSubscribersRetriever(MailerLite apiRef, String forEndpoint)
	{
		apiObjectReference = apiRef;
		endpoint = forEndpoint;
	}
	
	/**
	 * Status.
	 *
	 * @param status "completed" or "queued"
	 * @return the automation run subscribers retriever
	 */
	public AutomationRunSubscribersRetriever status(String status)
	{
		this.addQueryParameter("status", status);
		
		return this;
	}
	
	/**
	 * Sets the sort order.
	 *
	 * @param sort the sort
	 * @return the automation run subscribers retriever
	 */
	public AutomationRunSubscribersRetriever sort(String sort)
	{
		this.addQueryParameter("sort", sort);
		
		return this;
	}
	
	/**
	 * Adds a filter, for example "search", "date_from", "date_to" or "id".
	 *
	 * @param name the name
	 * @param value the value
	 * @return the automation run subscribers retriever
	 */
	public AutomationRunSubscribersRetriever filter(String name, String value)
	{
		this.addQueryParameter("filter[".concat(name).concat("]"), value);
		
		return this;
	}
	
	/**
	 * Gets the subscribers.
	 *
	 * @return the run subscriber list
	 * @throws MailerLiteException the mailer lite exception
	 */
	public RunSubscriberList get() throws MailerLiteException
	{
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		return api.getRequest(endpoint.concat(this.getQueryParameters()), RunSubscriberList.class);
	}
	
	/**
	 * Gets the single instance of AutomationRunSubscribersRetriever.
	 *
	 * @return single instance of AutomationRunSubscribersRetriever
	 */
	@Override
	protected AutomationRunSubscribersRetriever getInstance() {
		return this;
	}
}
