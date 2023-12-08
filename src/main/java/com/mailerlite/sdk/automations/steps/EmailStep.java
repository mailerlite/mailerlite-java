/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.steps;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.emails.Email;

public class EmailStep extends AutomationStep {

	@SerializedName("name")
	public String name;
	
	@SerializedName("subject")
	public String subject;
	
	@SerializedName("from")
	public String from;
	
	@SerializedName("from_name")
	public String fromName;
	
	@SerializedName("email_id")
	public String emailId;
	
	@SerializedName("email")
	public Email email;
	
	@SerializedName("language_id")
	public int languageId;
	
	@SerializedName("track_opens")
	public Boolean trackOpens;
	
	@SerializedName("google_analytics")
	public Boolean googleAnalytics;
	
	@SerializedName("tracking_was_disabled")
	public Boolean trackingWasDisabled;
}
