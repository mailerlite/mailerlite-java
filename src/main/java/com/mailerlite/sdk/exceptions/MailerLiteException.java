package com.mailerlite.sdk.exceptions;

import java.util.HashMap;

public class MailerLiteException extends Exception  {

    public int code; // the response code
    
    public String message = "";
    
    public String responseBody = null;
    
    public HashMap<String, String[]> errors = new HashMap<String, String[]>();
    
    /**
     * <p>Constructor for MailerSendException.</p>
     *
     * @param message a {@link java.lang.String} object.
     */
    public MailerLiteException(String message) {
        
        super(message);
    }
}
