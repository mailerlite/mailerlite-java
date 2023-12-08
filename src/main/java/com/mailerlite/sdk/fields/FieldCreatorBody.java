/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.fields;

import com.google.gson.annotations.SerializedName;

public class FieldCreatorBody {
	
	@SerializedName("name")
	public String name;
	
	@SerializedName("type")
	public String type;
}
