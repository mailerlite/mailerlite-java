/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.reports;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

/**
 * The Class AutomationEcommerceStats.
 */
public class AutomationEcommerceStats {

	/** The ecommerce. */
	@SerializedName("ecommerce")
	public EcommerceStats ecommerce;
	
	/** The best performing email. */
	@SerializedName("best_performing_email")
	public JsonElement bestPerformingEmail;
}
