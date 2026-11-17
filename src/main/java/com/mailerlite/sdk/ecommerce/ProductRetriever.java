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
 * The Class ProductRetriever.
 */
public class ProductRetriever extends EcommerceRequest<ProductRetriever> {

	private MailerLite apiObjectReference;

	private String shopId;

	public ProductRetriever(MailerLite apiRef, String shopId)
	{
		apiObjectReference = apiRef;
		this.shopId = shopId;
	}

	public ProductRetriever nameFilter(String value)
	{
		this.addQueryParameter("filter[name]", String.valueOf(value));
		return this;
	}

	public ProductRetriever typeFilter(String value)
	{
		this.addQueryParameter("filter[type]", String.valueOf(value));
		return this;
	}

	public ProductRetriever enabledFilter(boolean value)
	{
		this.addQueryParameter("filter[enabled]", String.valueOf(value));
		return this;
	}

	public ProductRetriever categoryFilter(String value)
	{
		this.addQueryParameter("filter[category]", String.valueOf(value));
		return this;
	}

	public ProductRetriever excludeFromAutomationsFilter(boolean value)
	{
		this.addQueryParameter("filter[exclude_from_automations]", String.valueOf(value));
		return this;
	}

	public ProductRetriever sort(String value)
	{
		this.addQueryParameter("sort", String.valueOf(value));
		return this;
	}

	public ProductsList get() throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).getRequest(EcommerceApi.shopPath(shopId, "/products").concat(this.getQueryParameters()), ProductsList.class);
	}

	public SingleProduct getSingle(String productId) throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).getRequest(EcommerceApi.shopPath(shopId, "/products").concat("/").concat(productId), SingleProduct.class);
	}

	@Override
	protected ProductRetriever getInstance() {
		return this;
	}
}
