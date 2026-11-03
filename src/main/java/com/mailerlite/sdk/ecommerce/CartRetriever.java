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
 * The Class CartRetriever.
 */
public class CartRetriever extends EcommerceRequest<CartRetriever> {

	private MailerLite apiObjectReference;

	private String shopId;

	public CartRetriever(MailerLite apiRef, String shopId)
	{
		apiObjectReference = apiRef;
		this.shopId = shopId;
	}


	public CartsList get() throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).getRequest(EcommerceApi.shopPath(shopId, "/carts").concat(this.getQueryParameters()), CartsList.class);
	}

	public SingleCart getSingle(String cartId) throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).getRequest(EcommerceApi.shopPath(shopId, "/carts").concat("/").concat(cartId), SingleCart.class);
	}

	@Override
	protected CartRetriever getInstance() {
		return this;
	}
}
