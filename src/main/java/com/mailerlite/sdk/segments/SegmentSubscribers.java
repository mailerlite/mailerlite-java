/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.segments;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.util.ApiRequest;

/**
 * The Class SegmentSubscribers.
 */
public class SegmentSubscribers extends ApiRequest {

	/** The api object reference. */
	private MailerLite apiObjectReference;
	
	/** The segment id. */
	private String segmentId;
	
	/**
	 * Instantiates a new segment subscribers.
	 *
	 * @param apiRef the api ref
	 * @param forSegmentId the for segment id
	 */
	public SegmentSubscribers(MailerLite apiRef, String forSegmentId)
	{
		apiObjectReference = apiRef;
		segmentId = forSegmentId;
	}
	
	/**
	 * Limit.
	 *
	 * @param limit the limit
	 * @return the segment subscribers
	 */
	public SegmentSubscribers limit(int limit)
	{
		this.addQueryParameter("limig", String.valueOf(limit));
		return this;
	}
	
	/**
	 * Status filter.
	 *
	 * @param statusFilter the status filter
	 * @return the segment subscribers
	 */
	public SegmentSubscribers statusFilter(String statusFilter)
	{
		this.addQueryParameter("filter[status]", statusFilter);
		
		return this;
	}
	
	/**
	 * After.
	 *
	 * @param subscriberId the subscriber id
	 * @return the segment subscribers
	 */
	public SegmentSubscribers after(String subscriberId)
	{
		this.addQueryParameter("after", subscriberId);
		
		return this;
	}
	
	/**
	 * Gets the.
	 *
	 * @return the segment subscribers list
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SegmentSubscribersList get() throws MailerLiteException
	{
		String endpoint = "/segments/".concat(this.segmentId).concat("/subscribers").concat(this.getQueryParameters());
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		SegmentSubscribersList list = api.getRequest(endpoint, SegmentSubscribersList.class);
		
		list.postDeserialize();
		
		return list;
	}
}
