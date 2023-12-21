/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations;

import com.google.gson.annotations.SerializedName;

/**
 * The Class FloatStringValue.
 */
public class FloatStringValue {

	/** The float value. */
	@SerializedName("float")
	public float floatValue;
	
	/** The string value. */
	@SerializedName("string")
	public String stringValue;
}
