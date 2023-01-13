package com.mailerlite.sdk.campaigns;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.MailerLiteResponse;

public class CampaignsList extends MailerLiteResponse {

	@SerializedName("data")
	public Campaign[] campaigns;
	
	
	public void postDeserialize()
	{
		for (Campaign c : campaigns) {
			
			c.parseDates();
		}
	}
}
