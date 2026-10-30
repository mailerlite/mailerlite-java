/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.reports;

import com.google.gson.annotations.SerializedName;

/**
 * The Class TriggersRunCounts.
 */
public class TriggersRunCounts {

	/** The triggers. */
	@SerializedName("triggers")
	public TriggerRunCount[] triggers;
	
	/** The manual runs. */
	@SerializedName("manual_runs")
	public ManualRunCount manualRuns;
}
