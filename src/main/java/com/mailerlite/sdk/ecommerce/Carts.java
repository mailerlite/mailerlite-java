/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.ecommerce;

import com.mailerlite.sdk.MailerLite;

/**
 * The Class Carts.
 */
public class Carts {

	private MailerLite apiObjectReference;

	private String shopId;

	public Carts(MailerLite apiRef, String shopId)
	{
		apiObjectReference = apiRef;
		this.shopId = shopId;
	}

	public CartRetriever retriever()
	{
		return new CartRetriever(apiObjectReference, shopId);
	}

	public CartBuilder builder()
	{
		return new CartBuilder(apiObjectReference, shopId);
	}

	public CartItems items(String cartId)
	{
		return new CartItems(apiObjectReference, shopId, cartId);
	}
}
