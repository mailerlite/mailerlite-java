/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.emails;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.ApiResource;

/**
 * The Class EmailBase.
 */
public class EmailBase extends ApiResource {
	
	/** The from. */
	@SerializedName("from")
	public String from;
	
	/** The from name. */
	@SerializedName("from_name")
	public String fromName;
	
	/** The content. */
	@SerializedName("content")
	public String content;
	
	/** The subject. */
	@SerializedName("subject")
	public String subject;
}
