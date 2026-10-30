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
 * A social link in the footer of an email.
 */
public class EmailSocialLink {

	/** The URL of the link. */
	@SerializedName("url")
	public String url;

	/** The network of the link, for example "website". */
	@SerializedName("type")
	public String type;

	/** The label of the link. */
	@SerializedName("label")
	public String label;

	/**
	 * The ID that the click reports use.
	 *
	 * Send it back without a change. Without it, the API makes a new link, and the click history of the old link is lost.
	 */
	@SerializedName("link_id")
	public Long linkId;
}
