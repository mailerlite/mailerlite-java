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

/**
 * The Class TriggerSubscriberJoinsGroup.
 */
public class TriggerSubscriberJoinsGroup extends AutomationTrigger {

	/** The group ids. */
	@SerializedName("group_ids")
	public String[] groupIds;
	
	/** The groups. */
	@SerializedName("groups")
	public Group[] groups;
	
	/** The exclude group ids. */
	@SerializedName("exclude_group_ids")
	public String[] excludeGroupIds;
	
	/** The excluded groups. */
	@SerializedName("excluded_groups")
	public Group[] excludedGroups;
	
}
