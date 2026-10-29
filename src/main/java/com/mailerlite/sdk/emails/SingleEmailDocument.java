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
 * The response of the endpoints that return a full email.
 */
public class SingleEmailDocument extends MailerLiteResponse {

	/** The email. */
	@SerializedName("data")
	public EmailDocument email;

	/** The etag of the email. Send it with the next write to the email. */
	@SerializedName("etag")
	public String etag;
}
