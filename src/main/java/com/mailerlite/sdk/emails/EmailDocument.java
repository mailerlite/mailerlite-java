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
 * An email built with the simple editor: its design settings and its content blocks.
 */
public class EmailDocument {

	/** The ID of the email. */
	@SerializedName("id")
	public String id;

	/** The type of the email, for example "builder_inline_email". */
	@SerializedName("type")
	public String type;

	/** The type of the owner of the email, for example "campaigns". */
	@SerializedName("emailable_type")
	public String emailableType;

	/** The ID of the owner of the email, for example the campaign ID. */
	@SerializedName("emailable_id")
	public String emailableId;

	/**
	 * The design settings, as they apply to the email. They include the values that the email inherits from your account.
	 */
	@SerializedName("settings")
	public EmailSettings settings;

	/** The content blocks, in the sequence in which they show in the email. */
	@SerializedName("blocks")
	public EmailBlock[] blocks;
}
