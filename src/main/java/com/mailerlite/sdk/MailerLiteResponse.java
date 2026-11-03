/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk;

import java.util.List;
import java.util.Map;

/**
 * The Class MailerLiteResponse.
 */
public class MailerLiteResponse {

    /** The response status code. */
    public int responseStatusCode;
    
    /** The rate limit. */
    public int rateLimit;
    
    /** The rate limit remaining. */
    public int rateLimitRemaining;
    
    /** The headers. */
    public Map<String, List<String>> headers;

	/**
	 * Post deserialize.
	 */
	public void postDeserialize() {
		// intentionally left empty
		
	}
	
	/**
	 * Successful.
	 *
	 * @return the boolean
	 */
	public Boolean successful()
	{
		return responseStatusCode >= 200 && responseStatusCode < 300;
	}
	
	/**
	 * Tells if the API returned the stored response of an earlier request with the same idempotency key.
	 * <p>
	 * The SDK finds the header Idempotent-Replayed without case, and compares its value with "true" without case.
	 *
	 * @return true if the response is a replay
	 */
	public boolean isIdempotentReplay()
	{
		if (headers == null) {
			return false;
		}
		
		for (Map.Entry<String, List<String>> header : headers.entrySet()) {
			
			if (!"Idempotent-Replayed".equalsIgnoreCase(header.getKey()) || header.getValue() == null) {
				continue;
			}
			
			for (String value : header.getValue()) {
				
				if (value != null && "true".equalsIgnoreCase(value.trim())) {
					return true;
				}
			}
		}
		
		return false;
	}
}
