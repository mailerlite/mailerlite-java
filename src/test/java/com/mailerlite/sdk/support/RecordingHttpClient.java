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

/**
 * A fake HttpClient for tests. It keeps each request that it gets, and it returns the responses
 * from a queue. It sends no request to the network.
 */
public class RecordingHttpClient extends HttpClient {

	/** The requests that the client got, in sequence. */
	private final List<RecordedRequest> requests = new ArrayList<RecordedRequest>();

	/** The responses and the errors to return, in sequence. */
	private final Deque<Object> responses = new ArrayDeque<Object>();

	/**
	 * Makes a new client and sets it in the MailerLiteHttpClientFactory.
	 *
	 * @return the new client
	 */
	public static RecordingHttpClient install()
	{
		RecordingHttpClient client = new RecordingHttpClient();
		MailerLiteHttpClientFactory.getInstance().setClient(client);

		return client;
	}

	/**
	 * Removes the client from the MailerLiteHttpClientFactory.
	 */
	public static void uninstall()
	{
		MailerLiteHttpClientFactory.getInstance().setClient(null);
	}

	/**
	 * Adds a response to the queue.
	 *
	 * @param status the status code
	 * @param body the response body
	 * @return this client
	 */
	public RecordingHttpClient respond(int status, String body)
	{
		return respond(status, body, Collections.<String, String>emptyMap());
	}

	/**
	 * Adds a response with headers to the queue.
	 *
	 * @param status the status code
	 * @param body the response body
	 * @param headers the response headers
	 * @return this client
	 */
	public RecordingHttpClient respond(int status, String body, Map<String, String> headers)
	{
		Map<String, List<String>> headerMap = new LinkedHashMap<String, List<String>>();

		for (Map.Entry<String, String> header : headers.entrySet()) {
			headerMap.put(header.getKey(), Collections.singletonList(header.getValue()));
		}

		responses.add(new QueuedResponse(status, body, HttpHeaders.of(headerMap, (name, value) -> true)));

		return this;
	}

	/**
	 * Adds an error to the queue. The client throws it for the next request.
	 *
	 * @param error an IOException or an InterruptedException
	 * @return this client
	 */
	public RecordingHttpClient fail(Exception error)
	{
		responses.add(error);

		return this;
	}

	/**
	 * Gets the requests that the client got.
	 *
	 * @return the requests
	 */
	public List<RecordedRequest> requests()
	{
		return requests;
	}

	/**
	 * Gets the last request that the client got.
	 *
	 * @return the last request
	 */
	public RecordedRequest lastRequest()
	{
		if (requests.isEmpty()) {
			throw new AssertionError("The client got no request");
		}

		return requests.get(requests.size() - 1);
	}

	/**
	 * Gets the number of queued responses that no request used.
	 *
	 * @return the number of queued responses
	 */
	public int pendingResponses()
	{
		return responses.size();
	}

	/**
	 * Keeps the request and returns the next queued response.
	 *
	 * @param <T> the body type
	 * @param request the request
	 * @param responseBodyHandler the body handler
	 * @return the response
	 * @throws IOException if the queue has an IOException, or if the queue is empty
	 * @throws InterruptedException if the queue has an InterruptedException
	 */
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

	/**
	 * Converts the queued body with the body handler of the caller.
	 *
	 * @param <T> the body type
	 * @param queued the queued response
	 * @param handler the body handler
	 * @return the body
	 */
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
				// nothing to cancel
			}
		});

		subscriber.onNext(Collections.singletonList(ByteBuffer.wrap(queued.body.getBytes(StandardCharsets.UTF_8))));
		subscriber.onComplete();

		return subscriber.getBody().toCompletableFuture().join();
	}

	/**
	 * Not supported.
	 *
	 * @param <T> the body type
	 * @param request the request
	 * @param responseBodyHandler the body handler
	 * @return nothing
	 */
	@Override
	public <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request, BodyHandler<T> responseBodyHandler)
	{
		throw new UnsupportedOperationException("RecordingHttpClient supports only send()");
	}

	/**
	 * Not supported.
	 *
	 * @param <T> the body type
	 * @param request the request
	 * @param responseBodyHandler the body handler
	 * @param pushPromiseHandler the push promise handler
	 * @return nothing
	 */
	@Override
	public <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request, BodyHandler<T> responseBodyHandler,
			PushPromiseHandler<T> pushPromiseHandler)
	{
		throw new UnsupportedOperationException("RecordingHttpClient supports only send()");
	}

	/**
	 * Cookie handler.
	 *
	 * @return an empty value
	 */
	@Override
	public Optional<CookieHandler> cookieHandler()
	{
		return Optional.empty();
	}

	/**
	 * Connect timeout.
	 *
	 * @return an empty value
	 */
	@Override
	public Optional<Duration> connectTimeout()
	{
		return Optional.empty();
	}

	/**
	 * Follow redirects.
	 *
	 * @return NEVER
	 */
	@Override
	public Redirect followRedirects()
	{
		return Redirect.NEVER;
	}

	/**
	 * Proxy.
	 *
	 * @return an empty value
	 */
	@Override
	public Optional<ProxySelector> proxy()
	{
		return Optional.empty();
	}

	/**
	 * Ssl context.
	 *
	 * @return null
	 */
	@Override
	public SSLContext sslContext()
	{
		return null;
	}

	/**
	 * Ssl parameters.
	 *
	 * @return null
	 */
	@Override
	public SSLParameters sslParameters()
	{
		return null;
	}

	/**
	 * Authenticator.
	 *
	 * @return an empty value
	 */
	@Override
	public Optional<Authenticator> authenticator()
	{
		return Optional.empty();
	}

	/**
	 * Version.
	 *
	 * @return HTTP_1_1
	 */
	@Override
	public Version version()
	{
		return Version.HTTP_1_1;
	}

	/**
	 * Executor.
	 *
	 * @return an empty value
	 */
	@Override
	public Optional<Executor> executor()
	{
		return Optional.empty();
	}

	/**
	 * A request that the client got, with the raw bytes of its body.
	 */
	public static class RecordedRequest {

		/** The method. */
		public final String method;

		/** The full URI. */
		public final URI uri;

		/** The headers. */
		public final HttpHeaders headers;

		/** True if the request has a body publisher. GET() and DELETE() give no publisher. */
		public final boolean hasBodyPublisher;

		/** The content length of the body publisher, or -2 if there is no publisher. */
		public final long contentLength;

		/** The raw bytes of the body. The array is empty if there is no body. */
		public final byte[] body;

		/**
		 * Reads the request and its body.
		 *
		 * @param request the request
		 */
		RecordedRequest(HttpRequest request)
		{
			this.method = request.method();
			this.uri = request.uri();
			this.headers = request.headers();
			this.hasBodyPublisher = request.bodyPublisher().isPresent();
			this.contentLength = request.bodyPublisher().map(p -> p.contentLength()).orElse(-2L);
			this.body = request.bodyPublisher().map(RecordedRequest::readAll).orElse(new byte[0]);
		}

		/**
		 * Gets the body as a UTF-8 string.
		 *
		 * @return the body
		 */
		public String bodyString()
		{
			return new String(body, StandardCharsets.UTF_8);
		}

		/**
		 * Gets the first value of a header, or null.
		 *
		 * @param name the header name
		 * @return the value or null
		 */
		public String header(String name)
		{
			return headers.firstValue(name).orElse(null);
		}

		/**
		 * Reads all the bytes of a body publisher.
		 *
		 * @param publisher the body publisher
		 * @return the bytes
		 */
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

	/**
	 * A queued response.
	 */
	private static class QueuedResponse {

		/** The status. */
		final int status;

		/** The body. */
		final String body;

		/** The headers. */
		final HttpHeaders headers;

		/**
		 * Instantiates a new queued response.
		 *
		 * @param status the status
		 * @param body the body
		 * @param headers the headers
		 */
		QueuedResponse(int status, String body, HttpHeaders headers)
		{
			this.status = status;
			this.body = body;
			this.headers = headers;
		}
	}

	/**
	 * The response that the client returns.
	 *
	 * @param <T> the body type
	 */
	private static class FakeResponse<T> implements HttpResponse<T> {

		/** The request. */
		private final HttpRequest request;

		/** The queued response. */
		private final QueuedResponse queued;

		/** The body. */
		private final T body;

		/**
		 * Instantiates a new fake response.
		 *
		 * @param request the request
		 * @param queued the queued response
		 * @param body the body
		 */
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
