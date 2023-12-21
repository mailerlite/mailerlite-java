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

/**
 * The Class TriggerFormCompleted.
 */
public class TriggerFormCompleted extends AutomationTrigger {

	/** The form id. */
	@SerializedName("form_id")
	public String formId;
	
	/** The form type. */
	@SerializedName("form_type")
	public String formType;
	
	/** The form. */
	@SerializedName("form")
	public Form form;
}
