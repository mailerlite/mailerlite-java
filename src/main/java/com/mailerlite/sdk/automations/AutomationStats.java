package com.mailerlite.sdk.automations;

import com.google.gson.annotations.SerializedName;

public class AutomationStats {

	public int completedSubscribersCount;
	
	public int subscribersInQueueCount;
	
	@SerializedName("bounce_rate")
	public FloatStringValue bounceRate;
	
	@SerializedName("click_to_open_rate")
	public FloatStringValue clickToOpenRate;
	
	@SerializedName("sent")
	public int sent;
	
	@SerializedName("opens_count")
	public int opensCount;
	
	@SerializedName("unique_opens_count")
	public int uniqueOpensCount;
	
	@SerializedName("open_rate")
	public FloatStringValue openRate;
	
	@SerializedName("clicks_count")
	public int clicksCount;
	
	@SerializedName("unique_clicks_count")
	public int uniqueClicksCount;
	
	@SerializedName("click_rate")
	public FloatStringValue clickRate;
	
	@SerializedName("unsubscribes_count")
	public int unsubscribesCount;
	
	@SerializedName("unsubscribe_rate")
	public FloatStringValue unsubscribeRate;
	
	@SerializedName("spam_count")
	public int spamCount;
	
	@SerializedName("spam_rate")
	public FloatStringValue spamRate;
	
	@SerializedName("hard_bounces_count")
	public int hardBouncesCount;
	
	@SerializedName("hard_bounce_rate")
	public FloatStringValue hardBounceRate;
	
	@SerializedName("soft_bounces_count")
	public int softBouncesCount;
	
	@SerializedName("soft_bounce_rate")
	public FloatStringValue softBounceRate;
}
