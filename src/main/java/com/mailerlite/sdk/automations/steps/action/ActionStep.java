/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.steps.action;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.automations.steps.AutomationStep;

/**
 * The Class ActionStep.
 */
public class ActionStep extends AutomationStep {

	/** The action type. */
	@SerializedName("action_type")
	public String actionType;
}
