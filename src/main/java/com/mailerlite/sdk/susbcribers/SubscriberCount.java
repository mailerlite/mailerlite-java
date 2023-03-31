package com.mailerlite.sdk.susbcribers;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.MailerLiteResponse;

public class SubscriberCount extends MailerLiteResponse {

	@SerializedName("total")
	public int count;
}
