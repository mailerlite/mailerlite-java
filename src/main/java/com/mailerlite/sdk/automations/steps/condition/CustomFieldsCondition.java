package com.mailerlite.sdk.automations.steps.condition;

import com.google.gson.annotations.SerializedName;

public class CustomFieldsCondition extends ConditionBase {

	@SerializedName("field_id")
	public String fieldId;
	
	@SerializedName("action")
	public String action;
	
	@SerializedName("action_value")
	public String actionValue;
	
	@SerializedName("field")
	public WorkflowConditionIdName field;
}
