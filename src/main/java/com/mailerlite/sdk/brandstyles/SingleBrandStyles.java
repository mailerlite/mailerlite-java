/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.brandstyles;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.MailerLiteResponse;

/**
 * The response of BrandStyles.get.
 */
public class SingleBrandStyles extends MailerLiteResponse {

	/** The palettes of the account. */
	@SerializedName("data")
	public BrandStylesData brandStyles;
}
