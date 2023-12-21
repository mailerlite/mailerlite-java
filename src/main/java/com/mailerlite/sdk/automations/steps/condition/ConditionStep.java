/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.steps.condition;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.automations.steps.AutomationStep;

/**
 * The Class ConditionStep.
 */
public class ConditionStep extends AutomationStep {

	/** The yes step id. */
	@SerializedName("yes_step_id")
	public String yesStepId;
	
	/** The no step id. */
	@SerializedName("no_step_id")
	public String noStepId;
	
	/** The matching type. */
	@SerializedName("matching_type")
	public String matchingType;
	
	
	/** The conditions. */
	public transient Object[] conditions;
}
