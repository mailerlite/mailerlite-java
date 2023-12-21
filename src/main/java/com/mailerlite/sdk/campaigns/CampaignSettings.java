/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.campaigns;

import com.google.gson.annotations.SerializedName;

/**
 * The Class CampaignSettings.
 */
public class CampaignSettings {

	/** The track opens. */
	@SerializedName("track_opens")
	public String trackOpens;
	
	/** The use google analytics. */
	@SerializedName("use_google_analytics")
	public String useGoogleAnalytics;
	
	/** The ecommerce tracking. */
	@SerializedName("ecommerce_tracking")
	public String ecommerceTracking;
}
