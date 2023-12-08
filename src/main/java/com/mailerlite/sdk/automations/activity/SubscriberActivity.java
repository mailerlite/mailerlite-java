/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.activity;

import java.time.LocalDateTime;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.ParseDate;

public class SubscriberActivity {

	@SerializedName("id")
	public String id;
	
	@SerializedName("status")
	public String status;
	
	@SerializedName("date")
	public String dateStr;
	
	@ParseDate("dateStr")
	public LocalDateTime activityDate;
	
	@SerializedName("reason")
	public String reason;
	
	@SerializedName("reason_description")
	public String reasonDescription;
	
	@SerializedName("stepRuns")
	public StepRun[] stepRuns;
	
	public transient Object nextStep;
	
	public transient Object currentStep;
}
