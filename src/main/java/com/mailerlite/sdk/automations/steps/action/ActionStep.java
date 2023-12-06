package com.mailerlite.sdk.automations.steps.action;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.automations.steps.AutomationStep;

public class ActionStep extends AutomationStep {

	@SerializedName("action_type")
	public String actionType;
}
