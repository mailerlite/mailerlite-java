/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.util;

import com.google.gson.annotations.SerializedName;

/**
 * The Class RateValue.
 */
public class RateValue {

	/** The float value. */
	@SerializedName("float")
	public float floatValue;
	
	/** The string value. */
	@SerializedName("string")
	public String stringValue;
}
