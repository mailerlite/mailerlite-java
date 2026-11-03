/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.susbcribers;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.MailerLiteResponse;

/**
 * The Class SubscriberActivityLogList.
 */
public class SubscriberActivityLogList extends MailerLiteResponse {

	/** The entries. */
	@SerializedName("data")
	public SubscriberActivityLogEntry[] entries;
	
	/**
	 * Post deserialize.
	 */
	@Override
	public void postDeserialize()
	{
		if (entries == null) {
			return;
		}
		
		for (SubscriberActivityLogEntry e : entries) {
			e.parseDates();
		}
	}
}
