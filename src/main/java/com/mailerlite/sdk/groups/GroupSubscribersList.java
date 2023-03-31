package com.mailerlite.sdk.groups;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.susbcribers.Subscriber;
import com.mailerlite.sdk.util.PaginatedResponse;

public class GroupSubscribersList extends PaginatedResponse {

	@SerializedName("data")
	public Subscriber[] subscribers;
	
	public void postDeserialise()
	{
		for (Subscriber s : subscribers) {
			s.parseDates();
		}
	}
}
