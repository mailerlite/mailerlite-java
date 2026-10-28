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
 * The Class CartBuilder.
 */
public class CartBuilder {

	private MailerLite apiObjectReference;

	private String shopId;

	private CartBuilderBody builderBody = new CartBuilderBody();

	public CartBuilder(MailerLite apiRef, String shopId)
	{
		apiObjectReference = apiRef;
		this.shopId = shopId;
	}

	public CartBuilderBody body()
	{
		return builderBody;
	}

	public CartBuilder checkoutUrl(String checkoutUrl)
	{
		builderBody.checkoutUrl = checkoutUrl;
		return this;
	}

	public CartBuilder cartTotal(Double cartTotal)
	{
		builderBody.cartTotal = cartTotal;
		return this;
	}

	public CartBuilder customerId(String customerId)
	{
		builderBody.customerId = customerId;
		return this;
	}

	public CartBuilder resourceId(String resourceId)
	{
		builderBody.resourceId = resourceId;
		return this;
	}

	public SingleCart update(String cartId) throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).putRequest(EcommerceApi.shopPath(shopId, "/carts/").concat(cartId), EcommerceApi.json(builderBody), SingleCart.class);
	}
}
