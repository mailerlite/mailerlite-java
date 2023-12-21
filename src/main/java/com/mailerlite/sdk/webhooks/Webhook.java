/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.webhooks;

import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.ApiResource;
import com.mailerlite.sdk.util.ParseDate;

public class Webhook extends ApiResource {

	@SerializedName("id")
	public String id;
	
	@SerializedName("name")
	public String name;
	
	@SerializedName("url")
	public String url;
	
	@SerializedName("events")
	public String[] events;
	
	@SerializedName("enabled")
	public Boolean enabled;
	
	@SerializedName("secret")
	public String secret;
	
	@SerializedName("created_at")
	public String createdAtStr;
	
	@ParseDate("createdAtStr")
	public Date createdAt;
	
	@SerializedName("updated_at")
	public String updatedAtStr;
	
	@ParseDate("updatedAtStr")
	public Date updatedAt;
}
