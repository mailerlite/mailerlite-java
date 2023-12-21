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
}
