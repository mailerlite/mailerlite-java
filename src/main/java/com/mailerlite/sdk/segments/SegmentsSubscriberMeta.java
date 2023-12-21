/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.segments;

import com.google.gson.annotations.SerializedName;

/**
 * The Class SegmentsSubscriberMeta.
 */
public class SegmentsSubscriberMeta {

	/** The total. */
	@SerializedName("total")
	public int total;
	
	/** The count. */
	@SerializedName("count")
	public int count;
	
	/** The last. */
	@SerializedName("last")
	public int last;
}
