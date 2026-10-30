/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.emails;

/**
 * The values of {@code EmailBrandSettings.mode}: where the palette of an email comes from.
 */
public final class EmailBrandModes {

	/** Follow the brand styles of your account. The API clears the colors of the email. */
	public static final String ACCOUNT = "account";

	/** Use the palette of the template of the email. */
	public static final String TEMPLATE = "template";

	/** Use the colors of the email. */
	public static final String CUSTOM = "custom";

	private EmailBrandModes()
	{
	}
}
