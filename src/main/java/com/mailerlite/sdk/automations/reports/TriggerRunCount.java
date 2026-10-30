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
 * The Class TriggerRunCount.
 */
public class TriggerRunCount {

	/** The id. */
	@SerializedName("id")
	public String id;
	
	/** The completed runs. */
	@SerializedName("completed_runs")
	public int completedRuns;
	
	/** The queued runs. */
	@SerializedName("queued_runs")
	public int queuedRuns;
}
