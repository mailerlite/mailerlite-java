/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations;

import com.google.gson.annotations.SerializedName;

public class FloatStringValue {

	@SerializedName("float")
	public float floatValue;
	
	@SerializedName("string")
	public String stringValue;
}
