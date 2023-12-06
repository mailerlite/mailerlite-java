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
import com.mailerlite.sdk.automations.steps.condition.ConditionStep;
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
		
		return null;
	}

}
