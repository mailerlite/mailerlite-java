package com.mailerlite.sdk.util;

import com.google.gson.annotations.SerializedName;

public class ResponseMetaLink {

	@SerializedName("url")
	public String url;
	
	@SerializedName("label")
	public String label;
	
	@SerializedName("active")
	public Boolean active;
}
