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

/**
 * The Class SingleSegment.
 */
public class SingleSegment extends MailerLiteResponse {

	/** The segment. */
	@SerializedName("data")
	public Segment segment;
}
