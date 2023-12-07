package com.mailerlite.sdk.webhooks;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.PaginatedResponse;

public class WebhooksList extends PaginatedResponse  {


	@SerializedName("data")
	public Webhook[] webhooks;
	
	public void postDeserialise()
	{
		for (Webhook w : webhooks) {
			w.parseDates();
		}
	}
}
