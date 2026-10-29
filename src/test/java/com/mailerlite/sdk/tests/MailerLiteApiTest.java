/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.MailerLiteResponse;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.support.RecordingHttpClient;

/**
 * Tests the HTTP layer of the SDK with the recording HTTP client.
 */
public class MailerLiteApiTest {

	/** The fake HTTP client. */
	private RecordingHttpClient client;

	/**
	 * Sets the fake HTTP client.
	 */
	@BeforeEach
	public void setupEach()
	{
		client = RecordingHttpClient.install();
	}

	/**
	 * Removes the fake HTTP client.
	 */
	@AfterEach
	public void afterEach()
	{
		RecordingHttpClient.uninstall();
		Thread.interrupted();
	}

	/**
	 * Makes an API object with a test token.
	 *
	 * @return the API object
	 */
	private MailerLiteApi api()
	{
		MailerLiteApi api = new MailerLiteApi();
		api.setToken("test-token");

		return api;
	}

	/**
	 * The old request methods, each with a name.
	 *
	 * @return the request methods
	 */
	private Map<String, Executable> oldRequestMethods()
	{
		Map<String, Executable> methods = new LinkedHashMap<String, Executable>();
		methods.put("GET", () -> api().getRequest("/groups", MailerLiteResponse.class));
		methods.put("POST", () -> api().postRequest("/groups", "{}", MailerLiteResponse.class));
		methods.put("PUT", () -> api().putRequest("/groups/1", "{}", MailerLiteResponse.class));
		methods.put("DELETE", () -> api().deleteRequest("/groups/1", MailerLiteResponse.class));

		return methods;
	}

	/**
	 * A network error becomes a MailerLiteException with the error as the cause.
	 */
	@Test
	public void networkErrorBecomesMailerLiteException()
	{
		for (Map.Entry<String, Executable> method : oldRequestMethods().entrySet()) {

			IOException error = new IOException("connection reset");
			client.fail(error);

			MailerLiteException e = assertThrows(MailerLiteException.class, method.getValue(), method.getKey());

			assertSame(error, e.getCause(), method.getKey());
			assertEquals("connection reset", e.getMessage(), method.getKey());
		}
	}

	/**
	 * An interrupt becomes a MailerLiteException, and the thread keeps its interrupt flag.
	 */
	@Test
	public void interruptBecomesMailerLiteExceptionAndKeepsTheFlag()
	{
		for (Map.Entry<String, Executable> method : oldRequestMethods().entrySet()) {

			InterruptedException error = new InterruptedException("interrupted");
			client.fail(error);

			MailerLiteException e = assertThrows(MailerLiteException.class, method.getValue(), method.getKey());

			assertSame(error, e.getCause(), method.getKey());
			assertTrue(Thread.interrupted(), method.getKey());
		}
	}

	/**
	 * An empty success body for a response class without a no-argument constructor gives a MailerLiteException.
	 */
	@Test
	public void emptyBodyForResponseWithoutDefaultConstructorBecomesMailerLiteException()
	{
		client.respond(200, "");

		MailerLiteException e = assertThrows(MailerLiteException.class,
				() -> api().getRequest("/test", NoDefaultConstructorResponse.class));

		assertTrue(e.getCause() instanceof ReflectiveOperationException);
	}

	/**
	 * A response class without a no-argument constructor.
	 */
	public static class NoDefaultConstructorResponse extends MailerLiteResponse {

		/**
		 * Instantiates a new response.
		 *
		 * @param value any value
		 */
		public NoDefaultConstructorResponse(int value)
		{
		}
	}
}
