package com.mailerlite.sdk.fields;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.MailerLiteResponse;

public class SingleField extends MailerLiteResponse {

	@SerializedName("data")
	public Field field;
}
