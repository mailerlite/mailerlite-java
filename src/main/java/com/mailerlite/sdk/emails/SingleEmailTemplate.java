/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.emails;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.MailerLiteResponse;

/**
 * The response of Emails.createTemplate.
 */
public class SingleEmailTemplate extends MailerLiteResponse {

	/** The new template. */
	@SerializedName("data")
	public EmailTemplate template;
}
