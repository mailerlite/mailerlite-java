/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.triggers;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.automations.TriggerData;

/**
 * The Class AutomationTriggerUpdate.
 */
public class AutomationTriggerUpdate {

	/** The trigger. */
	@SerializedName("trigger")
	public AutomationTrigger trigger;
	
	/** The trigger data. */
	@SerializedName("trigger_data")
	public TriggerData triggerData;
	
	/** The qualified subscribers count. Only set for group and segment triggers. */
	@SerializedName("qualified_subscribers_count")
	public Integer qualifiedSubscribersCount;
}
