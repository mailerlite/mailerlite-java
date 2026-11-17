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
 * The Class CustomerBuilder.
 */
public class CustomerBuilder {

	private MailerLite apiObjectReference;

	private String shopId;

	private CustomerBuilderBody builderBody = new CustomerBuilderBody();

	public CustomerBuilder(MailerLite apiRef, String shopId)
	{
		apiObjectReference = apiRef;
		this.shopId = shopId;
	}

	public CustomerBuilderBody body()
	{
		return builderBody;
	}

	public CustomerBuilder email(String email)
	{
		builderBody.email = email;
		return this;
	}

	public CustomerBuilder name(String name)
	{
		builderBody.name = name;
		return this;
	}

	public CustomerBuilder lastName(String lastName)
	{
		builderBody.lastName = lastName;
		return this;
	}

	public CustomerBuilder acceptsMarketing(Boolean acceptsMarketing)
	{
		builderBody.acceptsMarketing = acceptsMarketing;
		return this;
	}

	public CustomerBuilder totalSpent(Double totalSpent)
	{
		builderBody.totalSpent = totalSpent;
		return this;
	}

	public CustomerBuilder ordersCount(Integer ordersCount)
	{
		builderBody.ordersCount = ordersCount;
		return this;
	}

	public CustomerBuilder createSubscriber(Boolean createSubscriber)
	{
		builderBody.createSubscriber = createSubscriber;
		return this;
	}

	public CustomerBuilder resourceId(String resourceId)
	{
		builderBody.resourceId = resourceId;
		return this;
	}

	public SingleCustomer create() throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).postRequest(EcommerceApi.shopPath(shopId, "/customers"), EcommerceApi.json(builderBody), SingleCustomer.class);
	}

	public SingleCustomer update(String customerId) throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).putRequest(EcommerceApi.shopPath(shopId, "/customers").concat("/").concat(customerId), EcommerceApi.json(builderBody), SingleCustomer.class);
	}
}
