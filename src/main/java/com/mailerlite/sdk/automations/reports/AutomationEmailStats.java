/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.reports;

import com.google.gson.annotations.SerializedName;

/**
 * The Class AutomationEmailStats.
 */
public class AutomationEmailStats {

	/** The engagement. */
	@SerializedName("engagement")
	public EmailEngagementStats engagement;
	
	/** The deliverability. */
	@SerializedName("deliverability")
	public EmailDeliverabilityStats deliverability;
}
