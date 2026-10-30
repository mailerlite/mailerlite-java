/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * The shared Gson instance with the SDK exclusion strategies and no custom type adapters.
 */
public class SdkGson {

	/** The gson instance. */
	private static final Gson GSON = new GsonBuilder()
			.addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
			.addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
			.create();
	
	/**
	 * Returns the shared Gson instance.
	 *
	 * @return the gson instance
	 */
	public static Gson plain()
	{
		return GSON;
	}
}
