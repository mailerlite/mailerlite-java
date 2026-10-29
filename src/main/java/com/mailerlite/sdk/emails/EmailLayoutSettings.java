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
 * The layout of an email.
 *
 * In an update, null is not sent and does not change the value.
 * An empty string unsets the value, and the email then uses the value of your account.
 */
public class EmailLayoutSettings {

	/** The width mode, for example "default". */
	@SerializedName("width_mode")
	public String widthMode;

	/** The alignment of the email, for example "center". */
	@SerializedName("alignment")
	public String alignment;
}
