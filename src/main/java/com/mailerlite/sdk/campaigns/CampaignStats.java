/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.campaigns;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.emails.EmailStats;
import com.mailerlite.sdk.util.FloatStat;

public class CampaignStats extends EmailStats {
	
	@SerializedName("click_to_open_rate")
	public FloatStat clickToOpenRate;
	
}
