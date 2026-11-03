/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.groups;

import java.util.Map;

import com.google.gson.annotations.SerializedName;

/**
 * The Class GroupImportSubscriber.
 */
public class GroupImportSubscriber {

	/** The email. */
	@SerializedName("email")
	public String email;
	
	/** The name. */
	@SerializedName("name")
	public String name;
	
	/** The fields. */
	@SerializedName("fields")
	public Map<String, Object> fields;
}
