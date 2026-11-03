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
 * The Class StepRunCount.
 */
public class StepRunCount {

	/** The step id. */
	@SerializedName("step_id")
	public String stepId;
	
	/** The completed step runs. */
	@SerializedName("completed_step_runs")
	public int completedStepRuns;
	
	/** The in queue step runs. */
	@SerializedName("in_queue_step_runs")
	public int inQueueStepRuns;
}
