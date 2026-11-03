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
 * The Class OrderActivityList.
 */
public class OrderActivityList extends PaginatedResponse {

	/** The orders. */
	@SerializedName("data")
	public OrderActivity[] orders;
	
	/**
	 * Post deserialize.
	 */
	@Override
	public void postDeserialize()
	{
		if (orders == null) {
			return;
		}
		
		for (OrderActivity o : orders) {
			o.parseDates();
		}
	}
}
