/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.steps.action;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.groups.Group;

/**
 * The Class MoveToGroupAction.
 */
public class MoveToGroupAction extends ActionStep {

	/** The from group ids. */
	@SerializedName("from_group_ids")
	public String[] fromGroupIds;
	
	/** The to group ids. */
	@SerializedName("to_group_ids")
	public String[] toGroupIds;
	
	/** The from groups. */
	@SerializedName("from_groups")
	public Group[] fromGroups;
	
	/** The to groups. */
	@SerializedName("to_groups")
	public Group[] toGroups;
}
