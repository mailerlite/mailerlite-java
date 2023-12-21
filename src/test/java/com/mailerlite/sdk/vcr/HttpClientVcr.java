/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.vcr;

import java.io.IOException;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodySubscriber;
import java.net.http.HttpResponse.PushPromiseHandler;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;

/**
 * The Class HttpClientVcr.
 */
public class HttpClientVcr extends HttpClient {

	/** The client. */
	private HttpClient client;
	
	/** The tape. */
	private VcrTape tape;
	
	/**
	 * Replacement for the HttpClient class so that we can record and replay the MailerLite API responses.
	 */
	public HttpClientVcr()
	{
		// we keep an instance of HttpClient around to use if there isn't a recorded request
		client = HttpClient.newHttpClient();
	}
	
	/**
	 * Sets the recording tape.
	 *
	 * @param tape the new tape
	 */
	public void setTape(VcrTape tape)
	{
		this.tape = tape;
	}
	
	/*
	 * All the Overrides below are needed by the HttpClient abstract class
	 */
	
	/**
	 * Cookie handler.
	 *
	 * @return the optional
	 */
	@Override
	public Optional<CookieHandler> cookieHandler() {
		
		return this.client.cookieHandler();
	}

	
	/**
	 * Connect timeout.
	 *
	 * @return the optional
	 */
	@Override
	public Optional<Duration> connectTimeout() {
		
		return this.client.connectTimeout();
	}

	
	/**
	 * Follow redirects.
	 *
	 * @return the redirect
	 */
	@Override
	public Redirect followRedirects() {
		
		return this.client.followRedirects();
	}

	
	/**
	 * Proxy.
	 *
	 * @return the optional
	 */
	@Override
	public Optional<ProxySelector> proxy() {
		
		return this.client.proxy();
	}

	
	/**
	 * Ssl context.
	 *
	 * @return the SSL context
	 */
	@Override
	public SSLContext sslContext() {
		
		return this.client.sslContext();
	}

	
	/**
	 * Ssl parameters.
	 *
	 * @return the SSL parameters
	 */
	@Override
	public SSLParameters sslParameters() {
		
		return this.client.sslParameters();
	}

	
	/**
	 * Authenticator.
	 *
	 * @return the optional
	 */
	@Override
	public Optional<Authenticator> authenticator() {
		
		return this.client.authenticator();
	}

	
	/**
	 * Version.
	 *
	 * @return the version
	 */
	@Override
	public Version version() {
		
		return this.client.version();
	}

	
	/**
	 * Executor.
	 *
	 * @return the optional
	 */
	@Override
	public Optional<Executor> executor() {
		
		return this.client.executor();
	}
	
	
    /**
     * The Class StringSubscriber.
     */
    static final class StringSubscriber implements Subscriber<ByteBuffer> {
        
        /** The wrapped. */
        final BodySubscriber<String> wrapped;
        
        /**
         * Instantiates a new string subscriber.
         *
         * @param wrapped the wrapped
         */
        StringSubscriber(BodySubscriber<String> wrapped) {
            this.wrapped = wrapped;
        }
        
        /**
         * On subscribe.
         *
         * @param subscription the subscription
         */
        @Override
        public void onSubscribe(Subscription subscription) {
            wrapped.onSubscribe(subscription);
        }
        
        /**
         * On next.
         *
         * @param item the item
         */
        @Override
        public void onNext(ByteBuffer item) { wrapped.onNext(List.of(item)); }
        
        /**
         * On error.
         *
         * @param throwable the throwable
         */
        @Override
        public void onError(Throwable throwable) { wrapped.onError(throwable); }
        
        /**
         * On complete.
         */
        @Override
        public void onComplete() { wrapped.onComplete(); }
    }

    /**
     * Checks the tape for a recorded response of the request.
     * If it doesn't exist, it runs the request and adds the response to the tape
     *
     * @param <T> the generic type
     * @param request the request
     * @param responseBodyHandler the response body handler
     * @return the http response
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws InterruptedException the interrupted exception
     */
	@SuppressWarnings("unchecked")
	@Override
	public <T> HttpResponse<T> send(HttpRequest request, BodyHandler<T> responseBodyHandler)
			throws IOException, InterruptedException {
			
		if (tape != null) {
			
			HttpClientVcrResponse vcrResponse = tape.getRecordedResponse(request);
			
			if (vcrResponse != null) {
				
				return (HttpResponse<T>) vcrResponse;
			}
		}
		
		HttpResponse<T> response = client.send(request, responseBodyHandler);
			
		this.tape.addRecordedResponse(request,  response);
		
		return response;
	}

	/**
	 * Send async.
	 *
	 * @param <T> the generic type
	 * @param request the request
	 * @param responseBodyHandler the response body handler
	 * @return the completable future
	 */
	/*
	 * We don't use sendAsync in the MailerLite SDK, we keep these methods for completeness
	 */
	@Override
	public <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request, BodyHandler<T> responseBodyHandler) {
		
		return this.client.sendAsync(request, responseBodyHandler);
	}

	
	/**
	 * Send async.
	 *
	 * @param <T> the generic type
	 * @param request the request
	 * @param responseBodyHandler the response body handler
	 * @param pushPromiseHandler the push promise handler
	 * @return the completable future
	 */
	@Override
	public <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request, BodyHandler<T> responseBodyHandler,
			PushPromiseHandler<T> pushPromiseHandler) {
		
		return this.client.sendAsync(request,  responseBodyHandler, pushPromiseHandler);
	}
}
