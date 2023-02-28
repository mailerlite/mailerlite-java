package com.mailerlite.sdk.campaigns;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.susbcribers.Subscriber;
import com.mailerlite.sdk.util.PaginatedResponse;

public class CampaignSubscriberActivityList extends PaginatedResponse {
	
	@SerializedName("data")
	public CampaignSubscriberActivity[] activity;
	
	@Override
	public void postDeserialize()
	{
		for (CampaignSubscriberActivity act : activity) {
			act.postDeserialize();
		}
	}
}
