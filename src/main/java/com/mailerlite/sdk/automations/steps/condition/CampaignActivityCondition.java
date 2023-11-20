package com.mailerlite.sdk.automations.steps.condition;

import com.google.gson.annotations.SerializedName;

public class CampaignActivityCondition extends ConditionBase {

	@SerializedName("campaign_id")
	public String campaignId;
	
	@SerializedName("action")
	public String action;
	
	@SerializedName("link_id")
	public String linkId;
	
	@SerializedName("campaign")
	public WorkflowConditionIdName campaign;
	
	@SerializedName("link")
	public WorkflowConditionIdName link;
}
