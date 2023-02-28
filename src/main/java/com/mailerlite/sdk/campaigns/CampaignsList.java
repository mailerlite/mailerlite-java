package com.mailerlite.sdk.campaigns;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.PaginatedResponse;

public class CampaignsList extends PaginatedResponse {

	@SerializedName("data")
	public Campaign[] campaigns;
	
	
	public void postDeserialize()
	{
		for (Campaign c : campaigns) {
			
			c.parseDates();
		}
	}
}
