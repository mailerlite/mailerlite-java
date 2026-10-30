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
 * The Class SubscriberActivityLogRetriever.
 */
public class SubscriberActivityLogRetriever extends CursorPaginatedRequest<SubscriberActivityLogRetriever> {

	/** The api object reference. */
	private MailerLite apiObjectReference;
	
	/** The subscriber id. */
	private String subscriberId;
	
	/**
	 * Instantiates a new subscriber activity log retriever.
	 *
	 * @param apiRef the api ref
	 * @param forSubscriberId the subscriber id
	 */
	public SubscriberActivityLogRetriever(MailerLite apiRef, String forSubscriberId)
	{
		apiObjectReference = apiRef;
		subscriberId = forSubscriberId;
	}
	
	/**
	 * Returns only entries of this type, for example "email_open".
	 *
	 * @param logName the log name
	 * @return the subscriber activity log retriever
	 */
	public SubscriberActivityLogRetriever logName(String logName)
	{
		this.addQueryParameter("filter[log_name]", logName);
		
		return this;
	}
	
	/**
	 * Gets the activity log of the subscriber.
	 *
	 * @return the subscriber activity log list
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SubscriberActivityLogList get() throws MailerLiteException
	{
		String endpoint = "/subscribers/".concat(subscriberId).concat("/activity-log").concat(this.getQueryParameters());
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		return api.getRequest(endpoint, SubscriberActivityLogList.class);
	}
	
	/**
	 * Gets the single instance of SubscriberActivityLogRetriever.
	 *
	 * @return single instance of SubscriberActivityLogRetriever
	 */
	@Override
	protected SubscriberActivityLogRetriever getInstance() {
		return this;
	}
}
