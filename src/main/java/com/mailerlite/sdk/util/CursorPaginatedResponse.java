package com.mailerlite.sdk.util;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.MailerLiteResponse;

public class CursorPaginatedResponse extends MailerLiteResponse {

	@SerializedName("links")
	public ResponseLinks links;
	
	@SerializedName("meta")
	public CursorResponseMeta meta;
}
