/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.steps;

import com.google.gson.annotations.SerializedName;

/**
 * The Class DelayStep.
 */
public class DelayStep extends AutomationStep {

	/** The unit. */
	@SerializedName("unit")
	public String unit;

}
