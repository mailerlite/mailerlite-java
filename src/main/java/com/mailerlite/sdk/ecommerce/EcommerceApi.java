/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.ecommerce;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.util.JsonSerializationDeserializationStrategy;

/**
 * The Class EcommerceApi.
 */
final class EcommerceApi {

	private EcommerceApi()
	{
	}

	static MailerLiteApi create(MailerLite apiRef)
	{
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiRef.getToken());
		return api;
	}

	static String json(Object body)
	{
		Gson gson = new GsonBuilder()
				.addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
				.addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
				.create();

		return gson.toJson(body);
	}

	static String shopPath(String shopId, String path)
	{
		return "/ecommerce/shops/".concat(shopId).concat(path);
	}
}
