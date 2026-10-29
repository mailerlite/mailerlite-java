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
 * The palettes of the account.
 */
public class BrandStylesData {

	/** The brand styles of the account, or null if the account has none. */
	@SerializedName("brand")
	public BrandStyle brand;

	/** The default palette of the editor. The JSON key is "default", which is a Java keyword. */
	@SerializedName("default")
	public BrandStyle defaultStyle;

	/** The preset palettes. */
	@SerializedName("presets")
	public BrandStyle[] presets;
}
