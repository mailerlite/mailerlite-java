package com.mailerlite.sdk.susbcribers;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.MailerLiteResponse;

public class SingleSubscriber extends MailerLiteResponse {

	@SerializedName("data")
	public Subscriber subscriber;
}
