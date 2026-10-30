/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.reports;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.MailerLiteResponse;

/**
 * The Class SingleAutomationEcommerceStats.
 */
public class SingleAutomationEcommerceStats extends MailerLiteResponse {

	/** The stats. */
	@SerializedName("data")
	public AutomationEcommerceStats stats;
}
