package com.mailerlite.sdk.automations;

import java.time.LocalDateTime;
import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.automations.triggers.AutomationTrigger;
import com.mailerlite.sdk.util.ApiResource;
import com.mailerlite.sdk.util.ParseDate;

public class Automation extends ApiResource {

	@SerializedName("id")
	public String id;

	@SerializedName("name")
	public String name;
	
	@SerializedName("enabled")
	public Boolean enabled;
	
	@SerializedName("trigger_data")
	public TriggerData triggerData;
	

	public transient Object[] steps;
	
	public transient Object[] triggers;
	
	
	@SerializedName("complete")
	public Boolean complete;
	
	@SerializedName("broken")
	public Boolean broken;
	
	@SerializedName("warnings")
	public String[] warnings;
	
	@SerializedName("emails_count")
	public int emailsCount;
	
	@SerializedName("first_email_screenshot_url")
	public String firstEmailScreenshotUrl;
	
	@SerializedName("stats")
	public AutomationStats stats;
	
	@SerializedName("has_banned_content")
	public Boolean hasBannedContent;
	
	@SerializedName("qualified_subscribers_count")
	public int qualifiedSubscribersCount;
	
	@SerializedName("created_at")
	public String createdAtStr;
	
	@ParseDate("createdAtStr")
	public LocalDateTime createdAt;
	
	

}
