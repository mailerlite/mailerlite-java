package com.mailerlite.sdk.susbcribers;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.CursorPaginatedResponse;

public class SubscribersList extends CursorPaginatedResponse {

	@SerializedName("data")
	public Subscriber[] subscribers;
		
	public void postDeserialize()
	{
		for (Subscriber s : subscribers) {
			
			s.parseDates();
		}
	}
}
