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
 * The Class ResponseLinks.
 */
public class ResponseLinks {

	/** The first. */
	@SerializedName("first")
	public String first;
	
	/** The last. */
	@SerializedName("last")
	public String last;
	
	/** The previous. */
	@SerializedName("prev")
	public String previous;
	
	/** The next. */
	@SerializedName("next")
	public String next;
}
