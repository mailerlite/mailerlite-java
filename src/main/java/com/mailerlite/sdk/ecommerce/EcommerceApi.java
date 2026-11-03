/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.ecommerce;

import com.google.gson.JsonObject;
import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.util.JsonBody;
import com.mailerlite.sdk.util.SdkGson;

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
		return JsonBody.toJson(body);
	}

	static String jsonWithout(Object body, String... fields)
	{
		JsonObject json = SdkGson.plain().toJsonTree(body).getAsJsonObject();

		for (String field : fields) {
			json.remove(field);
		}

		return json.toString();
	}

	static String shopPath(String shopId, String path)
	{
		return "/ecommerce/shops/".concat(shopId).concat(path);
	}
}
