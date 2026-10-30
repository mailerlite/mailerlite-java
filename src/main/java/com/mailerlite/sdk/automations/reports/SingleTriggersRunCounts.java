/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.reports;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.MailerLiteResponse;

/**
 * The Class SingleTriggersRunCounts.
 */
public class SingleTriggersRunCounts extends MailerLiteResponse {

	/** The triggers run counts. */
	@SerializedName("data")
	public TriggersRunCounts triggersRunCounts;
}
