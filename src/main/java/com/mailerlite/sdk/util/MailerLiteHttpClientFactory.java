/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.util;

import java.net.http.HttpClient;

/**
 * We use a factory for the HttpClient that the SDk uses so that we can switch clients when needed
 * e.g. when running tests we use the HttpClientVcr to record and replay the API responses
 *
 * @version $Id: $Id
 */
public class MailerLiteHttpClientFactory {
	
	/** The instance. */
	private static MailerLiteHttpClientFactory instance = null;
	
	/** The client. */
	private HttpClient client;
	
	/**
	 * <p>Getter for the field <code>instance</code>.</p>
	 *
	 * @return MailerLiteHttpClientFactory object.
	 */
	public static MailerLiteHttpClientFactory getInstance()
	{
		if (instance == null) {
			instance = new MailerLiteHttpClientFactory();
		}
		
		return instance;
	}
	
	
	/**
	 * <p>createClient.</p>
	 *
	 * @return a HttpClient object.
	 */
	public HttpClient createClient()
	{
		if (client != null) {
			
			return client;
		}
		
		return HttpClient.newHttpClient();
	}
	
	/**
	 * <p>Setter for the field <code>client</code>.</p>
	 *
	 * @param client a HttpClient object.
	 */
	public void setClient(HttpClient client)
	{
		this.client = client;
	}

}
