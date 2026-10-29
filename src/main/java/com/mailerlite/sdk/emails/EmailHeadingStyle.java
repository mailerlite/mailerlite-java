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
 * The heading styles of an email. Send all values as strings, for example "700" and not 700.
 *
 * In an update, null is not sent and does not change the value.
 * An empty string unsets the value, and the email then uses the value of your account.
 */
public class EmailHeadingStyle {

	/** The font family. */
	@SerializedName("font_family")
	public String fontFamily;

	/** The font weight, for example "700". */
	@SerializedName("font_weight")
	public String fontWeight;

	/** The line height, for example "150%". */
	@SerializedName("line_height")
	public String lineHeight;

	/** The letter spacing, for example "0". */
	@SerializedName("letter_spacing")
	public String letterSpacing;

	/** The style of level 1 headings. */
	@SerializedName("h1")
	public EmailTextStyle h1;

	/** The style of level 2 headings. */
	@SerializedName("h2")
	public EmailTextStyle h2;

	/** The style of level 3 headings. */
	@SerializedName("h3")
	public EmailTextStyle h3;
}
