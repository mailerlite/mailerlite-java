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
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mailerlite.sdk.automations.triggers.AutomationTrigger;
import com.mailerlite.sdk.automations.triggers.GenericAutomationTrigger;
import com.mailerlite.sdk.automations.triggers.TriggerAbandonedCart;
import com.mailerlite.sdk.automations.triggers.TriggerClicksLink;
import com.mailerlite.sdk.automations.triggers.TriggerDate;
import com.mailerlite.sdk.automations.triggers.TriggerFieldUpdated;
import com.mailerlite.sdk.automations.triggers.TriggerFormCompleted;
import com.mailerlite.sdk.automations.triggers.TriggerPurchasedAnyProduct;
import com.mailerlite.sdk.automations.triggers.TriggerPurchasedFromCategory;
import com.mailerlite.sdk.automations.triggers.TriggerPurchasedSpecificProduct;
import com.mailerlite.sdk.automations.triggers.TriggerSubscriberJoinsGroup;
import com.mailerlite.sdk.util.SdkGson;

/**
 * Deserializes any automation trigger into its typed class, or GenericAutomationTrigger for unknown types.
 */
public class AutomationTriggerDeserializer implements JsonDeserializer<AutomationTrigger> {

	/**
	 * Deserialize.
	 *
	 * @param json the json
	 * @param typeOfT the type of T
	 * @param context the context
	 * @return the automation trigger
	 * @throws JsonParseException the json parse exception
	 */
	@Override
	public AutomationTrigger deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		return deserializeTrigger(json.getAsJsonObject());
	}
	
	/**
	 * Deserializes a trigger object.
	 *
	 * @param triggerObj the trigger object
	 * @return the automation trigger
	 */
	public AutomationTrigger deserializeTrigger(JsonObject triggerObj)
	{
		Gson gson = SdkGson.plain();
		JsonElement type = triggerObj.get("type");
		
		switch (type == null || type.isJsonNull() ? "" : type.getAsString()) {
		
			case GenericAutomationTrigger.TYPE_SUBSCRIBER_JOINS_GROUP:
				return gson.fromJson(triggerObj, TriggerSubscriberJoinsGroup.class);
				
			case GenericAutomationTrigger.TYPE_CLICKS_LINK:
				return gson.fromJson(triggerObj, TriggerClicksLink.class);
				
			case GenericAutomationTrigger.TYPE_FIELD_UPDATED:
				return gson.fromJson(triggerObj, TriggerFieldUpdated.class);
				
			case GenericAutomationTrigger.TYPE_ANNIVERSARY:
			case GenericAutomationTrigger.TYPE_EXACT_DATE:
				return gson.fromJson(triggerObj, TriggerDate.class);
				
			case GenericAutomationTrigger.TYPE_FORM_COMPLETED:
				return gson.fromJson(triggerObj, TriggerFormCompleted.class);
				
			case GenericAutomationTrigger.TYPE_PURCHASED_ANY_PRODUCT:
				return gson.fromJson(triggerObj, TriggerPurchasedAnyProduct.class);
				
			case GenericAutomationTrigger.TYPE_PURCHASED_SPECIFIC_PRODUCT:
				return gson.fromJson(triggerObj, TriggerPurchasedSpecificProduct.class);
				
			case GenericAutomationTrigger.TYPE_PURCHASED_FROM_CATEGORY:
				return gson.fromJson(triggerObj, TriggerPurchasedFromCategory.class);
				
			case GenericAutomationTrigger.TYPE_ABANDONED_CART:
				return gson.fromJson(triggerObj, TriggerAbandonedCart.class);
				
			default:
				return gson.fromJson(triggerObj, GenericAutomationTrigger.class);
		}
	}
}
