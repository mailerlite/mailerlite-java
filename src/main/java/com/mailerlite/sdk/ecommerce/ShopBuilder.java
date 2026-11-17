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
 * The Class ShopBuilder.
 */
public class ShopBuilder {

	private MailerLite apiObjectReference;

	private ShopBuilderBody builderBody = new ShopBuilderBody();

	public ShopBuilder(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}

	public ShopBuilderBody body()
	{
		return builderBody;
	}

	public ShopBuilder name(String name)
	{
		builderBody.name = name;
		return this;
	}

	public ShopBuilder url(String url)
	{
		builderBody.url = url;
		return this;
	}

	public ShopBuilder currency(String currency)
	{
		builderBody.currency = currency;
		return this;
	}

	public ShopBuilder platform(String platform)
	{
		builderBody.platform = platform;
		return this;
	}

	public ShopBuilder groupId(String groupId)
	{
		builderBody.groupId = groupId;
		return this;
	}

	public ShopBuilder enablePopups(Boolean enablePopups)
	{
		builderBody.enablePopups = enablePopups;
		return this;
	}

	public ShopBuilder enabled(Boolean enabled)
	{
		builderBody.enabled = enabled;
		return this;
	}

	public ShopBuilder accessData(String accessData)
	{
		builderBody.accessData = accessData;
		return this;
	}

	public SingleShop create() throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).postRequest("/ecommerce/shops", EcommerceApi.json(builderBody), SingleShop.class);
	}

	public SingleShop update(String shopId) throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).putRequest("/ecommerce/shops".concat("/").concat(shopId), EcommerceApi.json(builderBody), SingleShop.class);
	}
}
