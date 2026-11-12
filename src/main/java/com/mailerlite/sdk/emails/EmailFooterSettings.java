/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.emails;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * The footer of an email.
 *
 * In an update, a null field is not sent and does not change. To unset companyName, address or infoText, send an
 * empty editor document. The email then uses the text of your account.
 */
public class EmailFooterSettings {

	/** True to show the preferences link. */
	@SerializedName("show_preferences")
	public Boolean showPreferences;

	/** The company name, as an editor document. */
	@SerializedName("company_name")
	public EmailEditorText companyName;

	/** The address, as an editor document. */
	@SerializedName("address")
	public EmailEditorText address;

	/** The information text, as an editor document. */
	@SerializedName("info_text")
	public EmailEditorText infoText;

	/** The text of the unsubscribe link. It is plain text. */
	@SerializedName("unsubscribe_text")
	public String unsubscribeText;

	/** The text of the preferences link. It is plain text. */
	@SerializedName("preferences_text")
	public String preferencesText;

	/**
	 * The social links.
	 *
	 * An update replaces the whole list. Send all the links that you want to keep, in the sequence that you want.
	 * An empty list removes all the links. Keep the linkId of each link to keep its click history.
	 */
	@SerializedName("social_links")
	public List<EmailSocialLink> socialLinks;
}
