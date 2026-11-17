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
 * The Class CartItemBuilder.
 */
public class CartItemBuilder {

	private MailerLite apiObjectReference;

	private String shopId;

	private String cartId;

	private CartItemBuilderBody builderBody = new CartItemBuilderBody();

	public CartItemBuilder(MailerLite apiRef, String shopId, String cartId)
	{
		apiObjectReference = apiRef;
		this.shopId = shopId;
		this.cartId = cartId;
	}

	public CartItemBuilderBody body()
	{
		return builderBody;
	}

	public CartItemBuilder productId(String productId)
	{
		builderBody.productId = productId;
		return this;
	}

	public CartItemBuilder productResourceId(String productResourceId)
	{
		builderBody.productResourceId = productResourceId;
		return this;
	}

	public CartItemBuilder productVariantId(String productVariantId)
	{
		builderBody.productVariantId = productVariantId;
		return this;
	}

	public CartItemBuilder variant(String variant)
	{
		builderBody.variant = variant;
		return this;
	}

	public CartItemBuilder quantity(Integer quantity)
	{
		builderBody.quantity = quantity;
		return this;
	}

	public CartItemBuilder price(Double price)
	{
		builderBody.price = price;
		return this;
	}

	public CartItemBuilder resourceId(String resourceId)
	{
		builderBody.resourceId = resourceId;
		return this;
	}

	public SingleCartItem create() throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).postRequest(EcommerceApi.shopPath(shopId, "/carts/").concat(cartId).concat("/items"), EcommerceApi.json(builderBody), SingleCartItem.class);
	}

	public SingleCartItem update(String itemId) throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).putRequest(EcommerceApi.shopPath(shopId, "/carts/").concat(cartId).concat("/items").concat("/").concat(itemId), EcommerceApi.json(builderBody), SingleCartItem.class);
	}
}
