/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.groups;

import java.time.LocalDateTime;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.ApiResource;
import com.mailerlite.sdk.util.ParseDate;
import com.mailerlite.sdk.util.RateValue;

public class Group extends ApiResource {

	@SerializedName("id")
	public String id;
	
	@SerializedName("name")
	public String name;
	
	@SerializedName("active_count")
	public int activeCount;
	
	@SerializedName("sent_count")
	public int sentCount;
	
	@SerializedName("opens_count")
	public int opensCount;
	
	@SerializedName("open_rate")
	public RateValue openRate;
	
	@SerializedName("clicks_count")
	public int clicksCount;
	
	@SerializedName("click_rate")
	public RateValue clickRate;
	
	@SerializedName("unsubscribed_count")
	public int unsubscribedCount;
	
	@SerializedName("unconfirmed_count")
	public int unconfirmedCount;
	
	@SerializedName("bounced_count")
	public int bouncedCount;
	
	@SerializedName("junk_count")
	public int junkCount;
	
	@SerializedName("created_at")
	public String createdAtStr;
	
	@ParseDate("createdAtStr")
	public LocalDateTime createdAt;
}
