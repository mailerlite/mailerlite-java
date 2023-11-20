package com.mailerlite.sdk.automations.steps.condition;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.automations.steps.AutomationStep;

public class ConditionStep extends AutomationStep {

	@SerializedName("yes_step_id")
	public String yesStepId;
	
	@SerializedName("no_step_id")
	public String noStepId;
	
	@SerializedName("matching_type")
	public String matchingType;
}
