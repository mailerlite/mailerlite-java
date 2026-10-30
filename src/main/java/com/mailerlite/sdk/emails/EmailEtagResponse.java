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
 * The response of Emails.deleteBlock. It has only the new etag of the email.
 */
public class EmailEtagResponse extends MailerLiteResponse {

	/** The new etag of the email. Send it with the next write to the email. */
	@SerializedName("etag")
	public String etag;
}
