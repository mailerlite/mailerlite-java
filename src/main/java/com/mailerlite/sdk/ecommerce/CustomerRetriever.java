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
 * The Class CustomerRetriever.
 */
public class CustomerRetriever extends EcommerceRequest<CustomerRetriever> {

	private MailerLite apiObjectReference;

	private String shopId;

	public CustomerRetriever(MailerLite apiRef, String shopId)
	{
		apiObjectReference = apiRef;
		this.shopId = shopId;
	}

	public CustomerRetriever emailFilter(String value)
	{
		this.addQueryParameter("filter[email]", String.valueOf(value));
		return this;
	}

	public CustomerRetriever sort(String value)
	{
		this.addQueryParameter("sort", String.valueOf(value));
		return this;
	}

	public CustomersList get() throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).getRequest(EcommerceApi.shopPath(shopId, "/customers").concat(this.getQueryParameters()), CustomersList.class);
	}

	public SingleCustomer getSingle(String customerId) throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).getRequest(EcommerceApi.shopPath(shopId, "/customers").concat("/").concat(customerId), SingleCustomer.class);
	}

	@Override
	protected CustomerRetriever getInstance() {
		return this;
	}
}
