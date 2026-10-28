/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.ecommerce;

import com.mailerlite.sdk.util.PaginatedRequest;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The Class EcommerceRequest.
 */
public abstract class EcommerceRequest<T extends EcommerceRequest<T>> extends PaginatedRequest<T> {

	private Map<String, String> queryParameters = new LinkedHashMap<String, String>();

	public T page(int page)
	{
		this.addQueryParameter("page", Integer.toString(page));
		return getInstance();
	}

	@Override
	public void addQueryParameter(String name, String value)
	{
		queryParameters.put(name, value);
	}

	@Override
	public String getQueryParameters()
	{
		String query = "";

		for (Map.Entry<String, String> parameter : queryParameters.entrySet()) {
			query = query.concat(query.isEmpty() ? "?" : "&")
					.concat(URLEncoder.encode(parameter.getKey(), StandardCharsets.UTF_8))
					.concat("=")
					.concat(URLEncoder.encode(parameter.getValue(), StandardCharsets.UTF_8));
		}

		return query;
	}
}
