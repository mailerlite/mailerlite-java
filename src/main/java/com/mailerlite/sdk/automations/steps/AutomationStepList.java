/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.steps;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.MailerLiteResponse;

/**
 * The Class AutomationStepList.
 */
public class AutomationStepList extends MailerLiteResponse {

	/** The steps. */
	@SerializedName("data")
	public AutomationStep[] steps;
}
