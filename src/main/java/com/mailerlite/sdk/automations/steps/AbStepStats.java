package com.mailerlite.sdk.automations.steps;

import com.google.gson.annotations.SerializedName;

public class AbStepStats {

	@SerializedName("subscribers")
	public int subscribers;
	
	@SerializedName("sent")
	public int sent;
	
	@SerializedName("opens")
	public int opens;
	
	@SerializedName("clicks")
	public int clicks;
}
