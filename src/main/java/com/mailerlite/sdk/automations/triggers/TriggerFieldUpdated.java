/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.triggers;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.fields.Field;

/**
 * The Class TriggerFieldUpdated.
 */
public class TriggerFieldUpdated extends AutomationTrigger{

	/** The field id. */
	@SerializedName("field_id")
	public String fieldId;
	
	/** The field. */
	@SerializedName("field")
	public Field field;
	
	/** The value. */
	@SerializedName("value")
	public String value;
	
	/** The field operator. */
	@SerializedName("field_operator")
	public String fieldOperator;
}
