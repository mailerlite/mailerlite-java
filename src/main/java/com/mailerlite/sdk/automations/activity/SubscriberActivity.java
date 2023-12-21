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
 * The Class SubscriberActivity.
 */
public class SubscriberActivity {

	/** The id. */
	@SerializedName("id")
	public String id;
	
	/** The status. */
	@SerializedName("status")
	public String status;
	
	/** The date str. */
	@SerializedName("date")
	public String dateStr;
	
	/** The activity date. */
	@ParseDate("dateStr")
	public Date activityDate;
	
	/** The reason. */
	@SerializedName("reason")
	public String reason;
	
	/** The reason description. */
	@SerializedName("reason_description")
	public String reasonDescription;
	
	/** The step runs. */
	@SerializedName("stepRuns")
	public StepRun[] stepRuns;
	
	/** The next step. */
	public transient Object nextStep;
	
	/** The current step. */
	public transient Object currentStep;
}
