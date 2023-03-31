package com.mailerlite.sdk.susbcribers;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.annotations.SerializedName;

public class SubscriberBuilderBody {

	@SerializedName("email")
	public String email;
	
	@SerializedName("fields")
	public HashMap<String, Object> fields;
	
	@SerializedName("groups")
	public ArrayList<String> groups;
	
	@SerializedName("status")
	public String status;
	
	@SerializedName("subscribed_at")
	public String subscribedAt;
	
	@SerializedName("ip_address")
	public String ipAddress;
	
	@SerializedName("opted_in_at")
	public String optedInAt;
	
	@SerializedName("optin_ip")
	public String optinIp;
	
	@SerializedName("unsubscribed_at")
	public String unsubscribedAt;
}
