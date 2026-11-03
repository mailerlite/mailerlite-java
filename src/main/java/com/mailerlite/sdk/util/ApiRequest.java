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
	 *
	 * @param name the name
	 * @param value the value
	 */
	public void addQueryParameter(String name, String value)
	{
		queryParameters.put(name, value);
	}
	
	/**
	 * Removes a query parameter.
	 *
	 * @param name the name
	 */
	public void removeQueryParameter(String name)
	{
		queryParameters.remove(name);
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
