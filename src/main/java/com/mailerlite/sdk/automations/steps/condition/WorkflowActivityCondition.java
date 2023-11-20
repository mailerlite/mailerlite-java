package com.mailerlite.sdk.automations.steps.condition;

import com.google.gson.annotations.SerializedName;

public class WorkflowActivityCondition extends ConditionBase {

	@SerializedName("email_id")
	public String emailId;
	
	@SerializedName("action")
	public String action;
	
	@SerializedName("link_id")
	public String linkId;
	
	@SerializedName("email")
	public WorkflowConditionIdName email;
	
	@SerializedName("link")
	public WorkflowConditionIdName link;
}