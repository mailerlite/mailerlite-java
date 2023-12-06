package com.mailerlite.sdk.automations.activity;

import java.time.LocalDateTime;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.ParseDate;

public class StepRun {

	@SerializedName("id")
	public String id;
	
	@SerializedName("step_id")
	public String stepId;
	
	@SerializedName("description")
	public String description;
	
	@SerializedName("scheduled_for")
	public String scheduledForStr;
	
	@ParseDate("scheduledForStr")
	public LocalDateTime scheduledFor;
}