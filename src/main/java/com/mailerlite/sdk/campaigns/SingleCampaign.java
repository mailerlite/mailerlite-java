package com.mailerlite.sdk.campaigns;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.MailerLiteResponse;

public class SingleCampaign extends MailerLiteResponse {

	@SerializedName("data")
	public Campaign campaign;
}
