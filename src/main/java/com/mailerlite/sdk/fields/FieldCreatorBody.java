/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.fields;

import com.google.gson.annotations.SerializedName;

/**
 * The Class FieldCreatorBody.
 */
public class FieldCreatorBody {
	
	/** The name. */
	@SerializedName("name")
	public String name;
	
	/** The type. */
	@SerializedName("type")
	public String type;
}
