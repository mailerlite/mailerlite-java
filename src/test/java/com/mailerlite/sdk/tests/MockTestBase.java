/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.mailerlite.sdk.util.MailerLiteHttpClientFactory;
import com.mailerlite.sdk.vcr.MockHttpClient;

/**
 * Base for tests that check the request the SDK builds, without calling the API.
 */
public class MockTestBase extends TestBase {

	/** The mock http client. */
	protected MockHttpClient http;
	
	/**
	 * Installs the mock http client.
	 */
	@BeforeEach
	public void installMockClient()
	{
		http = new MockHttpClient();
		MailerLiteHttpClientFactory.getInstance().setClient(http);
	}
	
	/**
	 * Removes the mock http client.
	 */
	@AfterEach
	public void removeMockClient()
	{
		MailerLiteHttpClientFactory.getInstance().setClient(null);
	}
}
