package com.mailerlite.sdk.campaigns;

import java.util.HashMap;

import com.google.gson.annotations.SerializedName;

public class CampaignSchedulerBody {

	@SerializedName("delivery")
	public String delivery;
	
	@SerializedName("schedule")
	public HashMap<String, Object> schedule;
	
	@SerializedName("resend")
	public HashMap<String, Object> resend;
	
	
}
