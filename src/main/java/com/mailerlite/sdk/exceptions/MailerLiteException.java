/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.exceptions;

import java.util.HashMap;

import com.mailerlite.sdk.emails.EmailErrorCodes;

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
     * The {@code code} string of the API error body, for example "version_conflict", or null if the body has none.
     * The field {@code code} is the HTTP status.
     */
    public String errorCode = null;
    
    /**
     * The {@code etag} of the API error body, or null if the body has none.
     * A version conflict error has the current etag of the email.
     */
    public String etag = null;
    
    /**
     * <p>Constructor for MailerListException.</p>
     *
     * @param message a {@link java.lang.String} object.
     */
    public MailerLiteException(String message) {
        
        super(message);
    }
    
    /**
     * Makes an exception with a message and a cause, for example a network error.
     *
     * @param message the message
     * @param cause the error that caused this exception
     */
    public MailerLiteException(String message, Throwable cause) {
        
        super(message, cause);
    }
    
    /**
     * Tells if the error is a version conflict: the email changed after you read the etag that the write sent.
     * Then {@link #etag} is the current etag of the email.
     *
     * @return true if {@link #errorCode} is {@link EmailErrorCodes#VERSION_CONFLICT}
     */
    public boolean isVersionConflict() {
        
        return EmailErrorCodes.VERSION_CONFLICT.equals(this.errorCode);
    }
}
