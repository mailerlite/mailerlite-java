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
import com.mailerlite.sdk.util.PaginatedRequest;

public class SegmentsRetriever extends PaginatedRequest<SegmentsRetriever> {

	private MailerLite apiObjectReference;
	
	public SegmentsRetriever(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}
	
	public SegmentsList get() throws MailerLiteException
	{
		String endpoint = "/segments".concat(this.getQueryParameters());
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		SegmentsList list = api.getRequest(endpoint, SegmentsList.class);
		
		list.postDeserialize();
		
		return list;
	}
	
	public SingleSegment getSingle(String segmentId) throws MailerLiteException
	{
		String endpoint = "/segments/".concat(segmentId);
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		SingleSegment segment = api.getRequest(endpoint, SingleSegment.class);
		
		segment.segment.parseDates();
		
		return segment;
	}
	
	@Override
	protected SegmentsRetriever getInstance() {
		return this;
	}

}
