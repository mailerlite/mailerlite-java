/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.vcr;

import java.net.URLDecoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodySubscriber;
import java.net.http.HttpResponse.BodySubscribers;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Records the last request instead of sending it, and answers with a canned response.
 */
public class MockHttpClient extends HttpClientVcr {

	/** The last request. */
	private HttpRequest lastRequest;
	
	/** The last request body. */
	private String lastBody;
	
	/** The canned response status code. */
	private int statusCode = 200;
	
	/** The canned response body. */
	private String responseBody = "{}";
	
	/**
	 * Sets the canned response.
	 *
	 * @param statusCode the status code
	 * @param responseBody the response body
	 */
	public void respondWith(int statusCode, String responseBody)
	{
		this.statusCode = statusCode;
		this.responseBody = responseBody;
	}
	
	/**
	 * Records the request and returns the canned response.
	 *
	 * @param <T> the generic type
	 * @param request the request
	 * @param responseBodyHandler the response body handler
	 * @return the http response
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> HttpResponse<T> send(HttpRequest request, BodyHandler<T> responseBodyHandler)
	{
		lastRequest = request;
		lastBody = request.bodyPublisher().map(p -> {
			BodySubscriber<String> bodySubscriber = BodySubscribers.ofString(StandardCharsets.UTF_8);
			p.subscribe(new StringSubscriber(bodySubscriber));
			return bodySubscriber.getBody().toCompletableFuture().join();
		}).orElse("");
		
		HttpClientVcrResponse response = new HttpClientVcrResponse();
		response.statusCode = statusCode;
		response.body = responseBody;
		response.headers = new HashMap<String, List<String>>();
		
		return (HttpResponse<T>) response;
	}
	
	/**
	 * Whether any request was sent.
	 *
	 * @return true if a request was sent
	 */
	public boolean hasRequest()
	{
		return lastRequest != null;
	}
	
	/**
	 * The HTTP method of the last request.
	 *
	 * @return the method
	 */
	public String method()
	{
		return lastRequest.method();
	}
	
	/**
	 * The path of the last request, without the /api prefix.
	 *
	 * @return the path
	 */
	public String path()
	{
		return lastRequest.uri().getPath().replaceFirst("^/api", "");
	}
	
	/**
	 * The decoded query parameters of the last request.
	 *
	 * @return the query parameters
	 */
	public Map<String, String> query()
	{
		Map<String, String> params = new HashMap<String, String>();
		String rawQuery = lastRequest.uri().getRawQuery();
		
		if (rawQuery == null || rawQuery.isEmpty()) {
			return params;
		}
		
		for (String pair : rawQuery.split("&")) {
			String[] parts = pair.split("=", 2);
			params.put(URLDecoder.decode(parts[0], StandardCharsets.UTF_8), parts.length > 1 ? URLDecoder.decode(parts[1], StandardCharsets.UTF_8) : "");
		}
		
		return params;
	}
	
	/**
	 * The JSON body of the last request.
	 *
	 * @return the body
	 */
	public JsonObject body()
	{
		return JsonParser.parseString(lastBody).getAsJsonObject();
	}
	
	/**
	 * The raw body of the last request.
	 *
	 * @return the raw body
	 */
	public String rawBody()
	{
		return lastBody;
	}
}
