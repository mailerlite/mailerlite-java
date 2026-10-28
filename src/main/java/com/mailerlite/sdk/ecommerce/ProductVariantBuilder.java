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
 * The Class ProductVariantBuilder.
 */
public class ProductVariantBuilder {

	private MailerLite apiObjectReference;

	private String shopId;

	private String productId;

	private ProductVariantBuilderBody builderBody = new ProductVariantBuilderBody();

	public ProductVariantBuilder(MailerLite apiRef, String shopId, String productId)
	{
		apiObjectReference = apiRef;
		this.shopId = shopId;
		this.productId = productId;
	}

	public ProductVariantBuilderBody body()
	{
		return builderBody;
	}

	public ProductVariantBuilder name(String name)
	{
		builderBody.name = name;
		return this;
	}

	public ProductVariantBuilder priceType(String priceType)
	{
		builderBody.priceType = priceType;
		return this;
	}

	public ProductVariantBuilder price(Double price)
	{
		builderBody.price = price;
		return this;
	}

	public ProductVariantBuilder resourceId(String resourceId)
	{
		builderBody.resourceId = resourceId;
		return this;
	}

	public ProductVariantBuilder imageUrl(String imageUrl)
	{
		builderBody.imageUrl = imageUrl;
		return this;
	}

	public ProductVariantBuilder attribute(String name, String value)
	{
		builderBody.attributes.put(name, value);
		return this;
	}

	public SingleProduct update(String variantId) throws MailerLiteException
	{
		String endpoint = EcommerceApi.shopPath(shopId, "/products/").concat(productId).concat("/variants/").concat(variantId);
		return EcommerceApi.create(apiObjectReference).putRequest(endpoint, EcommerceApi.json(builderBody), SingleProduct.class);
	}
}
