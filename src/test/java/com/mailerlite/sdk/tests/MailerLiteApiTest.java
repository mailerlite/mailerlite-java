/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Collections;
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
import com.mailerlite.sdk.support.RecordingHttpClient.RecordedRequest;

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
	 * Asserts the headers that sendRequest sends on every request.
	 *
	 * @param request the request
	 */
	private void assertDefaultHeaders(RecordedRequest request)
	{
		assertEquals("application/json", request.header("Content-Type"));
		assertEquals("application/json", request.header("Accept"));
		assertEquals("Bearer test-token", request.header("Authorization"));
	}

	/**
	 * sendRequest sends a PATCH request with the body and the extra headers.
	 *
	 * @throws MailerLiteException if the request fails
	 */
	@Test
	public void sendRequestSendsPatchWithBody() throws MailerLiteException
	{
		client.respond(200, "{}");

		MailerLiteResponse response = api().sendRequest("PATCH", "/emails/123/blocks/57", "{\"etag\":\"42\"}",
				Collections.singletonMap("Idempotency-Key", "key-1"), MailerLiteResponse.class);

		RecordedRequest request = client.lastRequest();
		assertEquals("PATCH", request.method);
		assertEquals("https://connect.mailerlite.com/api/emails/123/blocks/57", request.uri.toString());
		assertEquals("{\"etag\":\"42\"}", request.bodyString());
		assertDefaultHeaders(request);
		assertEquals("key-1", request.header("Idempotency-Key"));
		assertEquals(200, response.responseStatusCode);
	}

	/**
	 * sendRequest sends a DELETE request with a body when the caller gives one.
	 *
	 * @throws MailerLiteException if the request fails
	 */
	@Test
	public void sendRequestSendsDeleteWithBody() throws MailerLiteException
	{
		client.respond(200, "{\"etag\":\"45\"}");

		api().sendRequest("DELETE", "/emails/123/blocks/57", "{\"etag\":\"44\"}", null, MailerLiteResponse.class);

		RecordedRequest request = client.lastRequest();
		assertEquals("DELETE", request.method);
		assertEquals("{\"etag\":\"44\"}", request.bodyString());
		assertDefaultHeaders(request);
		assertNull(request.header("Idempotency-Key"));
	}

	/**
	 * sendRequest sends GET and DELETE without a body publisher when the body is null.
	 *
	 * @throws MailerLiteException if the request fails
	 */
	@Test
	public void sendRequestSendsGetAndDeleteWithoutBody() throws MailerLiteException
	{
		client.respond(200, "{}").respond(204, "");

		api().sendRequest("GET", "/emails/123", null, null, MailerLiteResponse.class);
		MailerLiteResponse deleted = api().sendRequest("DELETE", "/campaigns/1", null, null, MailerLiteResponse.class);

		for (RecordedRequest request : client.requests()) {
			assertFalse(request.hasBodyPublisher, request.method);
			assertDefaultHeaders(request);
		}

		assertEquals("GET", client.requests().get(0).method);
		assertEquals("DELETE", client.requests().get(1).method);
		assertEquals(204, deleted.responseStatusCode);
	}

	/**
	 * sendRequest sends a POST with an empty body, not the JSON null, when the body is null.
	 *
	 * @throws MailerLiteException if the request fails
	 */
	@Test
	public void sendRequestSendsPostWithEmptyBody() throws MailerLiteException
	{
		client.respond(201, "{}");

		MailerLiteResponse response = api().sendRequest("POST", "/emails/123/versions", null, null, MailerLiteResponse.class);

		RecordedRequest request = client.lastRequest();
		assertEquals("POST", request.method);
		assertTrue(request.hasBodyPublisher);
		assertEquals(0, request.contentLength);
		assertArrayEquals(new byte[0], request.body);
		assertDefaultHeaders(request);
		assertEquals(201, response.responseStatusCode);
	}

	/**
	 * sendRequest converts a network error to a MailerLiteException.
	 */
	@Test
	public void sendRequestNetworkErrorBecomesMailerLiteException()
	{
		IOException error = new IOException("timeout");
		client.fail(error);

		MailerLiteException e = assertThrows(MailerLiteException.class,
				() -> api().sendRequest("POST", "/emails/123/versions", null, null, MailerLiteResponse.class));

		assertSame(error, e.getCause());
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
