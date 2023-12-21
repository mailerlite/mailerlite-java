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

/**
 * The Class StepRun.
 */
public class StepRun {

	/** The id. */
	@SerializedName("id")
	public String id;
	
	/** The step id. */
	@SerializedName("step_id")
	public String stepId;
	
	/** The description. */
	@SerializedName("description")
	public String description;
	
	/** The scheduled for str. */
	@SerializedName("scheduled_for")
	public String scheduledForStr;
	
	/** The scheduled for. */
	@ParseDate("scheduledForStr")
	public Date scheduledFor;
}
