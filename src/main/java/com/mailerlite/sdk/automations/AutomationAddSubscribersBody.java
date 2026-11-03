/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * The Class AutomationAddSubscribersBody.
 */
public class AutomationAddSubscribersBody {

	/** The step id. */
	@SerializedName("step_id")
	public String stepId;
	
	/** The emails. */
	@SerializedName("emails")
	public List<String> emails;
	
	/** The groups. */
	@SerializedName("groups")
	public List<String> groups;
}
