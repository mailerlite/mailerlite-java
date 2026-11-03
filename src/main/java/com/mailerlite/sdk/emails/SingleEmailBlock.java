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
}
