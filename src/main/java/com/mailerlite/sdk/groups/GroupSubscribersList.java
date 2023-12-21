/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.groups;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.susbcribers.Subscriber;
import com.mailerlite.sdk.util.PaginatedResponse;

/**
 * The Class GroupSubscribersList.
 */
public class GroupSubscribersList extends PaginatedResponse {

	/** The subscribers. */
	@SerializedName("data")
	public Subscriber[] subscribers;
	
	/**
	 * Post deserialise.
	 */
	public void postDeserialise()
	{
		for (Subscriber s : subscribers) {
			s.parseDates();
		}
	}
}
