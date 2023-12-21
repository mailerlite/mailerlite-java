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

/**
 * The Class CampaignSubscriberActivity.
 */
public class CampaignSubscriberActivity {

	/** The id. */
	@SerializedName("id")
	public String id;
	
	/** The opens count. */
	@SerializedName("opens_count")
	public int opensCount;
	
	/** The clicks count. */
	@SerializedName("clicks_count")
	public int clicksCount;
	
	/** The subscriber. */
	@SerializedName("subscriber")
	public Subscriber subscriber;
	
	
	/**
	 * Post deserialize.
	 */
	public void postDeserialize()
	{
		if (subscriber != null) {
			subscriber.parseDates();
		}
	}
}
