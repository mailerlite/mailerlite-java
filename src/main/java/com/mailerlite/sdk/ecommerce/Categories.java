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

/**
 * The Class Categories.
 */
public class Categories {

	private MailerLite apiObjectReference;

	private String shopId;

	public Categories(MailerLite apiRef, String shopId)
	{
		apiObjectReference = apiRef;
		this.shopId = shopId;
	}

	public CategoryRetriever retriever()
	{
		return new CategoryRetriever(apiObjectReference, shopId);
	}

	public CategoryBuilder builder()
	{
		return new CategoryBuilder(apiObjectReference, shopId);
	}

	public MailerLiteResponse assignProduct(String categoryId, String productId) throws MailerLiteException
	{
		String endpoint = EcommerceApi.shopPath(shopId, "/categories/").concat(categoryId).concat("/products/").concat(productId);
		return EcommerceApi.create(apiObjectReference).putRequest(endpoint, "{}", MailerLiteResponse.class);
	}

	public MailerLiteResponse unassignProduct(String categoryId, String productId) throws MailerLiteException
	{
		String endpoint = EcommerceApi.shopPath(shopId, "/categories/").concat(categoryId).concat("/products/").concat(productId);
		return EcommerceApi.create(apiObjectReference).deleteRequest(endpoint, MailerLiteResponse.class);
	}

	public MailerLiteResponse delete(String categoryId) throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).deleteRequest(EcommerceApi.shopPath(shopId, "/categories/").concat(categoryId), MailerLiteResponse.class);
	}
}
