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
 * The response of Emails.updateSettings.
 */
public class SingleEmailSettings extends MailerLiteResponse {

	/** The design settings after the update, as they apply to the email. */
	@SerializedName("data")
	public EmailSettings settings;

	/** The new etag of the email. Send it with the next write to the email. */
	@SerializedName("etag")
	public String etag;

	/** The unsafe values that the API removed or replaced, or null if there are none. */
	@SerializedName("rejected")
	public EmailRejected rejected;
}
