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
	 * Post deserialise.
	 */
	public void postDeserialise()
	{
		for (Segment s : segments) {
			s.parseDates();
		}
	}
}
