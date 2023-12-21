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

/**
 * The Class Webhook.
 */
public class Webhook extends ApiResource {

	/** The id. */
	@SerializedName("id")
	public String id;
	
	/** The name. */
	@SerializedName("name")
	public String name;
	
	/** The url. */
	@SerializedName("url")
	public String url;
	
	/** The events. */
	@SerializedName("events")
	public String[] events;
	
	/** The enabled. */
	@SerializedName("enabled")
	public Boolean enabled;
	
	/** The secret. */
	@SerializedName("secret")
	public String secret;
	
	/** The created at str. */
	@SerializedName("created_at")
	public String createdAtStr;
	
	/** The created at. */
	@ParseDate("createdAtStr")
	public Date createdAt;
	
	/** The updated at str. */
	@SerializedName("updated_at")
	public String updatedAtStr;
	
	/** The updated at. */
	@ParseDate("updatedAtStr")
	public Date updatedAt;
}
