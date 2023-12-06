package com.mailerlite.sdk.automations.activity;

import java.time.LocalDateTime;

import com.google.gson.annotations.SerializedName;

public class SubscriberActivity {

	@SerializedName("id")
	public String id;
	
	@SerializedName("status")
	public String status;
	
	@SerializedName("date")
	public String dateStr;
	
	public LocalDateTime date;
	
	@SerializedName("reason")
	public String reason;
	
	@SerializedName("reason_description")
	public String reasonDescription;
	
	@SerializedName("stepRuns")
	public StepRun[] stepRuns;
	
	public transient Object nextStep;
	
	public transient Object currentStep;
}
