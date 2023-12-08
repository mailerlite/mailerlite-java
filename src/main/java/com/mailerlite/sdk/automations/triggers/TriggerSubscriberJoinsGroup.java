/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.triggers;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.groups.Group;

public class TriggerSubscriberJoinsGroup extends AutomationTrigger {

	@SerializedName("group_ids")
	public String[] groupIds;
	
	@SerializedName("groups")
	public Group[] groups;
	
	@SerializedName("exclude_group_ids")
	public String[] excludeGroupIds;
	
	@SerializedName("excluded_groups")
	public Group[] excludedGroups;
	
}
