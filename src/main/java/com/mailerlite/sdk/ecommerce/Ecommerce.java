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
 * The Class Ecommerce.
 */
public class Ecommerce {

	private MailerLite apiObjectReference;

	public Ecommerce(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}

	public Shops shops()
	{
		return new Shops(apiObjectReference);
	}

	public Products products(String shopId)
	{
		return new Products(apiObjectReference, shopId);
	}

	public Categories categories(String shopId)
	{
		return new Categories(apiObjectReference, shopId);
	}

	public Customers customers(String shopId)
	{
		return new Customers(apiObjectReference, shopId);
	}

	public Orders orders(String shopId)
	{
		return new Orders(apiObjectReference, shopId);
	}

	public Carts carts(String shopId)
	{
		return new Carts(apiObjectReference, shopId);
	}

	public Imports imports(String shopId)
	{
		return new Imports(apiObjectReference, shopId);
	}

	public SingleEcommerceSettings getSettings() throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).getRequest("/ecommerce/settings", SingleEcommerceSettings.class);
	}
}
