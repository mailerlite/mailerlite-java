/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.steps.condition;

import com.google.gson.annotations.SerializedName;

public class GroupMembershipCondition extends ConditionBase {

	@SerializedName("group_id")
	public String groupId;
	
	@SerializedName("group")
	public WorkflowConditionIdName group;
}
