/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.vcr;

import java.net.URI;
import java.net.http.HttpClient.Version;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The Class HttpClientVcrResponse.
 */
public class HttpClientVcrResponse implements HttpResponse<Object> {
	
	/** The request hash. */
	public String requestHash;
	
	/** The body. */
	public String body;
	
	/** The headers. */
	public Map<String, List<String>> headers;
	
	/** The status code. */
	public int statusCode;

	/**
	 * Status code.
	 *
	 * @return the int
	 */
	@Override
	public int statusCode() {
		return this.statusCode;
	}

	/**
	 * Request.
	 *
	 * @return the http request
	 */
	@Override
	public HttpRequest request() {
		return null;
	}

	/**
	 * Previous response.
	 *
	 * @return the optional
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Optional previousResponse() {
		return null;
	}

	/**
	 * Headers.
	 *
	 * @return the http headers
	 */
	@Override
	public HttpHeaders headers() {
		return HttpHeaders.of(headers, (s1,s2) -> true);
	}

	/**
	 * Body.
	 *
	 * @return the object
	 */
	@Override
	public Object body() {
		return this.body;
	}

	/**
	 * Ssl session.
	 *
	 * @return the optional
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Optional sslSession() {
		return null;
	}

	/**
	 * Uri.
	 *
	 * @return the uri
	 */
	@Override
	public URI uri() {
		return null;
	}

	/**
	 * Version.
	 *
	 * @return the version
	 */
	@Override
	public Version version() {
		return null;
	}

}
