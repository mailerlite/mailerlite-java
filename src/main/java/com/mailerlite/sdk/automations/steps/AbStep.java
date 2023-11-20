package com.mailerlite.sdk.automations.steps;

import java.util.HashMap;

import com.google.gson.annotations.SerializedName;

public class AbStep extends AutomationStep {

	@SerializedName("name")
	public String name;
	
	@SerializedName("a_path_step_id")
	public String aPathStepId;
	
	@SerializedName("b_path_step_id")
	public String bPathStepId;
	
	@SerializedName("c_path_step_id")
	public String cPathStepId;
	
	@SerializedName("ab_test_winner")
	public String abTestWinnder;
	
	@SerializedName("can_select_winner")
	public Boolean canSelectWinner;
	
	@SerializedName("split_percentages")
	public String[] splitPercentages;
	
	@SerializedName("stats")
	public HashMap<String, AbStepStats> stats;
}
