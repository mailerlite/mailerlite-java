/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 * Deserializes a single automation response, parsing its steps and triggers by type.
 */
public class SingleAutomationDeserializer implements JsonDeserializer<SingleAutomation> {

	/**
	 * Deserialize.
	 *
	 * @param json the json
	 * @param typeOfT the type of T
	 * @param context the context
	 * @return the single automation
	 * @throws JsonParseException the json parse exception
	 */
	@Override
	public SingleAutomation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		
        Gson gson = AutomationDeserializer.automationGson();
        
        return gson.fromJson(json, SingleAutomation.class);
	}
}
