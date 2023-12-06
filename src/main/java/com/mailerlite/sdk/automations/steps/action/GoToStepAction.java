package com.mailerlite.sdk.automations.steps.action;

import com.google.gson.annotations.SerializedName;

public class GoToStepAction extends ActionStep {

	@SerializedName("go_to_step_type")
	public String goToStepType;
	
	@SerializedName("go_to_step_id")
	public String goToStepId;
}
