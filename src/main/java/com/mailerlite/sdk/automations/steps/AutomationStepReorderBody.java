/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.steps;

import com.google.gson.annotations.SerializedName;

/**
 * The Class AutomationStepReorderBody.
 */
public class AutomationStepReorderBody {

	/** The dragged step id. */
	@SerializedName("dragged_step_id")
	public String draggedStepId;
	
	/** The new parent step id. */
	@SerializedName("new_parent_step_id")
	public String newParentStepId;
	
	/** The branch. */
	@SerializedName("branch")
	public String branch;
	
	/** The branch for child. */
	@SerializedName("branch_for_child")
	public String branchForChild;
}
