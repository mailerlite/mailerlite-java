/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.reports;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.PaginatedResponse;

/**
 * The Class RunSubscriberList.
 */
public class RunSubscriberList extends PaginatedResponse {

	/** The subscribers. */
	@SerializedName("data")
	public RunSubscriber[] subscribers;
	
	/**
	 * Post deserialize.
	 */
	@Override
	public void postDeserialize()
	{
		if (subscribers == null) {
			return;
		}
		
		for (RunSubscriber s : subscribers) {
			s.parseDates();
		}
	}
}
