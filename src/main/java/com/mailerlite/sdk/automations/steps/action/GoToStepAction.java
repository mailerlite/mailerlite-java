/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.steps.action;

import com.google.gson.annotations.SerializedName;

/**
 * The Class GoToStepAction.
 */
public class GoToStepAction extends ActionStep {

	/** The go to step type. */
	@SerializedName("go_to_step_type")
	public String goToStepType;
	
	/** The go to step id. */
	@SerializedName("go_to_step_id")
	public String goToStepId;
}
