package com.mailerlite.sdk.forms;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.MailerLiteResponse;

public class SingleForm  extends MailerLiteResponse {

	@SerializedName("data")
	public Form form;
	
}