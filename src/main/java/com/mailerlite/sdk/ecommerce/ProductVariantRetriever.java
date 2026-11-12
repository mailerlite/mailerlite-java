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
 * The Class ProductVariantRetriever.
 */
public class ProductVariantRetriever extends EcommerceRequest<ProductVariantRetriever> {

	private MailerLite apiObjectReference;

	private String shopId;
	private String productId;

	public ProductVariantRetriever(MailerLite apiRef, String shopId, String productId)
	{
		apiObjectReference = apiRef;
		this.shopId = shopId;
		this.productId = productId;
	}


	public ProductVariantsList get() throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).getRequest(EcommerceApi.shopPath(shopId, "/products/").concat(productId).concat("/variants").concat(this.getQueryParameters()), ProductVariantsList.class);
	}

	@Override
	protected ProductVariantRetriever getInstance() {
		return this;
	}
}
