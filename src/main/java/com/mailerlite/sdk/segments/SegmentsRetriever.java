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

/**
 * The Class SegmentsRetriever.
 */
public class SegmentsRetriever extends PaginatedRequest<SegmentsRetriever> {

	/** The api object reference. */
	private MailerLite apiObjectReference;
	
	/**
	 * Instantiates a new segments retriever.
	 *
	 * @param apiRef the api ref
	 */
	public SegmentsRetriever(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}
	
	/**
	 * Gets the.
	 *
	 * @return the segments list
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SegmentsList get() throws MailerLiteException
	{
		String endpoint = "/segments".concat(this.getQueryParameters());
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		SegmentsList list = api.getRequest(endpoint, SegmentsList.class);
		
		list.postDeserialize();
		
		return list;
	}
	
	/**
	 * Gets the single.
	 *
	 * @param segmentId the segment id
	 * @return the single
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SingleSegment getSingle(String segmentId) throws MailerLiteException
	{
		String endpoint = "/segments/".concat(segmentId);
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		SingleSegment segment = api.getRequest(endpoint, SingleSegment.class);
		
		segment.segment.parseDates();
		
		return segment;
	}
	
	/**
	 * Sets the page to return.
	 *
	 * @param page the page
	 * @return the segments retriever
	 */
	public SegmentsRetriever page(int page)
	{
		this.addQueryParameter("page", Integer.toString(page));
		
		return this;
	}
	
	/**
	 * Sets the sort order.
	 *
	 * @param sort the sort
	 * @return the segments retriever
	 */
	public SegmentsRetriever sort(String sort)
	{
		this.addQueryParameter("sort", sort);
		
		return this;
	}
	
	/**
	 * Includes the segment filter rules.
	 *
	 * @param withFilter the with filter
	 * @return the segments retriever
	 */
	public SegmentsRetriever withFilter(boolean withFilter)
	{
		this.addQueryParameter("with_filter", withFilter ? "1" : "0");
		
		return this;
	}	
	
	/**
	 * Gets the single instance of SegmentsRetriever.
	 *
	 * @return single instance of SegmentsRetriever
	 */
	@Override
	protected SegmentsRetriever getInstance() {
		return this;
	}

}
