/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.susbcribers;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.CursorPaginatedResponse;

/**
 * The Class SubscribersList.
 */
public class SubscribersList extends CursorPaginatedResponse {

	/** The subscribers. */
	@SerializedName("data")
	public Subscriber[] subscribers;
		
	/**
	 * Post deserialize.
	 */
	public void postDeserialize()
	{
		for (Subscriber s : subscribers) {
			
			s.parseDates();
		}
	}
}
