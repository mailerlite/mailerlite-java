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
 * The Class ResponseMetaLink.
 */
public class ResponseMetaLink {

	/** The url. */
	@SerializedName("url")
	public String url;
	
	/** The label. */
	@SerializedName("label")
	public String label;
	
	/** The active. */
	@SerializedName("active")
	public Boolean active;
}
