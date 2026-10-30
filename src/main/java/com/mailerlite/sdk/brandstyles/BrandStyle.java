/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.brandstyles;

import com.google.gson.annotations.SerializedName;

/**
 * A palette: one font and six colors.
 *
 * The identifier does not change. The name is only for display, and it can change. So match a palette on its
 * identifier, not on its name.
 */
public class BrandStyle {

	/**
	 * The identifier of the palette, for example "preset-retro". To apply a preset to an email, set it in EmailBrandSettings.style.
	 */
	@SerializedName("identifier")
	public String identifier;

	/** The display name of the palette. */
	@SerializedName("name")
	public String name;

	/** The font family. */
	@SerializedName("font_family")
	public String fontFamily;

	/** The primary color. */
	@SerializedName("primary_color")
	public String primaryColor;

	/** The secondary color. */
	@SerializedName("secondary_color")
	public String secondaryColor;

	/** The heading color. */
	@SerializedName("heading_color")
	public String headingColor;

	/** The text color. */
	@SerializedName("text_color")
	public String textColor;

	/** The border color. */
	@SerializedName("border_color")
	public String borderColor;

	/** The background color. */
	@SerializedName("background_color")
	public String backgroundColor;
}
