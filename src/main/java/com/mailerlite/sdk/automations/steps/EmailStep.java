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

/**
 * The Class EmailStep.
 */
public class EmailStep extends AutomationStep {

	/** The name. */
	@SerializedName("name")
	public String name;
	
	/** The subject. */
	@SerializedName("subject")
	public String subject;
	
	/** The from. */
	@SerializedName("from")
	public String from;
	
	/** The from name. */
	@SerializedName("from_name")
	public String fromName;
	
	/** The email id. */
	@SerializedName("email_id")
	public String emailId;
	
	/** The email. */
	@SerializedName("email")
	public Email email;
	
	/** The language id. */
	@SerializedName("language_id")
	public int languageId;
	
	/** The track opens. */
	@SerializedName("track_opens")
	public Boolean trackOpens;
	
	/** The google analytics. */
	@SerializedName("google_analytics")
	public Boolean googleAnalytics;
	
	/** The tracking was disabled. */
	@SerializedName("tracking_was_disabled")
	public Boolean trackingWasDisabled;
}
