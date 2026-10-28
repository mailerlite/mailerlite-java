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
import java.util.ArrayList;

/**
 * The Class OrderBuilder.
 */
public class OrderBuilder {

	private MailerLite apiObjectReference;

	private String shopId;

	private OrderBuilderBody builderBody = new OrderBuilderBody();

	public OrderBuilder(MailerLite apiRef, String shopId)
	{
		apiObjectReference = apiRef;
		this.shopId = shopId;
	}

	public OrderBuilderBody body()
	{
		return builderBody;
	}

	public OrderBuilder customer(CustomerBuilderBody customer)
	{
		builderBody.customer = customer;
		return this;
	}

	public OrderBuilder status(String status)
	{
		builderBody.status = status;
		return this;
	}

	public OrderBuilder stage(String stage)
	{
		builderBody.stage = stage;
		return this;
	}

	public OrderBuilder totalPrice(Double totalPrice)
	{
		builderBody.totalPrice = totalPrice;
		return this;
	}

	public OrderBuilder resourceId(String resourceId)
	{
		builderBody.resourceId = resourceId;
		return this;
	}

	public OrderBuilder createdAt(String createdAt)
	{
		builderBody.createdAt = createdAt;
		return this;
	}

	public OrderBuilder withoutAutomations(Boolean withoutAutomations)
	{
		builderBody.withoutAutomations = withoutAutomations;
		return this;
	}

	public OrderBuilder item(CartItemBuilderBody item)
	{
		if (builderBody.cart == null) {
			builderBody.cart = new CartBuilderBody();
		}
		if (builderBody.cart.items == null) {
			builderBody.cart.items = new ArrayList<CartItemBuilderBody>();
		}
		builderBody.cart.items.add(item);
		return this;
	}

	public SingleOrder create() throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).postRequest(EcommerceApi.shopPath(shopId, "/orders"), EcommerceApi.json(builderBody), SingleOrder.class);
	}

	public SingleOrder update(String orderId) throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).putRequest(EcommerceApi.shopPath(shopId, "/orders").concat("/").concat(orderId), EcommerceApi.json(builderBody), SingleOrder.class);
	}
}
