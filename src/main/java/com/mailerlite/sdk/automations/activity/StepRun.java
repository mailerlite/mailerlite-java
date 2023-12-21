/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.activity;

import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.ParseDate;

public class StepRun {

	@SerializedName("id")
	public String id;
	
	@SerializedName("step_id")
	public String stepId;
	
	@SerializedName("description")
	public String description;
	
	@SerializedName("scheduled_for")
	public String scheduledForStr;
	
	@ParseDate("scheduledForStr")
	public Date scheduledFor;
}
