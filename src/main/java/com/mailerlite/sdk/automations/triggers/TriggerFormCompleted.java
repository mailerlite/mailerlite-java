/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.triggers;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.forms.Form;

public class TriggerFormCompleted extends AutomationTrigger {

	@SerializedName("form_id")
	public String formId;
	
	@SerializedName("form_type")
	public String formType;
	
	@SerializedName("form")
	public Form form;
}
