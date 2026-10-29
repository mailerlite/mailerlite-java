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
 * The link style of an email.
 *
 * In an update, null is not sent and does not change the value.
 * An empty string unsets the value, and the email then uses the value of your account.
 */
public class EmailLinkStyle {

	/** The link color, for example "#2cb191". */
	@SerializedName("color")
	public String color;
}
