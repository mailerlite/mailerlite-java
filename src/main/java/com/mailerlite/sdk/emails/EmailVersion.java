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
 * A version of an email: a saved copy of its content and its design settings.
 */
public class EmailVersion {

	/** The ID of the version. */
	@SerializedName("id")
	public String id;

	/** The date and time when the API saved the version, for example "2026-10-28 12:00:00". */
	@SerializedName("created_at")
	public String createdAt;

	/** True if the email has the content of this version now. */
	@SerializedName("current")
	public boolean current;
}
