/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.ecommerce;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.exceptions.MailerLiteException;

/**
 * The Class CartItemRetriever.
 */
public class CartItemRetriever extends EcommerceRequest<CartItemRetriever> {

	private MailerLite apiObjectReference;

	private String shopId;
	private String cartId;

	public CartItemRetriever(MailerLite apiRef, String shopId, String cartId)
	{
		apiObjectReference = apiRef;
		this.shopId = shopId;
		this.cartId = cartId;
	}


	public CartItemsList get() throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).getRequest(EcommerceApi.shopPath(shopId, "/carts/").concat(cartId).concat("/items").concat(this.getQueryParameters()), CartItemsList.class);
	}

	public SingleCartItem getSingle(String itemId) throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).getRequest(EcommerceApi.shopPath(shopId, "/carts/").concat(cartId).concat("/items").concat("/").concat(itemId), SingleCartItem.class);
	}

	@Override
	protected CartItemRetriever getInstance() {
		return this;
	}
}
