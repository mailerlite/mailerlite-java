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
import java.net.ConnectException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.MailerLiteResponse;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.support.RecordingHttpClient;
import com.mailerlite.sdk.support.RecordingHttpClient.RecordedRequest;

public class MailerLiteApiTest {

	private RecordingHttpClient client;

	@BeforeEach
	public void setupEach()
	{
		client = RecordingHttpClient.install();
	}

	@AfterEach
	public void afterEach()
	{
		RecordingHttpClient.uninstall();
		Thread.interrupted();
	}

	private MailerLiteApi api()
	{
		MailerLiteApi api = new MailerLiteApi();
		api.setToken("test-token");

		return api;
	}

	private Map<String, Executable> oldRequestMethods()
	{
		Map<String, Executable> methods = new LinkedHashMap<String, Executable>();
		methods.put("GET", () -> api().getRequest("/groups", MailerLiteResponse.class));
		methods.put("POST", () -> api().postRequest("/groups", "{}", MailerLiteResponse.class));
		methods.put("PUT", () -> api().putRequest("/groups/1", "{}", MailerLiteResponse.class));
		methods.put("DELETE", () -> api().deleteRequest("/groups/1", MailerLiteResponse.class));

		return methods;
	}

	@Test
	public void networkErrorBecomesMailerLiteException()
	{
		for (Map.Entry<String, Executable> method : oldRequestMethods().entrySet()) {

			IOException error = new IOException("connection reset");
			client.fail(error);

			MailerLiteException e = assertThrows(MailerLiteException.class, method.getValue(), method.getKey());

			assertSame(error, e.getCause(), method.getKey());
			assertEquals("java.io.IOException: connection reset", e.getMessage(), method.getKey());
		}
	}

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

	@Test
	public void emptyBodyForResponseWithoutDefaultConstructorBecomesMailerLiteException()
	{
		client.respond(200, "");

		MailerLiteException e = assertThrows(MailerLiteException.class,
				() -> api().getRequest("/test", NoDefaultConstructorResponse.class));

		assertTrue(e.getCause() instanceof ReflectiveOperationException);
	}

	private void assertDefaultHeaders(RecordedRequest request)
	{
		assertEquals("application/json", request.header("Content-Type"));
		assertEquals("application/json", request.header("Accept"));
		assertEquals("Bearer test-token", request.header("Authorization"));
	}

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

	@Test
	public void sendRequestNetworkErrorBecomesMailerLiteException()
	{
		IOException error = new IOException("timeout");
		client.fail(error);

		MailerLiteException e = assertThrows(MailerLiteException.class,
				() -> api().sendRequest("POST", "/emails/123/versions", null, null, MailerLiteResponse.class));

		assertSame(error, e.getCause());
	}

	@Test
	public void versionConflictExposesCodeAndEtag()
	{
		String body = "{\"message\":\"The email changed since you read it. Fetch it again, then retry with its new etag.\","
				+ "\"code\":\"version_conflict\",\"etag\":\"43\"}";
		client.respond(409, body);

		MailerLiteException e = assertThrows(MailerLiteException.class,
				() -> api().sendRequest("PATCH", "/emails/123/blocks/57", "{}", null, MailerLiteResponse.class));

		assertEquals(409, e.code);
		assertEquals("version_conflict", e.errorCode);
		assertEquals("43", e.etag);
		assertTrue(e.isVersionConflict());
		assertEquals("version_conflict: The email changed since you read it. Fetch it again, then retry with its new etag.",
				e.getMessage());
		assertEquals(body, e.responseBody);
		assertTrue(e.errors.isEmpty());
	}

	@Test
	public void conflictWithoutCodeIsNotVersionConflict()
	{
		client.respond(409, "{\"message\":\"The campaign is not a draft.\"}");

		MailerLiteException e = assertThrows(MailerLiteException.class,
				() -> api().postRequest("/emails/123/versions", "{}", MailerLiteResponse.class));

		assertEquals(409, e.code);
		assertNull(e.errorCode);
		assertNull(e.etag);
		assertFalse(e.isVersionConflict());
		assertEquals("The campaign is not a draft.", e.getMessage());
	}

	@Test
	public void validationErrorExposesErrors()
	{
		client.respond(422, "{\"message\":\"The given data was invalid.\","
				+ "\"errors\":{\"editor_content\":[\"The editor content field must be present.\"]}}");

		MailerLiteException e = assertThrows(MailerLiteException.class,
				() -> api().sendRequest("POST", "/emails/123/blocks", "{}", null, MailerLiteResponse.class));

		assertEquals(422, e.code);
		assertNull(e.errorCode);
		assertFalse(e.isVersionConflict());
		assertEquals("The given data was invalid.", e.getMessage());
		assertArrayEquals(new String[] {"The editor content field must be present."}, e.errors.get("editor_content"));
	}

	@Test
	public void validationErrorWithOnlyMessage()
	{
		client.respond(422, "{\"message\":\"This email was not built with the simple editor.\"}");

		MailerLiteException e = assertThrows(MailerLiteException.class,
				() -> api().sendRequest("GET", "/emails/123", null, null, MailerLiteResponse.class));

		assertEquals(422, e.code);
		assertEquals("This email was not built with the simple editor.", e.getMessage());
		assertTrue(e.errors.isEmpty());
		assertNull(e.errorCode);
	}

	@Test
	public void serviceUnavailableWithHtmlBody()
	{
		String body = "<html><body><h1>503 Service Unavailable</h1></body></html>";
		client.respond(503, body);

		MailerLiteException e = assertThrows(MailerLiteException.class,
				() -> api().sendRequest("GET", "/emails/123/html", null, null, MailerLiteResponse.class));

		assertEquals(503, e.code);
		assertEquals("Error parsing API response", e.getMessage());
		assertEquals(body, e.responseBody);
		assertNull(e.errorCode);
		assertNull(e.etag);
		assertFalse(e.isVersionConflict());
	}

	public static class NoDefaultConstructorResponse extends MailerLiteResponse {

		public NoDefaultConstructorResponse(int value)
		{
		}
	}

	@Test
	public void networkErrorWithoutMessageKeepsTheErrorName()
	{
		for (Map.Entry<String, Executable> method : oldRequestMethods().entrySet()) {

			ConnectException error = new ConnectException();
			client.fail(error);

			MailerLiteException e = assertThrows(MailerLiteException.class, method.getValue(), method.getKey());

			assertSame(error, e.getCause(), method.getKey());
			assertTrue(e.getMessage() != null && e.getMessage().contains("ConnectException"), method.getKey() + ": " + e.getMessage());
		}

		ConnectException error = new ConnectException();
		client.fail(error);

		MailerLiteException e = assertThrows(MailerLiteException.class,
				() -> api().sendRequest("POST", "/emails/123/versions", null, null, MailerLiteResponse.class));

		assertSame(error, e.getCause());
		assertTrue(e.getMessage() != null && e.getMessage().contains("ConnectException"), e.getMessage());
	}

	@Test
	public void interruptWithoutMessageKeepsTheErrorNameAndTheFlag()
	{
		InterruptedException error = new InterruptedException();
		client.fail(error);

		MailerLiteException e = assertThrows(MailerLiteException.class,
				() -> api().sendRequest("GET", "/emails/123", null, null, MailerLiteResponse.class));

		assertSame(error, e.getCause());
		assertTrue(e.getMessage() != null && e.getMessage().contains("InterruptedException"), e.getMessage());
		assertTrue(Thread.interrupted());
	}

	@Test
	public void apiErrorSetsTheMessageField()
	{
		Map<String, Executable> methods = oldRequestMethods();
		methods.put("sendRequest", () -> api().sendRequest("POST", "/emails/123/versions", null, null, MailerLiteResponse.class));
		methods.put("automation activity", () -> {
			MailerLite ml = new MailerLite();
			ml.setToken("test-token");
			ml.automations().subscriberActivityRetriever().get("1");
		});

		for (Map.Entry<String, Executable> method : methods.entrySet()) {

			client.respond(404, "{\"message\":\"Resource not found.\"}");

			MailerLiteException e = assertThrows(MailerLiteException.class, method.getValue(), method.getKey());

			assertEquals("Resource not found.", e.message, method.getKey());
			assertEquals("Resource not found.", e.getMessage(), method.getKey());
		}
	}

	@Test
	public void apiErrorWithCodeSetsTheMessageFieldWithoutTheCode()
	{
		client.respond(409, "{\"message\":\"The email changed.\",\"code\":\"version_conflict\",\"etag\":\"43\"}");

		MailerLiteException e = assertThrows(MailerLiteException.class,
				() -> api().sendRequest("PATCH", "/emails/123/blocks/57", "{}", null, MailerLiteResponse.class));

		assertEquals("The email changed.", e.message);
		assertEquals("version_conflict: The email changed.", e.getMessage());
	}

	@Test
	public void apiErrorWithoutMessageKeepsAnEmptyMessageField()
	{
		client.respond(409, "{\"code\":\"version_conflict\"}").respond(500, "{\"message\":null}");

		MailerLiteException withCode = assertThrows(MailerLiteException.class,
				() -> api().sendRequest("PATCH", "/emails/123/blocks/57", "{}", null, MailerLiteResponse.class));
		MailerLiteException withNull = assertThrows(MailerLiteException.class,
				() -> api().getRequest("/groups", MailerLiteResponse.class));

		assertEquals("", withCode.message);
		assertEquals("", withNull.message);
	}

	@Test
	public void errorWithoutApiMessageSetsTheMessageFieldToGetMessage()
	{
		client.respond(503, "<html><body>503</body></html>");

		MailerLiteException unreadable = assertThrows(MailerLiteException.class,
				() -> api().getRequest("/groups", MailerLiteResponse.class));

		assertEquals("Error parsing API response", unreadable.message);

		client.fail(new IOException("connection reset"));

		MailerLiteException network = assertThrows(MailerLiteException.class,
				() -> api().sendRequest("GET", "/emails/123", null, null, MailerLiteResponse.class));

		assertEquals("java.io.IOException: connection reset", network.message);
		assertEquals(network.getMessage(), network.message);
	}
}
