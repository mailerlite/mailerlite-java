/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.forms;

import java.util.Date;
import java.util.HashMap;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.automations.FloatStringValue;
import com.mailerlite.sdk.util.ApiResource;

/**
 * The Class Form.
 */
public class Form extends ApiResource {

	/** The id. */
	@SerializedName("id")
	public String id;
	
	/** The type. */
	@SerializedName("type")
	public String type;
	
	/** The slug. */
	@SerializedName("slug")
	public String slug;
	
	/** The name. */
	@SerializedName("name")
	public String name;
	
	/** The created at str. */
	@SerializedName("created_at")
	public String createdAtStr;
		
	/** The created at. */
	public Date createdAt;
	
	/** The conversions count. */
	@SerializedName("conversions_count")
	public int conversionsCount;
	
	/** The opens count. */
	@SerializedName("opens_count")
	public int opensCount;
	
	/** The conversion rate. */
	@SerializedName("conversion_rate")
	public FloatStringValue conversionRate;
	
	/** The settings. */
	@SerializedName("settings")
	public HashMap<String, String> settings;
	
	/** The last registration at str. */
	@SerializedName("last_registration_at")
	public String lastRegistrationAtStr;
	
	/** The last registration at. */
	public Date lastRegistrationAt;
	
	/** The active. */
	@SerializedName("active")
	public Boolean active;
	
	/** The is broken. */
	@SerializedName("is_broken")
	public Boolean isBroken;
	
	/** The has content. */
	@SerializedName("has_content")
	public Boolean hasContent;
	
	/** The permissions. */
	@SerializedName("can")
	public HashMap<String, Boolean> permissions;
	
	/** The used in automations. */
	@SerializedName("used_in_automations")
	public Boolean usedInAutomations;
	
	/** The warnings. */
	@SerializedName("warnings")
	public String[] warnings;
	
	/** The double optin. */
	@SerializedName("double_optin")
	public Boolean doubleOptin;
	
	/** The screenshot url. */
	@SerializedName("screenshot_url")
	public String screenshotUrl;
}
