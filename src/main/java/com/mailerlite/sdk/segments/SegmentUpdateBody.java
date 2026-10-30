/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.segments;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;

/**
 * The Class SegmentUpdateBody.
 */
public class SegmentUpdateBody {

	/** The name. */
	@SerializedName("name")
	public String name;
	
	/** The filter. */
	@SerializedName("filter")
	public JsonArray filter;
}
