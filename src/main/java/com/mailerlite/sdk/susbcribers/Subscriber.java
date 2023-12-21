/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.susbcribers;

import java.util.Date;
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
	public Date subscribedAt;
	
	@SerializedName("unsubscribed_at")
	public String unsubscribedAtStr;
	
	@ParseDate("unsubscribedAtStr")
	public Date unsubscribedAt;
	
	@SerializedName("created_at")
	public String createdAtStr;
	
	@ParseDate("createdAtStr")
	public Date createdAt;
	
	@SerializedName("updated_at")
	public String updatedAtStr;
	
	@ParseDate("updatedAtStr")
	public Date updatedAt;
	
	@SerializedName("opted_in_at")
	public String optedInAtStr;
	
	@ParseDate("optedInAtStr")
	public Date optedInAt;
	
	@SerializedName("optin_ip")
	public String optinIp;
	
	@SerializedName("fields")
	public HashMap<String, Object> fields = new HashMap<String, Object>();
}
