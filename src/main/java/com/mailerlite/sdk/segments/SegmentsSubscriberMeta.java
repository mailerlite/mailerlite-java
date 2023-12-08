/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.segments;

import com.google.gson.annotations.SerializedName;

public class SegmentsSubscriberMeta {

	@SerializedName("total")
	public int total;
	
	@SerializedName("count")
	public int count;
	
	@SerializedName("last")
	public int last;
}
