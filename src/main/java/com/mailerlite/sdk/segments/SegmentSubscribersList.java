package com.mailerlite.sdk.segments;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.MailerLiteResponse;
import com.mailerlite.sdk.susbcribers.Subscriber;

public class SegmentSubscribersList extends MailerLiteResponse {

	@SerializedName("data")
	public Subscriber[] subscribers;
	
	@SerializedName("meta")
	public SegmentsSubscriberMeta meta;
	
	public void postDeserialize()
	{
		for (Subscriber s : subscribers) {
			s.parseDates();
		}
	}
}
