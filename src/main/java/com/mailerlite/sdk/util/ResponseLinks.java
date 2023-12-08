/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.util;

import com.google.gson.annotations.SerializedName;

public class ResponseLinks {

	@SerializedName("first")
	public String first;
	
	@SerializedName("last")
	public String last;
	
	@SerializedName("prev")
	public String previous;
	
	@SerializedName("next")
	public String next;
}
