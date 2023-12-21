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
 * The Class CampaignsList.
 */
public class CampaignsList extends PaginatedResponse {

	/** The campaigns. */
	@SerializedName("data")
	public Campaign[] campaigns;
	
	
	/**
	 * Post deserialize.
	 */
	public void postDeserialize()
	{
		for (Campaign c : campaigns) {
			
			c.parseDates();
		}
	}
}
