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
import com.mailerlite.sdk.automations.steps.AbStep;
import com.mailerlite.sdk.automations.steps.AutomationStep;
import com.mailerlite.sdk.automations.steps.DelayStep;
import com.mailerlite.sdk.automations.steps.EmailStep;
import com.mailerlite.sdk.automations.steps.action.ActionStep;
import com.mailerlite.sdk.automations.steps.action.CopyToGroupAction;
import com.mailerlite.sdk.automations.steps.action.GoToStepAction;
import com.mailerlite.sdk.automations.steps.action.MarkUnsubscribedAction;
import com.mailerlite.sdk.automations.steps.action.MoveToGroupAction;
import com.mailerlite.sdk.automations.steps.action.RemoveFromGroupAction;
import com.mailerlite.sdk.automations.steps.action.UpdateCustomFieldAction;
import com.mailerlite.sdk.automations.steps.condition.CampaignActivityCondition;
import com.mailerlite.sdk.automations.steps.condition.ConditionBase;
import com.mailerlite.sdk.automations.steps.condition.ConditionStep;
import com.mailerlite.sdk.automations.steps.condition.CustomFieldsCondition;
import com.mailerlite.sdk.automations.steps.condition.GroupMembershipCondition;
import com.mailerlite.sdk.automations.steps.condition.SegmentMembershipCondition;
import com.mailerlite.sdk.automations.steps.condition.WorkflowActivityCondition;
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

public class AutomationDeserializer implements JsonDeserializer<Automation> {

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
		
		ArrayList<AutomationStep> steps = new ArrayList<AutomationStep>();
		
		for (JsonElement jsonStep : automationSteps) {
			
			JsonObject stepObj = jsonStep.getAsJsonObject();
			String type = stepObj.get("type").getAsString();
			
			switch (type) {
			
				case "email":
					steps.add(gson.fromJson(jsonObject, EmailStep.class));
					break;
					
				case "delay":
					steps.add(gson.fromJson(jsonObject, DelayStep.class));
					break;
					
				case "abtest":
					steps.add(gson.fromJson(jsonObject, AbStep.class));
					break;
					
				case "action":
					steps.add(deserializeActionStep(stepObj, gson));
					break;
					
				case "condition":
					steps.add(deserializeConditionStep(stepObj, gson));
					break;
			}
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
	
	private ActionStep deserializeActionStep(JsonObject stepObj, Gson gson)
	{
		if (stepObj == null || stepObj.get("action_type") == null) {
			return null;
		}
		
		String actionType = stepObj.get("action_type").getAsString();
		
		switch (actionType) {
		
			case "update_custom_field":
				
				return gson.fromJson(stepObj, UpdateCustomFieldAction.class);
				
			case "mark_as_unsubscribed":
				
				return gson.fromJson(stepObj, MarkUnsubscribedAction.class);
				
			case "remove_from_group":
				
				return gson.fromJson(stepObj, RemoveFromGroupAction.class);
				
			case "go_to_step":
				
				return gson.fromJson(stepObj, GoToStepAction.class);
				
			case "copy_to_group":
				
				return gson.fromJson(stepObj, CopyToGroupAction.class);
				
			case "move_to_group":
				
				return gson.fromJson(stepObj, MoveToGroupAction.class);
		}
		
		return null;
	}
	
	private ConditionStep deserializeConditionStep(JsonObject stepObj, Gson gson)
	{
		
		ConditionStep step = gson.fromJson(stepObj, ConditionStep.class);
		
		JsonArray conditions = stepObj.get("conditions").getAsJsonArray();
		
		ArrayList<ConditionBase> conditionItems = new ArrayList<ConditionBase>();
		
		
		for (JsonElement el : conditions) {
			
			JsonObject conditionObj = el.getAsJsonObject();
			
			String conditionType = conditionObj.get("type").getAsString();
			
			switch (conditionType) {
			
			case "workflow_activity":
				conditionItems.add(gson.fromJson(conditionObj, WorkflowActivityCondition.class));
				break;
				
			case "campaign_activity":
				conditionItems.add(gson.fromJson(conditionObj, CampaignActivityCondition.class));
				break;
				
			case "custom_fields":
				conditionItems.add(gson.fromJson(conditionObj, CustomFieldsCondition.class));
				break;
				
			case "segment_membership":
				conditionItems.add(gson.fromJson(conditionObj, SegmentMembershipCondition.class));
				break;
				
			case "group_membership":
				conditionItems.add(gson.fromJson(conditionObj, GroupMembershipCondition.class));
				break;
			}
		}
		
		
		step.conditions = conditionItems.toArray();
		
		return step;
	}

}
