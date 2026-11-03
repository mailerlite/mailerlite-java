/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.activity;

import com.google.gson.annotations.SerializedName;

/**
 * The Class ActivitySubscriber.
 */
public class ActivitySubscriber {

	/** The id. */
	@SerializedName("id")
	public String id;
	
	/** The email. */
	@SerializedName("email")
	public String email;
	
	/** The name. */
	@SerializedName("name")
	public String name;
}
