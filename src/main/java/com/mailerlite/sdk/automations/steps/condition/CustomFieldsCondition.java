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
 * The Class CustomFieldsCondition.
 */
public class CustomFieldsCondition extends ConditionBase {

	/** The field id. */
	@SerializedName("field_id")
	public String fieldId;
	
	/** The action. */
	@SerializedName("action")
	public String action;
	
	/** The action value. */
	@SerializedName("action_value")
	public String actionValue;
	
	/** The field. */
	@SerializedName("field")
	public WorkflowConditionIdName field;
}
