/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.segments;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.MailerLiteResponse;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.groups.NameCreatorBody;
import com.mailerlite.sdk.util.JsonSerializationDeserializationStrategy;

/**
 * The Class Segments.
 */
public class Segments {

	/** The api object reference. */
	private MailerLite apiObjectReference;
	
	/**
	 * Instantiates a new segments.
	 *
	 * @param apiRef the api ref
	 */
	public Segments(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}
	
	/**
	 * Retriever.
	 *
	 * @return the segments retriever
	 */
	public SegmentsRetriever retriever()
	{
		return new SegmentsRetriever(apiObjectReference);
	}
	
	/**
	 * Subscribers.
	 *
	 * @param segmentId the segment id
	 * @return the segment subscribers
	 */
	public SegmentSubscribers subscribers(String segmentId)
	{
		return new SegmentSubscribers(apiObjectReference, segmentId);
	}
	
	/**
	 * Update.
	 *
	 * @param segmentId the segment id
	 * @param newSegmentName the new segment name
	 * @return the single segment
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SingleSegment update(String segmentId, String newSegmentName) throws MailerLiteException
	{
		MailerLiteApi api = new MailerLiteApi();
        api.setToken(apiObjectReference.getToken());
        
        NameCreatorBody requestBody = new NameCreatorBody();
        requestBody.name = newSegmentName;
        
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();
       
        String json = gson.toJson(requestBody);
        
        SingleSegment segment = api.putRequest("/segments/".concat(segmentId), json, SingleSegment.class);
        
        segment.segment.parseDates();
        
        return segment;
	}
	
	/**
	 * Delete.
	 *
	 * @param segmentId the segment id
	 * @return the mailer lite response
	 * @throws MailerLiteException the mailer lite exception
	 */
	public MailerLiteResponse delete(String segmentId) throws MailerLiteException
	{
		String endpoint = "/segments/".concat(segmentId);
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		MailerLiteResponse response = api.deleteRequest(endpoint, MailerLiteResponse.class);
		
		return response;
	}
}
