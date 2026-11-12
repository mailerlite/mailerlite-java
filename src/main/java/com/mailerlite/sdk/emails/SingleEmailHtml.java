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
 * The response of Emails.getHtml.
 */
public class SingleEmailHtml extends MailerLiteResponse {

	/** The HTML preview of the email. */
	@SerializedName("data")
	public EmailHtml preview;

	/** The etag of the email. Send it with the next write to the email. */
	@SerializedName("etag")
	public String etag;
}
