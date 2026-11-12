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
 * The values that the API removed or replaced. The request is still a success.
 * <p>
 * For settings, these are unsafe values. For a block, these are the attributes and the mobile keys that the block
 * type does not declare.
 */
public class EmailRejected {

	/** The message. */
	@SerializedName("message")
	public String message;

	/**
	 * The paths of the values, for example "footer.social_links[0].url" or "editor_content.attrs.onclick".
	 */
	@SerializedName("keys")
	public String[] keys;
}
