/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.campaigns;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.PaginatedResponse;

/**
 * The Class CampaignSubscriberActivityList.
 */
public class CampaignSubscriberActivityList extends PaginatedResponse {
	
	/** The activity. */
	@SerializedName("data")
	public CampaignSubscriberActivity[] activity;
	
	/**
	 * Post deserialize.
	 */
	@Override
	public void postDeserialize()
	{
		for (CampaignSubscriberActivity act : activity) {
			act.postDeserialize();
		}
	}
}
