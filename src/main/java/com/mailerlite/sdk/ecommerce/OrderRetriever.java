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
 * The Class OrderRetriever.
 */
public class OrderRetriever extends EcommerceRequest<OrderRetriever> {

	private MailerLite apiObjectReference;

	private String shopId;

	public OrderRetriever(MailerLite apiRef, String shopId)
	{
		apiObjectReference = apiRef;
		this.shopId = shopId;
	}

	public OrderRetriever statusFilter(String value)
	{
		this.addQueryParameter("filter[status]", String.valueOf(value));
		return this;
	}

	public OrderRetriever customerFilter(String value)
	{
		this.addQueryParameter("filter[customer]", String.valueOf(value));
		return this;
	}

	public OrderRetriever attributedFilter(boolean value)
	{
		this.addQueryParameter("filter[attributed]", String.valueOf(value));
		return this;
	}

	public OrderRetriever emailIdFilter(String value)
	{
		this.addQueryParameter("filter[email_id]", String.valueOf(value));
		return this;
	}

	public OrderRetriever sort(String value)
	{
		this.addQueryParameter("sort", String.valueOf(value));
		return this;
	}

	public OrdersList get() throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).getRequest(EcommerceApi.shopPath(shopId, "/orders").concat(this.getQueryParameters()), OrdersList.class);
	}

	public SingleOrder getSingle(String orderId) throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).getRequest(EcommerceApi.shopPath(shopId, "/orders").concat("/").concat(orderId), SingleOrder.class);
	}

	@Override
	protected OrderRetriever getInstance() {
		return this;
	}
}
