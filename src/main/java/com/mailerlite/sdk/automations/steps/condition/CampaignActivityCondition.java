/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.steps.condition;

import com.google.gson.annotations.SerializedName;

/**
 * The Class CampaignActivityCondition.
 */
public class CampaignActivityCondition extends ConditionBase {

	/** The campaign id. */
	@SerializedName("campaign_id")
	public String campaignId;
	
	/** The action. */
	@SerializedName("action")
	public String action;
	
	/** The link id. */
	@SerializedName("link_id")
	public String linkId;
	
	/** The campaign. */
	@SerializedName("campaign")
	public WorkflowConditionIdName campaign;
	
	/** The link. */
	@SerializedName("link")
	public WorkflowConditionIdName link;
}
