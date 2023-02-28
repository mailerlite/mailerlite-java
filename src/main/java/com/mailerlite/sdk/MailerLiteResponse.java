package com.mailerlite.sdk;

import java.util.List;
import java.util.Map;

public class MailerLiteResponse {

    public int responseStatusCode;
    public int rateLimit;
    public int rateLimitRemaining;
    
    public Map<String, List<String>> headers;
    
    public void postDeserialze()
    {
    	// intentionally left empty
    }
}
