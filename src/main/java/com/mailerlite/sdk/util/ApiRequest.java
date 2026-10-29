/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * The Class ApiRequest.
 */
public class ApiRequest {


	/** The query parameters. */
	private HashMap<String, String> queryParameters = new HashMap<String, String>();
	
	/**
	 * Adds the query parameter.
	 * <p>
	 * A null value is not sent, and it removes a value that you set before. An empty value is sent as "name=".
	 *
	 * @param name the name, for example "filter[name]". The SDK sends it as it is.
	 * @param value the value. The SDK encodes it, so a space is sent as "+".
	 */
	public void addQueryParameter(String name, String value)
	{
		if (value == null) {
			
			queryParameters.remove(name);
			return;
		}
		
		queryParameters.put(name, value);
	}
	
	/**
	 * Gets the query parameters.
	 *
	 * @return the query parameters
	 */
	public String getQueryParameters()
	{
		String query = "";
		
		for (String name : queryParameters.keySet()) {
			
			if (!query.isEmpty()) {
				query = query.concat("&");
			}
			
			query = query.concat(name).concat("=").concat(URLEncoder.encode(queryParameters.get(name), StandardCharsets.UTF_8));
		}
		
		return "?".concat(query);
	}
}
