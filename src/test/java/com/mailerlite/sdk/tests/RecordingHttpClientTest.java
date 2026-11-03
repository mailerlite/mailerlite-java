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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Collections;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.mailerlite.sdk.support.RecordingHttpClient;
import com.mailerlite.sdk.support.RecordingHttpClient.RecordedRequest;

public class RecordingHttpClientTest {

	private static final URI TEST_URI = URI.create("https://connect.mailerlite.com/api/test");

	@AfterEach
	public void afterEach()
	{
		RecordingHttpClient.uninstall();
	}

	@Test
	public void keepsTheRawBodyAndReturnsTheQueuedResponse() throws Exception
	{
		RecordingHttpClient client = new RecordingHttpClient()
				.respond(201, "{\"ok\":true}", Collections.singletonMap("Idempotent-Replayed", "true"));

		HttpRequest request = HttpRequest.newBuilder(TEST_URI)
				.method("PATCH", BodyPublishers.ofString("{\"a\":1}"))
				.build();

		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

		assertEquals(201, response.statusCode());
		assertEquals("{\"ok\":true}", response.body());
		assertEquals("true", response.headers().firstValue("idempotent-replayed").get());

		RecordedRequest recorded = client.lastRequest();
		assertEquals("PATCH", recorded.method);
		assertEquals(TEST_URI, recorded.uri);
		assertEquals("{\"a\":1}", recorded.bodyString());
		assertEquals(0, client.pendingResponses());
	}

	@Test
	public void tellsNoPublisherFromAnEmptyBody() throws Exception
	{
		RecordingHttpClient client = new RecordingHttpClient().respond(200, "").respond(200, "");

		client.send(HttpRequest.newBuilder(TEST_URI).GET().build(), BodyHandlers.ofString());
		client.send(HttpRequest.newBuilder(TEST_URI).method("POST", BodyPublishers.noBody()).build(), BodyHandlers.ofString());

		RecordedRequest get = client.requests().get(0);
		assertFalse(get.hasBodyPublisher);
		assertArrayEquals(new byte[0], get.body);

		RecordedRequest post = client.requests().get(1);
		assertTrue(post.hasBodyPublisher);
		assertEquals(0, post.contentLength);
		assertArrayEquals(new byte[0], post.body);
	}

	@Test
	public void throwsQueuedErrorsAndFailsWhenTheQueueIsEmpty()
	{
		RecordingHttpClient client = new RecordingHttpClient().fail(new IOException("connection reset"));
		HttpRequest request = HttpRequest.newBuilder(TEST_URI).GET().build();

		IOException queued = assertThrows(IOException.class, () -> client.send(request, BodyHandlers.ofString()));
		assertEquals("connection reset", queued.getMessage());

		assertThrows(IOException.class, () -> client.send(request, BodyHandlers.ofString()));
		assertEquals(2, client.requests().size());
	}
}
