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
 * The Class WorkflowActivityCondition.
 */
public class WorkflowActivityCondition extends ConditionBase {

	/** The email id. */
	@SerializedName("email_id")
	public String emailId;
	
	/** The action. */
	@SerializedName("action")
	public String action;
	
	/** The link id. */
	@SerializedName("link_id")
	public String linkId;
	
	/** The email. */
	@SerializedName("email")
	public WorkflowConditionIdName email;
	
	/** The link. */
	@SerializedName("link")
	public WorkflowConditionIdName link;
}
