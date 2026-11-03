/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mailerlite.sdk.automations.steps.AutomationStep;
import com.mailerlite.sdk.automations.triggers.AutomationTrigger;
import com.mailerlite.sdk.util.JsonSerializationDeserializationStrategy;
import com.mailerlite.sdk.util.SdkGson;

/**
 * The Class AutomationDeserializer.
 */
public class AutomationDeserializer implements JsonDeserializer<Automation> {

	/** The gson instance that parses automations with this deserializer. */
	private static final Gson AUTOMATION_GSON = new GsonBuilder()
			.addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
			.addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
			.registerTypeAdapter(Automation.class, new AutomationDeserializer())
			.create();

	/**
	 * Deserialize.
	 *
	 * @param json the json
	 * @param typeOfT the type of T
	 * @param context the context
	 * @return the automation
	 * @throws JsonParseException the json parse exception
	 */
	@Override
	public Automation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		
		JsonObject jsonObject = json.getAsJsonObject();
		
        Gson gson = SdkGson.plain();
		
		Automation automation = gson.fromJson(json, Automation.class);
		
		automation.parseDates();
		
		ArrayList<AutomationStep> steps = new ArrayList<AutomationStep>();
		
		AutomationStepDeserializer stepDeserializer = new AutomationStepDeserializer();
		
		for (JsonElement jsonStep : jsonArrayOrEmpty(jsonObject, "steps")) {
			
			JsonObject stepObj = jsonStep.getAsJsonObject();

			steps.add(stepDeserializer.deserializeAutomationStep(stepObj, gson));
		}
		
		automation.steps = steps.toArray();
		
		
		ArrayList<AutomationTrigger> triggers = new ArrayList<AutomationTrigger>();
		
		AutomationTriggerDeserializer triggerDeserializer = new AutomationTriggerDeserializer();
		
		for (JsonElement jsonTrigger : jsonArrayOrEmpty(jsonObject, "triggers")) {
			
			JsonObject triggerObj = jsonTrigger.getAsJsonObject();
			
			triggers.add(triggerDeserializer.deserializeTrigger(triggerObj));
		}
		
		automation.triggers = triggers.toArray();
		
		return automation;
	}
	
	/**
	 * Gson that parses automations with this deserializer.
	 *
	 * @return the gson instance
	 */
	public static Gson automationGson()
	{
		return AUTOMATION_GSON;
	}
	
	private JsonArray jsonArrayOrEmpty(JsonObject jsonObject, String memberName)
	{
		JsonElement element = jsonObject.get(memberName);
		
		if (element == null || !element.isJsonArray()) {
			return new JsonArray();
		}
		
		return element.getAsJsonArray();
	}
	

}
