/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations;

import com.google.gson.annotations.SerializedName;

/**
 * The Class AutomationStats.
 */
public class AutomationStats {

	/** The completed subscribers count. */
	public int completedSubscribersCount;
	
	/** The subscribers in queue count. */
	public int subscribersInQueueCount;
	
	/** The bounce rate. */
	@SerializedName("bounce_rate")
	public FloatStringValue bounceRate;
	
	/** The click to open rate. */
	@SerializedName("click_to_open_rate")
	public FloatStringValue clickToOpenRate;
	
	/** The sent. */
	@SerializedName("sent")
	public int sent;
	
	/** The opens count. */
	@SerializedName("opens_count")
	public int opensCount;
	
	/** The unique opens count. */
	@SerializedName("unique_opens_count")
	public int uniqueOpensCount;
	
	/** The open rate. */
	@SerializedName("open_rate")
	public FloatStringValue openRate;
	
	/** The clicks count. */
	@SerializedName("clicks_count")
	public int clicksCount;
	
	/** The unique clicks count. */
	@SerializedName("unique_clicks_count")
	public int uniqueClicksCount;
	
	/** The click rate. */
	@SerializedName("click_rate")
	public FloatStringValue clickRate;
	
	/** The unsubscribes count. */
	@SerializedName("unsubscribes_count")
	public int unsubscribesCount;
	
	/** The unsubscribe rate. */
	@SerializedName("unsubscribe_rate")
	public FloatStringValue unsubscribeRate;
	
	/** The spam count. */
	@SerializedName("spam_count")
	public int spamCount;
	
	/** The spam rate. */
	@SerializedName("spam_rate")
	public FloatStringValue spamRate;
	
	/** The hard bounces count. */
	@SerializedName("hard_bounces_count")
	public int hardBouncesCount;
	
	/** The hard bounce rate. */
	@SerializedName("hard_bounce_rate")
	public FloatStringValue hardBounceRate;
	
	/** The soft bounces count. */
	@SerializedName("soft_bounces_count")
	public int softBouncesCount;
	
	/** The soft bounce rate. */
	@SerializedName("soft_bounce_rate")
	public FloatStringValue softBounceRate;
}
