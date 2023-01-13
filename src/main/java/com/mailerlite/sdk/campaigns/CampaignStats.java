package com.mailerlite.sdk.campaigns;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.emails.EmailStats;
import com.mailerlite.sdk.util.FloatStat;

public class CampaignStats extends EmailStats {
	
	@SerializedName("click_to_open_rate")
	public FloatStat clickToOpenRate;
	
}
