/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.segments;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.PaginatedResponse;

/**
 * The Class SegmentsList.
 */
public class SegmentsList extends PaginatedResponse {

	/** The segments. */
	@SerializedName("data")
	public Segment[] segments;
	
	/**
	 * Parses the dates of every item.
	 */
	@Override
	public void postDeserialize()
	{
		if (segments == null) {
			return;
		}
		
		for (Segment s : segments) {
			s.parseDates();
		}
	}
	
	/**
	 * Post deserialise.
	 *
	 * @deprecated misspelled, use {@link #postDeserialize()}
	 */
	@Deprecated
	public void postDeserialise()
	{
		postDeserialize();
	}
}
