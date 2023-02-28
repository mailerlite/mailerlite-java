package com.mailerlite.sdk.susbcribers;

import java.time.LocalDateTime;
import java.util.HashMap;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.ApiResource;
import com.mailerlite.sdk.util.ParseDate;

public class Subscriber extends ApiResource {

	@SerializedName("id")
	public String id;
	
	@SerializedName("email")
	public String email;
	
	@SerializedName("status")
	public String status;
	
	@SerializedName("source")
	public String source;
	
	@SerializedName("sent")
	public int sent;
	
	@SerializedName("opens_count")
	public int opensCount;
	
	@SerializedName("clicks_count")
	public int clicksCount;
	
	@SerializedName("open_rate")
	public float openRate;
	
	@SerializedName("click_rate")
	public float clickRate;
	
	@SerializedName("ip_address")
	public String ipAddress;
	
	@SerializedName("subscribed_at")
	public String subscribedAtStr;
	
	@ParseDate("subscribedAtStr")
	public LocalDateTime subscribedAt;
	
	@SerializedName("unsubscribed_at")
	public String unsubscribedAtStr;
	
	@ParseDate("unsubscribedAtStr")
	public LocalDateTime unsubscribedAt;
	
	@SerializedName("created_at")
	public String createdAtStr;
	
	@ParseDate("createdAtStr")
	public LocalDateTime createdAt;
	
	@SerializedName("updated_at")
	public String updatedAtStr;
	
	@ParseDate("updatedAtStr")
	public LocalDateTime updatedAt;
	
	@SerializedName("opted_in_at")
	public String optedInAtStr;
	
	@ParseDate("optedInAtStr")
	public LocalDateTime optedInAt;
	
	@SerializedName("optin_ip")
	public String optinIp;
	
	@SerializedName("fields")
	public HashMap<String, Object> fields = new HashMap<String, Object>();
}
