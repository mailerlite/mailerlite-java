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
 * The paragraph styles of an email. Send all values as strings, for example "400" and not 400.
 *
 * In an update, null is not sent and does not change the value.
 * An empty string unsets the value, and the email then uses the value of your account.
 */
public class EmailParagraphStyle {

	/** The font family. */
	@SerializedName("font_family")
	public String fontFamily;

	/** The font weight, for example "400". */
	@SerializedName("font_weight")
	public String fontWeight;

	/** The line height, for example "150%". */
	@SerializedName("line_height")
	public String lineHeight;

	/** The letter spacing, for example "0". */
	@SerializedName("letter_spacing")
	public String letterSpacing;

	/** The style of paragraphs. */
	@SerializedName("p1")
	public EmailTextStyle p1;
}
