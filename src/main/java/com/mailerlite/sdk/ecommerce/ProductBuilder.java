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
 * The Class ProductBuilder.
 */
public class ProductBuilder {

	private MailerLite apiObjectReference;

	private String shopId;

	private ProductBuilderBody builderBody = new ProductBuilderBody();

	public ProductBuilder(MailerLite apiRef, String shopId)
	{
		apiObjectReference = apiRef;
		this.shopId = shopId;
	}

	public ProductBuilderBody body()
	{
		return builderBody;
	}

	public ProductBuilder name(String name)
	{
		builderBody.name = name;
		return this;
	}

	public ProductBuilder resourceId(String resourceId)
	{
		builderBody.resourceId = resourceId;
		return this;
	}

	public ProductBuilder price(Double price)
	{
		builderBody.price = price;
		return this;
	}

	public ProductBuilder url(String url)
	{
		builderBody.url = url;
		return this;
	}

	public ProductBuilder image(String image)
	{
		builderBody.image = image;
		return this;
	}

	public ProductBuilder shortDescription(String shortDescription)
	{
		builderBody.shortDescription = shortDescription;
		return this;
	}

	public ProductBuilder description(String description)
	{
		builderBody.description = description;
		return this;
	}

	public ProductBuilder excludeFromAutomations(Boolean excludeFromAutomations)
	{
		builderBody.excludeFromAutomations = excludeFromAutomations;
		return this;
	}

	public ProductBuilder enabled(Boolean enabled)
	{
		builderBody.enabled = enabled;
		return this;
	}

	public ProductBuilder type(String type)
	{
		builderBody.type = type;
		return this;
	}

	public ProductBuilder categoryId(String categoryId)
	{
		if (builderBody.categories == null) {
			builderBody.categories = new ArrayList<String>();
		}
		builderBody.categories.add(categoryId);
		return this;
	}

	public ProductBuilder variant(ProductVariantBuilderBody variant)
	{
		if (builderBody.variants == null) {
			builderBody.variants = new ArrayList<ProductVariantBuilderBody>();
		}
		builderBody.variants.add(variant);
		return this;
	}

	public SingleProduct create() throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).postRequest(EcommerceApi.shopPath(shopId, "/products"), EcommerceApi.json(builderBody), SingleProduct.class);
	}

	public SingleProduct update(String productId) throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).putRequest(EcommerceApi.shopPath(shopId, "/products").concat("/").concat(productId), EcommerceApi.json(builderBody), SingleProduct.class);
	}
}
