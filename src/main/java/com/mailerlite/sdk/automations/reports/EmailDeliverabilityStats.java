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
 * The Class EmailDeliverabilityStats.
 */
public class EmailDeliverabilityStats {

	/** The sent. */
	@SerializedName("sent")
	public int sent;
	
	/** The delivered count. */
	@SerializedName("delivered_count")
	public int deliveredCount;
	
	/** The delivered rate. */
	@SerializedName("delivered_rate")
	public FloatStringValue deliveredRate;
	
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
	
	/** The spam count. */
	@SerializedName("spam_count")
	public int spamCount;
	
	/** The spam rate. */
	@SerializedName("spam_rate")
	public FloatStringValue spamRate;
	
	/** The unsubscribes count. */
	@SerializedName("unsubscribes_count")
	public int unsubscribesCount;
	
	/** The unsubscribe rate. */
	@SerializedName("unsubscribe_rate")
	public FloatStringValue unsubscribeRate;
}
