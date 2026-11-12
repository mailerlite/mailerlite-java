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
 * Do not send an empty string. The API rejects an empty string with a 422 error. Only a brand color accepts an
 * empty string.
 */
public class EmailLinkStyle {

	/** The link color, for example "#2cb191". */
	@SerializedName("color")
	public String color;
}
