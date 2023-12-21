/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations;

import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.ApiResource;
import com.mailerlite.sdk.util.ParseDate;

/**
 * The Class Automation.
 */
public class Automation extends ApiResource {

	/** The id. */
	@SerializedName("id")
	public String id;

	/** The name. */
	@SerializedName("name")
	public String name;
	
	/** The enabled. */
	@SerializedName("enabled")
	public Boolean enabled;
	
	/** The trigger data. */
	@SerializedName("trigger_data")
	public TriggerData triggerData;
	

	/** The steps. */
	public transient Object[] steps;
	
	/** The triggers. */
	public transient Object[] triggers;
	
	
	/** The complete. */
	@SerializedName("complete")
	public Boolean complete;
	
	/** The broken. */
	@SerializedName("broken")
	public Boolean broken;
	
	/** The warnings. */
	@SerializedName("warnings")
	public String[] warnings;
	
	/** The emails count. */
	@SerializedName("emails_count")
	public int emailsCount;
	
	/** The first email screenshot url. */
	@SerializedName("first_email_screenshot_url")
	public String firstEmailScreenshotUrl;
	
	/** The stats. */
	@SerializedName("stats")
	public AutomationStats stats;
	
	/** The has banned content. */
	@SerializedName("has_banned_content")
	public Boolean hasBannedContent;
	
	/** The qualified subscribers count. */
	@SerializedName("qualified_subscribers_count")
	public int qualifiedSubscribersCount;
	
	/** The created at str. */
	@SerializedName("created_at")
	public String createdAtStr;
	
	/** The created at. */
	@ParseDate("createdAtStr")
	public Date createdAt;
	
	

}
