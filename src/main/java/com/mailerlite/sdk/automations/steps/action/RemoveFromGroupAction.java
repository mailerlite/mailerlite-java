package com.mailerlite.sdk.automations.steps.action;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.groups.Group;

public class RemoveFromGroupAction extends ActionStep {

	@SerializedName("group_ids")
	public String[] groupIds;
	
	@SerializedName("groups")
	public Group[] groups;
}
