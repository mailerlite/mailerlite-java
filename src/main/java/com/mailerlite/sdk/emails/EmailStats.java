/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.emails;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.FloatStat;

public class EmailStats {

	@SerializedName("sent")
	public int sent;
	
	@SerializedName("opens_count")
	public int opensCount;
	
	@SerializedName("unique_opens_count")
	public int uniqueOpensCount;
	
	@SerializedName("open_rate")
	public FloatStat openRate;
	
	@SerializedName("clicks_count")
	public int clicksCount;
	
	@SerializedName("unique_clicks_count")
	public int uniqueClicksCount;
	
	@SerializedName("click_rate")
	public FloatStat clickRate;
	
	@SerializedName("unsubscribes_count")
	public int unsubscribesCount;
	
	@SerializedName("unsubscribe_rate")
	public FloatStat unsubscribeRate;
	
	@SerializedName("spam_count")
	public int spamCount;
	
	@SerializedName("spam_rate")
	public FloatStat spamRate;
	
	@SerializedName("hard_bounces_count")
	public int hardBouncesCount;
	
	@SerializedName("hard_bounce_rate")
	public FloatStat hardBounceRate;
	
	@SerializedName("soft_bounces_count")
	public int softBouncesCount;
	
	@SerializedName("soft_bounce_rate")
	public FloatStat softBounceRate;
	
	@SerializedName("forwards_count")
	public int forwardsCount;
}
