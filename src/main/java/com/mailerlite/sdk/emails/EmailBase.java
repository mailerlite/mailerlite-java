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

public class EmailBase extends ApiResource {
	
	@SerializedName("from")
	public String from;
	
	@SerializedName("from_name")
	public String fromName;
	
	@SerializedName("content")
	public String content;
	
	@SerializedName("subject")
	public String subject;
}
