/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.webhooks;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.PaginatedResponse;

/**
 * The Class WebhooksList.
 */
public class WebhooksList extends PaginatedResponse {


	/** The webhooks. */
	@SerializedName("data")
	public Webhook[] webhooks;
	
	/**
	 * Parses the dates of every item.
	 */
	@Override
	public void postDeserialize()
	{
		if (webhooks == null) {
			return;
		}
		
		for (Webhook w : webhooks) {
			w.parseDates();
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
