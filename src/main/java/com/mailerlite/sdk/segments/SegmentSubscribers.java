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

public class SegmentSubscribers extends ApiRequest {

	private MailerLite apiObjectReference;
	
	private String segmentId;
	
	public SegmentSubscribers(MailerLite apiRef, String forSegmentId)
	{
		apiObjectReference = apiRef;
		segmentId = forSegmentId;
	}
	
	public SegmentSubscribers limit(int limit)
	{
		this.addQueryParameter("limig", String.valueOf(limit));
		return this;
	}
	
	public SegmentSubscribers statusFilter(String statusFilter)
	{
		this.addQueryParameter("filter[status]", statusFilter);
		
		return this;
	}
	
	public SegmentSubscribers after(String subscriberId)
	{
		this.addQueryParameter("after", subscriberId);
		
		return this;
	}
	
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
