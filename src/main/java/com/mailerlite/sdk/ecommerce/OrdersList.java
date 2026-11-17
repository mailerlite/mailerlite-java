/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.ecommerce;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.PaginatedResponse;

/**
 * The Class OrdersList.
 */
public class OrdersList extends PaginatedResponse {

	@SerializedName("data")
	public EcommerceOrder[] orders;

	@Override
	public void postDeserialize()
	{
		if (orders == null) {
			return;
		}

		for (EcommerceOrder item : orders) {
			item.parseDates();
		}
	}
}
