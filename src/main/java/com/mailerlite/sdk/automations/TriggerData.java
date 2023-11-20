package com.mailerlite.sdk.automations;

import com.google.gson.annotations.SerializedName;

public class TriggerData {

	@SerializedName("track_ecommerce")
	public Boolean trackEcommerce;
	
	@SerializedName("repeatable")
	public Boolean repeatable;
	
	@SerializedName("valid")
	public Boolean valid;
}
