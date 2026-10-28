/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.ecommerce;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteResponse;
import com.mailerlite.sdk.exceptions.MailerLiteException;

/**
 * The Class Orders.
 */
public class Orders {

	private MailerLite apiObjectReference;

	private String shopId;

	public Orders(MailerLite apiRef, String shopId)
	{
		apiObjectReference = apiRef;
		this.shopId = shopId;
	}

	public OrderRetriever retriever()
	{
		return new OrderRetriever(apiObjectReference, shopId);
	}

	public OrderBuilder builder()
	{
		return new OrderBuilder(apiObjectReference, shopId);
	}

	public MailerLiteResponse delete(String orderId) throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).deleteRequest(EcommerceApi.shopPath(shopId, "/orders/").concat(orderId), MailerLiteResponse.class);
	}
}
