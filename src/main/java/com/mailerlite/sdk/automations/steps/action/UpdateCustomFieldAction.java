/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.steps.action;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.fields.Field;

/**
 * The Class UpdateCustomFieldAction.
 */
public class UpdateCustomFieldAction extends ActionStep {

	/** The field id. */
	@SerializedName("field_id")
	public String fieldId;
	
	/** The field. */
	@SerializedName("field")
	public Field field;
	
	/** The data operation. */
	@SerializedName("data_operation")
	public String dataOperation;
	
	/** The value. */
	@SerializedName("value")
	public String value;
}
