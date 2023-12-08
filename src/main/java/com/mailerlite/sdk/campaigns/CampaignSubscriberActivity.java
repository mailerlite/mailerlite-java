/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.campaigns;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.susbcribers.Subscriber;

public class CampaignSubscriberActivity {

	@SerializedName("id")
	public String id;
	
	@SerializedName("opens_count")
	public int opensCount;
	
	@SerializedName("clicks_count")
	public int clicksCount;
	
	@SerializedName("subscriber")
	public Subscriber subscriber;
	
	
	public void postDeserialize()
	{
		if (subscriber != null) {
			subscriber.parseDates();
		}
	}
}
