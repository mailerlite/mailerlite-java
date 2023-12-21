/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.triggers;

import com.google.gson.annotations.SerializedName;

/**
 * The Class AutomationTrigger.
 */
public abstract class AutomationTrigger {

	/** The id. */
	@SerializedName("id")
	public String id;
	
	/** The type. */
	@SerializedName("type")
	public String type;
	
	/** The broken. */
	@SerializedName("broken")
	public Boolean broken;
}
