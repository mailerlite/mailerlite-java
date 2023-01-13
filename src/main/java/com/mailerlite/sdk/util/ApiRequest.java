package com.mailerlite.sdk.util;

import java.util.HashMap;

public class ApiRequest {


	private HashMap<String, String> queryParameters = new HashMap<String, String>();
	
	public void AddQueryParameter(String name, String value)
	{
		queryParameters.put(name, value);
	}
	
	public String getQueryParameters()
	{
		String query = "";
		
		for (String name : queryParameters.keySet()) {
			
			if (!query.isEmpty()) {
				query = query.concat("&");
			}
			
			query = query.concat(name).concat("=").concat(queryParameters.get(name));
		}
		
		return "?".concat(query);
	}
}
