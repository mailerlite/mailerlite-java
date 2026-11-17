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
 * The Class CategoryRetriever.
 */
public class CategoryRetriever extends EcommerceRequest<CategoryRetriever> {

	private MailerLite apiObjectReference;

	private String shopId;

	public CategoryRetriever(MailerLite apiRef, String shopId)
	{
		apiObjectReference = apiRef;
		this.shopId = shopId;
	}

	public CategoryRetriever excludeFromAutomationsFilter(boolean value)
	{
		this.addQueryParameter("filter[exclude_from_automations]", String.valueOf(value));
		return this;
	}

	public CategoriesList get() throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).getRequest(EcommerceApi.shopPath(shopId, "/categories").concat(this.getQueryParameters()), CategoriesList.class);
	}

	public SingleCategory getSingle(String categoryId) throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).getRequest(EcommerceApi.shopPath(shopId, "/categories").concat("/").concat(categoryId), SingleCategory.class);
	}

	public ProductsList getProducts(String categoryId) throws MailerLiteException
	{
		String endpoint = EcommerceApi.shopPath(shopId, "/categories/").concat(categoryId).concat("/products").concat(this.getQueryParameters());
		return EcommerceApi.create(apiObjectReference).getRequest(endpoint, ProductsList.class);
	}

	@Override
	protected CategoryRetriever getInstance() {
		return this;
	}
}
