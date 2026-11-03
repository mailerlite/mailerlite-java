/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.susbcribers;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.PaginatedResponse;

/**
 * The Class SubscriberImportList.
 */
public class SubscriberImportList extends PaginatedResponse {

	/** The imports. */
	@SerializedName("data")
	public SubscriberImport[] imports;
	
	/**
	 * Parses the dates of every item.
	 */
	@Override
	public void postDeserialize()
	{
		if (imports == null) {
			return;
		}
		
		for (SubscriberImport i : imports) {
			i.parseDates();
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
