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
	
	/** The path. */
	@SerializedName("path")
	public String path;
	
	/** The per page. */
	@SerializedName("per_page")
	public int perPage;
	
	/** The next cursor. */
	@SerializedName("next_cursor")
	public String nextCursor;
	
	/** The previous cursor. */
	@SerializedName("prev_cursor")
	public String previousCursor;
}
