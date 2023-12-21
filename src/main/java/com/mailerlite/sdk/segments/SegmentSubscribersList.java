/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.segments;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.MailerLiteResponse;
import com.mailerlite.sdk.susbcribers.Subscriber;

/**
 * The Class SegmentSubscribersList.
 */
public class SegmentSubscribersList extends MailerLiteResponse {

	/** The subscribers. */
	@SerializedName("data")
	public Subscriber[] subscribers;
	
	/** The meta. */
	@SerializedName("meta")
	public SegmentsSubscriberMeta meta;
	
	/**
	 * Post deserialize.
	 */
	public void postDeserialize()
	{
		for (Subscriber s : subscribers) {
			s.parseDates();
		}
	}
}
