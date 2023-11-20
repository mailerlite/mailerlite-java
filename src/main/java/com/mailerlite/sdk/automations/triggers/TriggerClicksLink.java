package com.mailerlite.sdk.automations.triggers;

import com.google.gson.annotations.SerializedName;

public class TriggerClicksLink extends AutomationTrigger {

	@SerializedName("link")
	public String link;
}