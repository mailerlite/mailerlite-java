/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.emails;

import com.google.gson.annotations.SerializedName;

/**
 * The HTML of an email.
 *
 * The HTML is a preview, not an email that is ready to send. Personalization shows as tokens such as {$name},
 * and the links have markers instead of tracking URLs. Do not send this HTML yourself.
 */
public class EmailHtml {

	/** The HTML of the email, as one string. */
	@SerializedName("html")
	public String html;
}
