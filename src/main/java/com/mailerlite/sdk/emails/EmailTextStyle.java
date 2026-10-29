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
 * The color and the size of a text style. Send the size as a string, for example "16" and not 16.
 *
 * In an update, null is not sent and does not change the value.
 * An empty string unsets the value, and the email then uses the value of your account.
 */
public class EmailTextStyle {

	/** The text color, for example "#000000". */
	@SerializedName("color")
	public String color;

	/** The text size in pixels, without a unit, for example "16". */
	@SerializedName("size")
	public String size;
}
