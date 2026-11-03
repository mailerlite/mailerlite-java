/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.emails;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.PaginatedResponse;

/**
 * The response of EmailVersionsRetriever.get. The list starts with the newest version.
 */
public class EmailVersionsList extends PaginatedResponse {

	/** The versions of the email. */
	@SerializedName("data")
	public EmailVersion[] versions;
}
