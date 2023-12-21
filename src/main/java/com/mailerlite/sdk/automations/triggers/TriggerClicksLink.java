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
 * The Class TriggerClicksLink.
 */
public class TriggerClicksLink extends AutomationTrigger {

	/** The link. */
	@SerializedName("link")
	public String link;
}
