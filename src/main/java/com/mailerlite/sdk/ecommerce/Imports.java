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
import java.util.HashMap;
import java.util.List;

/**
 * The Class Imports.
 */
public class Imports {

	private MailerLite apiObjectReference;

	private String shopId;

	public Imports(MailerLite apiRef, String shopId)
	{
		apiObjectReference = apiRef;
		this.shopId = shopId;
	}

	public MailerLiteResponse products(List<ProductBuilderBody> products) throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).postRequest(EcommerceApi.shopPath(shopId, "/products/import"), EcommerceApi.json(products), MailerLiteResponse.class);
	}

	public MailerLiteResponse categories(List<CategoryBuilderBody> categories) throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).postRequest(EcommerceApi.shopPath(shopId, "/categories/import"), EcommerceApi.json(categories), MailerLiteResponse.class);
	}

	public MailerLiteResponse customers(List<CustomerBuilderBody> customers) throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).postRequest(EcommerceApi.shopPath(shopId, "/customers/import"), EcommerceApi.json(customers), MailerLiteResponse.class);
	}

	public MailerLiteResponse orders(List<OrderBuilderBody> orders) throws MailerLiteException
	{
		HashMap<String, Object> body = new HashMap<String, Object>();
		body.put("orders", orders);

		return EcommerceApi.create(apiObjectReference).postRequest(EcommerceApi.shopPath(shopId, "/orders/import"), EcommerceApi.json(body), MailerLiteResponse.class);
	}
}
