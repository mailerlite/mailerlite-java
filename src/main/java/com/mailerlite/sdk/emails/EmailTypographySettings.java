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
 * The text styles of an email.
 */
public class EmailTypographySettings {

	/** The link style. */
	@SerializedName("link")
	public EmailLinkStyle link;

	/** The heading styles. */
	@SerializedName("heading")
	public EmailHeadingStyle heading;

	/** The paragraph styles. */
	@SerializedName("paragraph")
	public EmailParagraphStyle paragraph;
}
