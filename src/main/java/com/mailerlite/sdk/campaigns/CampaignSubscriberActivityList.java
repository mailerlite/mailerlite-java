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

public class CampaignSubscriberActivityList extends PaginatedResponse {
	
	@SerializedName("data")
	public CampaignSubscriberActivity[] activity;
	
	@Override
	public void postDeserialize()
	{
		for (CampaignSubscriberActivity act : activity) {
			act.postDeserialize();
		}
	}
}
