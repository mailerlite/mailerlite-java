/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.support;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodySubscriber;
import java.net.http.HttpResponse.PushPromiseHandler;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Flow;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;

import com.mailerlite.sdk.util.MailerLiteHttpClientFactory;

public class RecordingHttpClient extends HttpClient {

	private final List<RecordedRequest> requests = new ArrayList<RecordedRequest>();

	private final Deque<Object> responses = new ArrayDeque<Object>();

	public static RecordingHttpClient install()
	{
		RecordingHttpClient client = new RecordingHttpClient();
		MailerLiteHttpClientFactory.getInstance().setClient(client);

		return client;
	}

	public static void uninstall()
	{
		MailerLiteHttpClientFactory.getInstance().setClient(null);
	}

	public RecordingHttpClient respond(int status, String body)
	{
		return respond(status, body, Collections.<String, String>emptyMap());
	}

	public RecordingHttpClient respond(int status, String body, Map<String, String> headers)
	{
		Map<String, List<String>> headerMap = new LinkedHashMap<String, List<String>>();

		for (Map.Entry<String, String> header : headers.entrySet()) {
			headerMap.put(header.getKey(), Collections.singletonList(header.getValue()));
		}

		responses.add(new QueuedResponse(status, body, HttpHeaders.of(headerMap, (name, value) -> true)));

		return this;
	}

	/** The error must be an IOException or an InterruptedException. */
	public RecordingHttpClient fail(Exception error)
	{
		responses.add(error);

		return this;
	}

	public List<RecordedRequest> requests()
	{
		return requests;
	}

	public RecordedRequest lastRequest()
	{
		if (requests.isEmpty()) {
			throw new AssertionError("The client got no request");
		}

		return requests.get(requests.size() - 1);
	}

	public int pendingResponses()
	{
		return responses.size();
	}

	@Override
	public <T> HttpResponse<T> send(HttpRequest request, BodyHandler<T> responseBodyHandler)
			throws IOException, InterruptedException
	{
		requests.add(new RecordedRequest(request));

		Object next = responses.poll();

		if (next == null) {
			throw new IOException("RecordingHttpClient has no queued response");
		}

		if (next instanceof IOException) {
			throw (IOException) next;
		}

		if (next instanceof InterruptedException) {
			throw (InterruptedException) next;
		}

		QueuedResponse queued = (QueuedResponse) next;

		return new FakeResponse<T>(request, queued, readBody(queued, responseBodyHandler));
	}

	private <T> T readBody(QueuedResponse queued, BodyHandler<T> handler)
	{
		BodySubscriber<T> subscriber = handler.apply(new HttpResponse.ResponseInfo() {

			@Override
			public int statusCode()
			{
				return queued.status;
			}

			@Override
			public HttpHeaders headers()
			{
				return queued.headers;
			}

			@Override
			public Version version()
			{
				return Version.HTTP_1_1;
			}
		});

		subscriber.onSubscribe(new Flow.Subscription() {

			@Override
			public void request(long n)
			{
				// the body is given at once below
			}

			@Override
			public void cancel()
			{
			}
		});

		subscriber.onNext(Collections.singletonList(ByteBuffer.wrap(queued.body.getBytes(StandardCharsets.UTF_8))));
		subscriber.onComplete();

		return subscriber.getBody().toCompletableFuture().join();
	}

	@Override
	public <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request, BodyHandler<T> responseBodyHandler)
	{
		throw new UnsupportedOperationException("RecordingHttpClient supports only send()");
	}

	@Override
	public <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request, BodyHandler<T> responseBodyHandler,
			PushPromiseHandler<T> pushPromiseHandler)
	{
		throw new UnsupportedOperationException("RecordingHttpClient supports only send()");
	}

	@Override
	public Optional<CookieHandler> cookieHandler()
	{
		return Optional.empty();
	}

	@Override
	public Optional<Duration> connectTimeout()
	{
		return Optional.empty();
	}

	@Override
	public Redirect followRedirects()
	{
		return Redirect.NEVER;
	}

	@Override
	public Optional<ProxySelector> proxy()
	{
		return Optional.empty();
	}

	@Override
	public SSLContext sslContext()
	{
		return null;
	}

	@Override
	public SSLParameters sslParameters()
	{
		return null;
	}

	@Override
	public Optional<Authenticator> authenticator()
	{
		return Optional.empty();
	}

	@Override
	public Version version()
	{
		return Version.HTTP_1_1;
	}

	@Override
	public Optional<Executor> executor()
	{
		return Optional.empty();
	}

	public static class RecordedRequest {

		public final String method;

		public final URI uri;

		public final HttpHeaders headers;

		/** True if the request has a body publisher. GET() and DELETE() give no publisher. */
		public final boolean hasBodyPublisher;

		/** The content length of the body publisher, or -2 if there is no publisher. */
		public final long contentLength;

		public final byte[] body;

		RecordedRequest(HttpRequest request)
		{
			this.method = request.method();
			this.uri = request.uri();
			this.headers = request.headers();
			this.hasBodyPublisher = request.bodyPublisher().isPresent();
			this.contentLength = request.bodyPublisher().map(p -> p.contentLength()).orElse(-2L);
			this.body = request.bodyPublisher().map(RecordedRequest::readAll).orElse(new byte[0]);
		}

		public String bodyString()
		{
			return new String(body, StandardCharsets.UTF_8);
		}

		public String header(String name)
		{
			return headers.firstValue(name).orElse(null);
		}

		private static byte[] readAll(HttpRequest.BodyPublisher publisher)
		{
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			CompletableFuture<Void> done = new CompletableFuture<Void>();

			publisher.subscribe(new Flow.Subscriber<ByteBuffer>() {

				@Override
				public void onSubscribe(Flow.Subscription subscription)
				{
					subscription.request(Long.MAX_VALUE);
				}

				@Override
				public void onNext(ByteBuffer item)
				{
					byte[] chunk = new byte[item.remaining()];
					item.get(chunk);
					bytes.write(chunk, 0, chunk.length);
				}

				@Override
				public void onError(Throwable throwable)
				{
					done.completeExceptionally(throwable);
				}

				@Override
				public void onComplete()
				{
					done.complete(null);
				}
			});

			done.join();

			return bytes.toByteArray();
		}
	}

	private static class QueuedResponse {

		final int status;

		final String body;

		final HttpHeaders headers;

		QueuedResponse(int status, String body, HttpHeaders headers)
		{
			this.status = status;
			this.body = body;
			this.headers = headers;
		}
	}

	private static class FakeResponse<T> implements HttpResponse<T> {

		private final HttpRequest request;

		private final QueuedResponse queued;

		private final T body;

		FakeResponse(HttpRequest request, QueuedResponse queued, T body)
		{
			this.request = request;
			this.queued = queued;
			this.body = body;
		}

		@Override
		public int statusCode()
		{
			return queued.status;
		}

		@Override
		public HttpRequest request()
		{
			return request;
		}

		@Override
		public Optional<HttpResponse<T>> previousResponse()
		{
			return Optional.empty();
		}

		@Override
		public HttpHeaders headers()
		{
			return queued.headers;
		}

		@Override
		public T body()
		{
			return body;
		}

		@Override
		public Optional<SSLSession> sslSession()
		{
			return Optional.empty();
		}

		@Override
		public URI uri()
		{
			return request.uri();
		}

		@Override
		public Version version()
		{
			return Version.HTTP_1_1;
		}
	}
}
