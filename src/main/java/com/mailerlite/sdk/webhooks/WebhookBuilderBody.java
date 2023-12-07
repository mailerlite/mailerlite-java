package com.mailerlite.sdk.webhooks;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class WebhookBuilderBody {

	@SerializedName("name")
	public String name;
	
	@SerializedName("events")
	public ArrayList<String> events = new ArrayList<String>();
	
	@SerializedName("url")
	public String url;
	
	@SerializedName("enabled")
	public Boolean enabled;
}
