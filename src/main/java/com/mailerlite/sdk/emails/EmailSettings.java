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
 * The design settings of an email.
 *
 * In a response, the settings show the values that apply to the email, also the values that the email
 * inherits from your account. In an update, set only the fields that you want to change. A null field is not sent.
 * An empty string unsets a value.
 *
 * Do not send back all the settings that you read. The API stores each value that you send, also an inherited
 * value. The email then no longer follows your account for that value.
 */
public class EmailSettings {

	/** The palette and the font of the email. */
	@SerializedName("brand")
	public EmailBrandSettings brand;

	/** The layout of the email. */
	@SerializedName("layout")
	public EmailLayoutSettings layout;

	/** The text styles of the email. */
	@SerializedName("typography")
	public EmailTypographySettings typography;

	/** The button style of the email. */
	@SerializedName("button")
	public EmailButtonSettings button;

	/** The footer of the email. */
	@SerializedName("footer")
	public EmailFooterSettings footer;
}
