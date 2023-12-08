/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.exceptions;

import java.util.HashMap;

public class JsonResponseError {

    public String message = "";
    public HashMap<String, String[]> errors = new HashMap<String, String[]>();
}
