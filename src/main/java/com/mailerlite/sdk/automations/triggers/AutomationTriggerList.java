/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.triggers;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.MailerLiteResponse;

/**
 * The Class AutomationTriggerList.
 */
public class AutomationTriggerList extends MailerLiteResponse {

	/** The triggers. */
	@SerializedName("data")
	public AutomationTrigger[] triggers;
}
