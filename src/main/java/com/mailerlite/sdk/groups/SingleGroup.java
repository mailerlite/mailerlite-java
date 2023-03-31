package com.mailerlite.sdk.groups;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.MailerLiteResponse;

public class SingleGroup extends MailerLiteResponse {

	@SerializedName("data")
	public Group group;
}
