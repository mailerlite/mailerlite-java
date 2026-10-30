/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.util;

/**
 * Serializes request bodies the same way for every endpoint.
 */
public class JsonBody {

	/**
	 * Serializes a request body to JSON.
	 *
	 * @param body the body
	 * @return the JSON string
	 */
	public static String toJson(Object body)
	{
        return SdkGson.plain().toJson(body);
	}
}
