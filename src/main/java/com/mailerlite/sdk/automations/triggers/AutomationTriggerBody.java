/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.triggers;

import java.util.Map;

import com.google.gson.annotations.SerializedName;

/**
 * The Class AutomationTriggerBody.
 */
public class AutomationTriggerBody {

	/** The type. */
	@SerializedName("type")
	public String type;
	
	/** The data. */
	@SerializedName("data")
	public Map<String, Object> data;
}
