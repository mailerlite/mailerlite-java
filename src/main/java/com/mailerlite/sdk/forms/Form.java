package com.mailerlite.sdk.forms;

import java.time.LocalDateTime;
import java.util.HashMap;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.automations.FloatStringValue;
import com.mailerlite.sdk.util.ApiResource;

public class Form extends ApiResource {

	@SerializedName("id")
	public String id;
	
	@SerializedName("type")
	public String type;
	
	@SerializedName("slug")
	public String slug;
	
	@SerializedName("name")
	public String name;
	
	@SerializedName("created_at")
	public String createdAtStr;
		
	public LocalDateTime createdAt;
	
	@SerializedName("conversions_count")
	public int conversionsCount;
	
	@SerializedName("opens_count")
	public int opensCount;
	
	@SerializedName("conversion_rate")
	public FloatStringValue conversionRate;
	
	@SerializedName("settings")
	public HashMap<String, String> settings;
	
	@SerializedName("last_registration_at")
	public String lastRegistrationAtStr;
	
	public LocalDateTime lastRegistrationAt;
	
	@SerializedName("active")
	public Boolean active;
	
	@SerializedName("is_broken")
	public Boolean isBroken;
	
	@SerializedName("has_content")
	public Boolean hasContent;
	
	@SerializedName("can")
	public HashMap<String, Boolean> permissions;
	
	@SerializedName("used_in_automations")
	public Boolean usedInAutomations;
	
	@SerializedName("warnings")
	public String[] warnings;
	
	@SerializedName("double_optin")
	public Boolean doubleOptin;
	
	@SerializedName("screenshot_url")
	public String screenshotUrl;
}
