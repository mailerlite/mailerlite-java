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
 * Do not send an empty string. The API rejects an empty string with a 422 error. Only a brand color accepts an
 * empty string.
 */
public class EmailLayoutSettings {

	/** The width mode, for example "default". */
	@SerializedName("width_mode")
	public String widthMode;

	/** The alignment of the email, for example "center". */
	@SerializedName("alignment")
	public String alignment;
}
