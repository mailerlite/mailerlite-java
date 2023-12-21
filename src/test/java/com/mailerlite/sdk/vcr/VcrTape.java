/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.vcr;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodySubscriber;
import java.net.http.HttpResponse.BodySubscribers;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mailerlite.sdk.util.JsonSerializationDeserializationStrategy;
import com.mailerlite.sdk.vcr.HttpClientVcr.StringSubscriber;

/**
 * The Class VcrTape.
 */
public class VcrTape {

	/** The tape path. */
	private Path tapePath;
	
	/** The recorded responses. */
	private HashMap<String, HttpClientVcrResponse> recordedResponses = new HashMap<String, HttpClientVcrResponse>();
	
	/** The has new recordings. */
	private boolean hasNewRecordings = false;
	
	/**
	 * Searches the recorded responses for a response matching the hash of the given request.
	 *
	 * @param request the request
	 * @return the recorded response
	 */
	public HttpClientVcrResponse getRecordedResponse(HttpRequest request)
	{
		String requestHash = this.getRequestHash(request);
		
		if (this.recordedResponses.containsKey(requestHash)) {
			
			return this.recordedResponses.get(requestHash);
		}
		
		return null;
	}
	
	/**
	 * Adds a response to the tape.
	 * Each response is identified by its request hash. See getRequestHash for how the request hash is generated.
	 *
	 * @param <T> the generic type
	 * @param request the request
	 * @param response the response
	 */
	public <T> void addRecordedResponse(HttpRequest request, HttpResponse<T> response)
	{
		Map<String, List<String>> headers = response.headers().map();
		
		HttpClientVcrResponse resp = new HttpClientVcrResponse();
		resp.body = response.body().toString();
		resp.headers = headers;
		resp.statusCode = response.statusCode();
		
		String requestHash = this.getRequestHash(request);
		
		this.recordedResponses.put(requestHash, resp);
		this.hasNewRecordings = true;
	}
	
	/**
	 * Loads the tape in the given path and deserializes the tape contents into the recorded responses hashmap.
	 *
	 * @param path the path
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void loadTape(String path) throws IOException {
		
		this.tapePath = Path.of(path);
		
		if (Files.exists(this.tapePath)) {
			String fixture = Files.readString(this.tapePath);
	        Gson gson = new GsonBuilder()
	                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
	                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
	                .create();
	        
	        Type recordingsType = new TypeToken<HashMap<String, HttpClientVcrResponse>>(){}.getType();
	        
	        this.recordedResponses = gson.fromJson(fixture, recordingsType);
	        
	        if (this.recordedResponses == null) {
	        	this.recordedResponses = new HashMap<String, HttpClientVcrResponse>();
	        }
		}
	}
	
	/**
	 * Serializes and saves the recorded responses into the tape file .
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void saveTape() throws IOException
	{
		if (hasNewRecordings) {
			
	        Gson gson = new GsonBuilder()
	                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
	                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
	                .create();
	        String fixture = gson.toJson(this.recordedResponses);
	        
	        BufferedWriter writer = new BufferedWriter(new FileWriter(this.tapePath.toString()));
	        writer.write(fixture);
	        writer.close();
		}
	}
	
	/**
	 * Calculates the hash of a request according to the formula sha1({request url}{request method}{body contents}).
	 *
	 * @param request the request
	 * @return the request hash
	 */
	private String getRequestHash(HttpRequest request)
	{
		
		String url = request.uri().toString();
		String method = request.method();
		
		Optional<String> body = request.bodyPublisher().map(p -> {
			BodySubscriber<String> bodySubscriber = BodySubscribers.ofString(StandardCharsets.UTF_8);
			StringSubscriber flowSubscriber = new StringSubscriber(bodySubscriber);
			p.subscribe(flowSubscriber);
			return bodySubscriber.getBody().toCompletableFuture().join();
		});
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(url);
		stringBuilder.append(method);
		stringBuilder.append(body);
		
		return this.sha1Hash(stringBuilder.toString());
	}
	
	/**
	 * Returns the sha1 hash of the given string.
	 *
	 * @param value the value
	 * @return the string
	 */
	private String sha1Hash(String value)
	{
	    String sha1 = "";
	    try
	    {
	        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	        crypt.reset();
	        crypt.update(value.getBytes("UTF-8"));
	        sha1 = byteToHex(crypt.digest());
	    }
	    catch(NoSuchAlgorithmException e)
	    {
	        e.printStackTrace();
	    }
	    catch(UnsupportedEncodingException e)
	    {
	        e.printStackTrace();
	    }
	    return sha1;
	}
	
	/**
	 * Converts a byte array to a hex string.
	 *
	 * @param hash the hash
	 * @return the string
	 */
	private String byteToHex(final byte[] hash)
	{
	    Formatter formatter = new Formatter();
	    for (byte b : hash)
	    {
	        formatter.format("%02x", b);
	    }
	    String result = formatter.toString();
	    formatter.close();
	    return result;
	}
}
