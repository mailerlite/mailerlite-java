/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.susbcribers;

import com.google.gson.annotations.SerializedName;

public class SubscriberImportStatusEntry {

	@SerializedName("id")
	public String id;
	
	@SerializedName("email")
	public String email;
}
