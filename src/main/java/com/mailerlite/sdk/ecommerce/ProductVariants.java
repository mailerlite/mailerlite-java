/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.ecommerce;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteResponse;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import java.util.List;

/**
 * The Class ProductVariants.
 */
public class ProductVariants {

	private MailerLite apiObjectReference;

	private String shopId;

	private String productId;

	public ProductVariants(MailerLite apiRef, String shopId, String productId)
	{
		apiObjectReference = apiRef;
		this.shopId = shopId;
		this.productId = productId;
	}

	public ProductVariantRetriever retriever()
	{
		return new ProductVariantRetriever(apiObjectReference, shopId, productId);
	}

	public ProductVariantBuilder builder()
	{
		return new ProductVariantBuilder(apiObjectReference, shopId, productId);
	}

	public SingleProduct sync(List<ProductVariantBuilderBody> variants) throws MailerLiteException
	{
		String endpoint = EcommerceApi.shopPath(shopId, "/products/").concat(productId).concat("/variants");
		return EcommerceApi.create(apiObjectReference).postRequest(endpoint, EcommerceApi.json(variants), SingleProduct.class);
	}

	public MailerLiteResponse delete(String variantId) throws MailerLiteException
	{
		String endpoint = EcommerceApi.shopPath(shopId, "/products/").concat(productId).concat("/variants/").concat(variantId);
		return EcommerceApi.create(apiObjectReference).deleteRequest(endpoint, MailerLiteResponse.class);
	}
}
