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
 * The Class JsonResponseError.
 */
public class JsonResponseError {

    /** The message. */
    public String message = "";
    
    /** The errors. */
    public HashMap<String, String[]> errors = new HashMap<String, String[]>();
    
    /** The error code, for example "version_conflict", or null. */
    public String code;
    
    /** The current etag of the email, or null. */
    public String etag;
}
