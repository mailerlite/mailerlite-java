/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations;

import com.google.gson.annotations.SerializedName;

/**
 * The Class AutomationReaddBody.
 */
public class AutomationReaddBody {

	/** The readd subscribers to. */
	@SerializedName("readd_subscribers_to")
	public String readdSubscribersTo;
	
	/** The selected step id. */
	@SerializedName("selected_step_id")
	public String selectedStepId;
	
	/** The filter. */
	@SerializedName("filter")
	public AutomationActivityFilter filter;
}
