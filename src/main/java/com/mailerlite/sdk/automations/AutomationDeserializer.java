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
import com.mailerlite.sdk.automations.triggers.TriggerAbandonedCart;
import com.mailerlite.sdk.automations.triggers.TriggerClicksLink;
import com.mailerlite.sdk.automations.triggers.TriggerDate;
import com.mailerlite.sdk.automations.triggers.TriggerFieldUpdated;
import com.mailerlite.sdk.automations.triggers.TriggerFormCompleted;
import com.mailerlite.sdk.automations.triggers.TriggerPurchasedAnyProduct;
import com.mailerlite.sdk.automations.triggers.TriggerPurchasedFromCategory;
import com.mailerlite.sdk.automations.triggers.TriggerPurchasedSpecificProduct;
import com.mailerlite.sdk.automations.triggers.TriggerSubscriberJoinsGroup;
import com.mailerlite.sdk.util.JsonSerializationDeserializationStrategy;

/**
 * The Class AutomationDeserializer.
 */
public class AutomationDeserializer implements JsonDeserializer<Automation> {

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
		
		JsonArray automationSteps = jsonObject.get("steps").getAsJsonArray();
		
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();
		
		Automation automation = gson.fromJson(json, Automation.class);
		
		automation.parseDates();
		
		ArrayList<AutomationStep> steps = new ArrayList<AutomationStep>();
		
		AutomationStepDeserializer stepDeserializer = new AutomationStepDeserializer();
		
		for (JsonElement jsonStep : automationSteps) {
			
			JsonObject stepObj = jsonStep.getAsJsonObject();

			steps.add(stepDeserializer.deserializeAutomationStep(stepObj, gson));
		}
		
		automation.steps = steps.toArray();
		
		
		JsonArray automationTriggers = jsonObject.get("triggers").getAsJsonArray();
		
		ArrayList<AutomationTrigger> triggers = new ArrayList<AutomationTrigger>();
		
		for (JsonElement jsonTrigger : automationTriggers) {
			
			JsonObject triggerObj = jsonTrigger.getAsJsonObject();
			
			String type = triggerObj.get("type").getAsString();
			
			switch (type) {
			
				case "subscriber_joins_group":
					triggers.add(gson.fromJson(jsonObject, TriggerSubscriberJoinsGroup.class));
					break;
					
				case "clicks_link":
					triggers.add(gson.fromJson(jsonObject, TriggerClicksLink.class));
					break;
					
				case "field_updated":
					triggers.add(gson.fromJson(jsonObject, TriggerFieldUpdated.class));
					break;
					
				case "anniversary":
					triggers.add(gson.fromJson(jsonObject, TriggerDate.class));
					break;
					
				case "exact_date":
					triggers.add(gson.fromJson(jsonObject, TriggerDate.class));
					break;
					
				case "form_completed":
					triggers.add(gson.fromJson(jsonObject, TriggerFormCompleted.class));
					break;
					
				case "purchased_any_product":
					triggers.add(gson.fromJson(jsonObject, TriggerPurchasedAnyProduct.class));
					break;
					
				case "purchased_specific_product":
					triggers.add(gson.fromJson(jsonObject, TriggerPurchasedSpecificProduct.class));
					break;
					
				case "purchased_from_category":
					triggers.add(gson.fromJson(jsonObject, TriggerPurchasedFromCategory.class));
					break;
					
				case "abandoned_cart":
					triggers.add(gson.fromJson(jsonObject, TriggerAbandonedCart.class));
					break;
			}
		}
		
		automation.triggers = triggers.toArray();
		
		return automation;
	}
	

}
