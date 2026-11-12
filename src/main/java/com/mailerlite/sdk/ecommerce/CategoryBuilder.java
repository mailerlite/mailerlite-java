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
 * The Class CategoryBuilder.
 */
public class CategoryBuilder {

	private MailerLite apiObjectReference;

	private String shopId;

	private CategoryBuilderBody builderBody = new CategoryBuilderBody();

	public CategoryBuilder(MailerLite apiRef, String shopId)
	{
		apiObjectReference = apiRef;
		this.shopId = shopId;
	}

	public CategoryBuilderBody body()
	{
		return builderBody;
	}

	public CategoryBuilder name(String name)
	{
		builderBody.name = name;
		return this;
	}

	public CategoryBuilder resourceId(String resourceId)
	{
		builderBody.resourceId = resourceId;
		return this;
	}

	public CategoryBuilder excludeFromAutomations(Boolean excludeFromAutomations)
	{
		builderBody.excludeFromAutomations = excludeFromAutomations;
		return this;
	}

	public SingleCategory create() throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).postRequest(EcommerceApi.shopPath(shopId, "/categories"), EcommerceApi.json(builderBody), SingleCategory.class);
	}

	public SingleCategory update(String categoryId) throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).putRequest(EcommerceApi.shopPath(shopId, "/categories").concat("/").concat(categoryId), EcommerceApi.json(builderBody), SingleCategory.class);
	}
}
