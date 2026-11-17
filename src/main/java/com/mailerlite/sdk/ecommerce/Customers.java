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
 * The Class Customers.
 */
public class Customers {

	private MailerLite apiObjectReference;

	private String shopId;

	public Customers(MailerLite apiRef, String shopId)
	{
		apiObjectReference = apiRef;
		this.shopId = shopId;
	}

	public CustomerRetriever retriever()
	{
		return new CustomerRetriever(apiObjectReference, shopId);
	}

	public CustomerBuilder builder()
	{
		return new CustomerBuilder(apiObjectReference, shopId);
	}

	public MailerLiteResponse delete(String customerId) throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).deleteRequest(EcommerceApi.shopPath(shopId, "/customers/").concat(customerId), MailerLiteResponse.class);
	}
}
