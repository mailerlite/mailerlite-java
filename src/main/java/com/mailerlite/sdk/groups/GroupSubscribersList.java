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
	 * Parses the dates of every item.
	 */
	@Override
	public void postDeserialize()
	{
		if (subscribers == null) {
			return;
		}
		
		for (Subscriber s : subscribers) {
			s.parseDates();
		}
	}
	
	/**
	 * Post deserialise.
	 *
	 * @deprecated misspelled, use {@link #postDeserialize()}
	 */
	@Deprecated
	public void postDeserialise()
	{
		postDeserialize();
	}
}
