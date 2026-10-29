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
 * The palette and the font of an email.
 *
 * In an update, null is not sent and does not change the value.
 * An empty string unsets the value, and the email then uses the value of your account.
 */
public class EmailBrandSettings {

	/** Where the palette comes from: one of the values in EmailBrandModes. */
	@SerializedName("mode")
	public String mode;

	/**
	 * Write-only. The identifier of a preset from the brand styles, for example "preset-retro".
	 * The API applies the palette of the preset, and the mode then reads back as "custom".
	 */
	@SerializedName("style")
	public String style;

	/** The font family, for example "Inter, sans-serif". */
	@SerializedName("font_family")
	public String fontFamily;

	/** The background color, for example "#ffffff". */
	@SerializedName("background_color")
	public String backgroundColor;

	/** The text color. */
	@SerializedName("text_color")
	public String textColor;

	/** The border color. */
	@SerializedName("border_color")
	public String borderColor;
}
