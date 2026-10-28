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
 * The Class ShopRetriever.
 */
public class ShopRetriever extends EcommerceRequest<ShopRetriever> {

	private MailerLite apiObjectReference;

	public ShopRetriever(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}


	public ShopsList get() throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).getRequest("/ecommerce/shops".concat(this.getQueryParameters()), ShopsList.class);
	}

	public SingleShop getSingle(String shopId) throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).getRequest("/ecommerce/shops".concat("/").concat(shopId), SingleShop.class);
	}

	@Override
	protected ShopRetriever getInstance() {
		return this;
	}
}
