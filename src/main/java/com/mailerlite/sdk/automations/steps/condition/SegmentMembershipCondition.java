package com.mailerlite.sdk.automations.steps.condition;

import com.google.gson.annotations.SerializedName;

public class SegmentMembershipCondition extends ConditionBase {

	@SerializedName("segment_id")
	public String segmentId;
	
	@SerializedName("segment")
	public WorkflowConditionIdName segment;
}
