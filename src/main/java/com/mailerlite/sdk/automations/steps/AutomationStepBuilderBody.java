/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.steps;

import java.util.Map;

import com.google.gson.annotations.SerializedName;

/**
 * The Class AutomationStepBuilderBody.
 */
public class AutomationStepBuilderBody {

	/** The type. */
	@SerializedName("type")
	public String type;
	
	/** The parent id. */
	@SerializedName("parent_id")
	public String parentId;
	
	/** The branch. */
	@SerializedName("branch")
	public String branch;
	
	/** The move to branch. */
	@SerializedName("move_to_branch")
	public String moveToBranch;
	
	/** The data. */
	@SerializedName("data")
	public Map<String, Object> data;
	
	/** The reschedule waiting subscribers. */
	@SerializedName("reschedule_waiting_subscribers")
	public Boolean rescheduleWaitingSubscribers;
}
