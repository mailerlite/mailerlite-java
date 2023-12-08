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

public class MailerLiteResponse {

    public int responseStatusCode;
    public int rateLimit;
    public int rateLimitRemaining;
    
    public Map<String, List<String>> headers;

	public void postDeserialize() {
		// intentionally left empty
		
	}
	
	public Boolean successful()
	{
		return responseStatusCode >= 200 && responseStatusCode < 300;
	}
}
