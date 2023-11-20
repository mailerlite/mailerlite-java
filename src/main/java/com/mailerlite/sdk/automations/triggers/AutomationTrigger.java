package com.mailerlite.sdk.automations.triggers;

import com.google.gson.annotations.SerializedName;

public abstract class AutomationTrigger {

	@SerializedName("id")
	public String id;
	
	@SerializedName("type")
	public String type;
	
	@SerializedName("broken")
	public Boolean broken;
}
