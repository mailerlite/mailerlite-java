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
 * The Class GroupMembershipCondition.
 */
public class GroupMembershipCondition extends ConditionBase {

	/** The group id. */
	@SerializedName("group_id")
	public String groupId;
	
	/** The group. */
	@SerializedName("group")
	public WorkflowConditionIdName group;
}
