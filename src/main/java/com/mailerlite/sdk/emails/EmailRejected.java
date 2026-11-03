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
 * The unsafe values that the API removed or replaced. The request is still a success.
 */
public class EmailRejected {

	/** The message. */
	@SerializedName("message")
	public String message;

	/** The paths of the values, for example "footer.social_links[0].url". */
	@SerializedName("keys")
	public String[] keys;
}
