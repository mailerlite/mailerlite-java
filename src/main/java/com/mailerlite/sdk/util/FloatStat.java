/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.util;

import com.google.gson.annotations.SerializedName;

public class FloatStat {

	@SerializedName("float")
	public float floatValue;
	
	@SerializedName("string")
	public String stringValue;
}
