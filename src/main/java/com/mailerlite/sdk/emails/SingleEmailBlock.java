/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.emails;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.MailerLiteResponse;

/**
 * The response of the endpoints that return one block.
 */
public class SingleEmailBlock extends MailerLiteResponse {

	/** The block, with its placement. */
	@SerializedName("data")
	public EmailBlock block;

	/** The etag of the email. Send it with the next write to the email. */
	@SerializedName("etag")
	public String etag;

	/**
	 * The attributes that the API removed from the block, or null if there are none. Only createBlock and
	 * updateBlock fill it. getBlock and moveBlock leave it null.
	 */
	@SerializedName("rejected")
	public EmailRejected rejected;
}
