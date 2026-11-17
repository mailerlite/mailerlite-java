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
import java.util.HashMap;

/**
 * The Class Products.
 */
public class Products {

	private MailerLite apiObjectReference;

	private String shopId;

	public Products(MailerLite apiRef, String shopId)
	{
		apiObjectReference = apiRef;
		this.shopId = shopId;
	}

	public ProductRetriever retriever()
	{
		return new ProductRetriever(apiObjectReference, shopId);
	}

	public ProductBuilder builder()
	{
		return new ProductBuilder(apiObjectReference, shopId);
	}

	public ProductVariants variants(String productId)
	{
		return new ProductVariants(apiObjectReference, shopId, productId);
	}

	public SingleProduct changeStatus(String productId, boolean enabled) throws MailerLiteException
	{
		HashMap<String, Object> body = new HashMap<String, Object>();
		body.put("status", enabled);

		String endpoint = EcommerceApi.shopPath(shopId, "/products/").concat(productId).concat("/change-status");
		return EcommerceApi.create(apiObjectReference).putRequest(endpoint, EcommerceApi.json(body), SingleProduct.class);
	}

	public SingleProduct assignCategories(String productId, String[] categoryIds, boolean replace) throws MailerLiteException
	{
		HashMap<String, Object> body = new HashMap<String, Object>();
		body.put("categories", categoryIds);
		body.put("replace", replace);

		String endpoint = EcommerceApi.shopPath(shopId, "/products/").concat(productId).concat("/categories/multiple");
		return EcommerceApi.create(apiObjectReference).putRequest(endpoint, EcommerceApi.json(body), SingleProduct.class);
	}

	public MailerLiteResponse delete(String productId) throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).deleteRequest(EcommerceApi.shopPath(shopId, "/products/").concat(productId), MailerLiteResponse.class);
	}
}
