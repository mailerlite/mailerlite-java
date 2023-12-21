/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.exceptions;

import java.util.HashMap;

/**
 * The Class MailerLiteException.
 */
public class MailerLiteException extends Exception  {

    /** The code. */
    public int code; // the response code
    
    /** The message. */
    public String message = "";
    
    /** The response body. */
    public String responseBody = null;
    
    /** The errors. */
    public HashMap<String, String[]> errors = new HashMap<String, String[]>();
    
    /**
     * <p>Constructor for MailerListException.</p>
     *
     * @param message a {@link java.lang.String} object.
     */
    public MailerLiteException(String message) {
        
        super(message);
    }
}
