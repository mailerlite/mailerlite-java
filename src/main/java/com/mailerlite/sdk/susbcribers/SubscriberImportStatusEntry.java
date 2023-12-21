/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.susbcribers;

import com.google.gson.annotations.SerializedName;

/**
 * The Class SubscriberImportStatusEntry.
 */
public class SubscriberImportStatusEntry {

	/** The id. */
	@SerializedName("id")
	public String id;
	
	/** The email. */
	@SerializedName("email")
	public String email;
}
