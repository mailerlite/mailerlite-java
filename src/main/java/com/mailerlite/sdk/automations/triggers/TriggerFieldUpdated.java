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

public class TriggerFieldUpdated extends AutomationTrigger{

	@SerializedName("field_id")
	public String fieldId;
	
	@SerializedName("field")
	public Field field;
	
	@SerializedName("value")
	public String value;
	
	@SerializedName("field_operator")
	public String fieldOperator;
}
