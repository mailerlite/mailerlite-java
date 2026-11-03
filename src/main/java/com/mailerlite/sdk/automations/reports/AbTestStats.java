/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.reports;

import java.util.HashMap;

import com.google.gson.annotations.SerializedName;

/**
 * The Class AbTestStats.
 */
public class AbTestStats {

	/** The step id. */
	@SerializedName("step_id")
	public String stepId;
	
	/** The name. */
	@SerializedName("name")
	public String name;
	
	/** The ab test winner. */
	@SerializedName("ab_test_winner")
	public String abTestWinner;
	
	/** The parent id. */
	@SerializedName("parent_id")
	public String parentId;
	
	/** The can select winner. */
	@SerializedName("can_select_winner")
	public Boolean canSelectWinner;
	
	/** The a path step id. */
	@SerializedName("a_path_step_id")
	public String aPathStepId;
	
	/** The b path step id. */
	@SerializedName("b_path_step_id")
	public String bPathStepId;
	
	/** The c path step id. */
	@SerializedName("c_path_step_id")
	public String cPathStepId;
	
	/** The split percentages. */
	@SerializedName("split_percentages")
	public AbTestSplit[] splitPercentages;
	
	/** The stats. */
	@SerializedName("stats")
	public HashMap<String, AbTestVariantStats> stats;
}
