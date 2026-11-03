/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.reports;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.automations.FloatStringValue;

/**
 * The Class EmailEngagementStats.
 */
public class EmailEngagementStats {

	/** The sent. */
	@SerializedName("sent")
	public int sent;
	
	/** The opens count. */
	@SerializedName("opens_count")
	public int opensCount;
	
	/** The clicks count. */
	@SerializedName("clicks_count")
	public int clicksCount;
	
	/** The total opens count. */
	@SerializedName("total_opens_count")
	public int totalOpensCount;
	
	/** The total clicks count. */
	@SerializedName("total_clicks_count")
	public int totalClicksCount;
	
	/** The open rate. */
	@SerializedName("open_rate")
	public FloatStringValue openRate;
	
	/** The click rate. */
	@SerializedName("click_rate")
	public FloatStringValue clickRate;
	
	/** The click to open rate. */
	@SerializedName("click_to_open_rate")
	public FloatStringValue clickToOpenRate;
	
	/** The forwards count. */
	@SerializedName("forwards_count")
	public int forwardsCount;
	
	/** The forward rate. */
	@SerializedName("forward_rate")
	public FloatStringValue forwardRate;
	
	/** The social interactions count. */
	@SerializedName("social_interactions_count")
	public int socialInteractionsCount;
	
	/** The social interaction rate. */
	@SerializedName("social_interaction_rate")
	public FloatStringValue socialInteractionRate;
}
