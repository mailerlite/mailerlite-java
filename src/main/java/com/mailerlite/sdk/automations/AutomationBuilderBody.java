/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations;

import java.util.ArrayList;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

/**
 * The Class AutomationBuilderBody.
 */
public class AutomationBuilderBody {

	/** The name. */
	@SerializedName("name")
	public String name;
	
	/** The trigger data. */
	@SerializedName("trigger_data")
	public Map<String, Object> triggerData;
	
	/** The triggers. */
	@SerializedName("triggers")
	public ArrayList<Map<String, Object>> triggers;
}
