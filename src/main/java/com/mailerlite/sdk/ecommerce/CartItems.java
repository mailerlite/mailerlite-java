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
 * The Class CartItems.
 */
public class CartItems {

	private MailerLite apiObjectReference;

	private String shopId;

	private String cartId;

	public CartItems(MailerLite apiRef, String shopId, String cartId)
	{
		apiObjectReference = apiRef;
		this.shopId = shopId;
		this.cartId = cartId;
	}

	public CartItemRetriever retriever()
	{
		return new CartItemRetriever(apiObjectReference, shopId, cartId);
	}

	public CartItemBuilder builder()
	{
		return new CartItemBuilder(apiObjectReference, shopId, cartId);
	}

	public MailerLiteResponse delete(String itemId) throws MailerLiteException
	{
		String endpoint = EcommerceApi.shopPath(shopId, "/carts/").concat(cartId).concat("/items/").concat(itemId);
		return EcommerceApi.create(apiObjectReference).deleteRequest(endpoint, MailerLiteResponse.class);
	}
}
