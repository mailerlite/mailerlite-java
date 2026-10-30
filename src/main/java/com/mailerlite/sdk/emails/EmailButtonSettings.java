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
 * The button style of an email. Send all values as strings, for example "4" and not 4.
 *
 * In an update, null is not sent and does not change the value.
 * Do not send an empty string. The API rejects an empty string with a 422 error. Only a brand color accepts an
 * empty string.
 */
public class EmailButtonSettings {

	/** The font family. */
	@SerializedName("font_family")
	public String fontFamily;

	/** The font weight, for example "600". */
	@SerializedName("font_weight")
	public String fontWeight;

	/** The letter spacing, for example "0". */
	@SerializedName("letter_spacing")
	public String letterSpacing;

	/** The background color. */
	@SerializedName("background_color")
	public String backgroundColor;

	/** The text color. */
	@SerializedName("text_color")
	public String textColor;

	/** The border radius in pixels, without a unit, for example "4". */
	@SerializedName("border_radius")
	public String borderRadius;
}
