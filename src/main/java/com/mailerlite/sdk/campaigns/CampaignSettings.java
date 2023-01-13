package com.mailerlite.sdk.campaigns;

import com.google.gson.annotations.SerializedName;

public class CampaignSettings {

	@SerializedName("track_opens")
	public String trackOpens;
	
	@SerializedName("use_google_analytics")
	public String useGoogleAnalytics;
	
	@SerializedName("ecommerce_tracking")
	public String ecommerceTracking;
}
