package com.mailerlite.sdk.automations.steps.action;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.groups.Group;

public class MoveToGroupAction extends ActionStep {

	@SerializedName("from_group_ids")
	public String[] fromGroupIds;
	
	@SerializedName("to_group_ids")
	public String[] toGroupIds;
	
	@SerializedName("from_groups")
	public Group[] fromGroups;
	
	@SerializedName("to_groups")
	public Group[] toGroups;
}
