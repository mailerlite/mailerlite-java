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
 * The Class RemoveFromGroupAction.
 */
public class RemoveFromGroupAction extends ActionStep {

	/** The group ids. */
	@SerializedName("group_ids")
	public String[] groupIds;
	
	/** The groups. */
	@SerializedName("groups")
	public Group[] groups;
}
