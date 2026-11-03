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
 * The Class ManualRunCount.
 */
public class ManualRunCount {

	/** The in progress runs. */
	@SerializedName("in_progress_runs")
	public int inProgressRuns;
	
	/** The completed runs. */
	@SerializedName("completed_runs")
	public int completedRuns;
}
