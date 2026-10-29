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
 * The response of Emails.createVersion.
 */
public class SingleEmailVersion extends MailerLiteResponse {

	/** The new version, or the last version if nothing changed. */
	@SerializedName("data")
	public EmailVersion version;

	/**
	 * Tells if the API saved a new version. If nothing changed after the last version, the API saves no new
	 * version, returns the last version, and the status is 200.
	 *
	 * @return true if the status is 201
	 */
	public boolean isCreated()
	{
		return responseStatusCode == 201;
	}
}
