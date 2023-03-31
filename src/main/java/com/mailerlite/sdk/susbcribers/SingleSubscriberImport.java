package com.mailerlite.sdk.susbcribers;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.MailerLiteResponse;

public class SingleSubscriberImport extends MailerLiteResponse {

	@SerializedName("data")
	public SubscriberImport subscriberImport;
	
	public void postDesirialize()
	{
		subscriberImport.parseDates();
	}
}
