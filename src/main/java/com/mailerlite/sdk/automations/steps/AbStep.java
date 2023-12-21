/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.steps;

import java.util.HashMap;

import com.google.gson.annotations.SerializedName;

/**
 * The Class AbStep.
 */
public class AbStep extends AutomationStep {

	/** The name. */
	@SerializedName("name")
	public String name;
	
	/** The a path step id. */
	@SerializedName("a_path_step_id")
	public String aPathStepId;
	
	/** The b path step id. */
	@SerializedName("b_path_step_id")
	public String bPathStepId;
	
	/** The c path step id. */
	@SerializedName("c_path_step_id")
	public String cPathStepId;
	
	/** The ab test winnder. */
	@SerializedName("ab_test_winner")
	public String abTestWinnder;
	
	/** The can select winner. */
	@SerializedName("can_select_winner")
	public Boolean canSelectWinner;
	
	/** The split percentages. */
	@SerializedName("split_percentages")
	public String[] splitPercentages;
	
	/** The stats. */
	@SerializedName("stats")
	public HashMap<String, AbStepStats> stats;
}
