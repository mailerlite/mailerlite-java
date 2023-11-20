package com.mailerlite.sdk.automations.steps.condition;

import com.google.gson.annotations.SerializedName;

public class GroupMembershipCondition extends ConditionBase {

	@SerializedName("group_id")
	public String groupId;
	
	@SerializedName("group")
	public WorkflowConditionIdName group;
}
