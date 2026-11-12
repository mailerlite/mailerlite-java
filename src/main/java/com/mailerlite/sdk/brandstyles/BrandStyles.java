/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.brandstyles;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.exceptions.MailerLiteException;

/**
 * The brand styles endpoint: the palettes that you can apply to an email built with the simple editor.
 */
public class BrandStyles {

	private MailerLite apiObjectReference;

	/**
	 * Instantiates a new brand styles service.
	 *
	 * @param apiReference the api reference
	 */
	public BrandStyles(MailerLite apiReference)
	{
		apiObjectReference = apiReference;
	}

	/**
	 * Gets the brand styles of the account, the default palette and the presets.
	 *
	 * @return the palettes
	 * @throws MailerLiteException if the API returns an error or the request fails
	 */
	public SingleBrandStyles get() throws MailerLiteException
	{
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());

		return api.sendRequest("GET", "/brand-styles", null, null, SingleBrandStyles.class);
	}
}
