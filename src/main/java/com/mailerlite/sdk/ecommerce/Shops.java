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
 * The Class Shops.
 */
public class Shops {

	private MailerLite apiObjectReference;

	public Shops(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}

	public ShopRetriever retriever()
	{
		return new ShopRetriever(apiObjectReference);
	}

	public ShopBuilder builder()
	{
		return new ShopBuilder(apiObjectReference);
	}

	public EcommerceReportRetriever ecommerceReport(String shopId)
	{
		return new EcommerceReportRetriever(apiObjectReference, EcommerceApi.shopPath(shopId, "/reports/stats"), null, null);
	}

	public MailerLiteResponse delete(String shopId) throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).deleteRequest(EcommerceApi.shopPath(shopId, ""), MailerLiteResponse.class);
	}
}
