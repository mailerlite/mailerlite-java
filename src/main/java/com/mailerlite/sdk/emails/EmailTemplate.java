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
 * A template that the API made from an email.
 */
public class EmailTemplate {

	/** The ID of the template. */
	@SerializedName("id")
	public String id;

	/** The name of the template. */
	@SerializedName("name")
	public String name;

	/** The type of the template, for example "email". */
	@SerializedName("type")
	public String type;

	/** The date and time when the API made the template, for example "2026-10-28 12:00:00". */
	@SerializedName("created_at")
	public String createdAt;
}
