package com.mailerlite.sdk.webhooks;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.MailerLiteResponse;

public class SingleWebhook  extends MailerLiteResponse {

	@SerializedName("data")
	public Webhook webhook;
}
