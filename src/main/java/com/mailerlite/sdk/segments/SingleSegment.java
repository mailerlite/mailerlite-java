package com.mailerlite.sdk.segments;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.MailerLiteResponse;

public class SingleSegment extends MailerLiteResponse {

	@SerializedName("data")
	public Segment segment;
}
