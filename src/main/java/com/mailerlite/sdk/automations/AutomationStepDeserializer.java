/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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

/**
 * The Class AutomationStepDeserializer.
 */
public class AutomationStepDeserializer {

	/**
	 * Deserialize automation step.
	 *
	 * @param stepObj the step obj
	 * @param gson the gson
	 * @return the automation step
	 */
	public AutomationStep deserializeAutomationStep(JsonObject stepObj, Gson gson)
	{
		String type = stepObj.get("type").getAsString();
		
		switch (type) {
		
			case "email":
				EmailStep emailStep = gson.fromJson(stepObj, EmailStep.class);
				emailStep.parseDates();
				return emailStep;
				
			case "delay":
				DelayStep delayStep = gson.fromJson(stepObj, DelayStep.class);
				delayStep.parseDates();
				return delayStep;
				
			case "abtest":
				AbStep abStep = gson.fromJson(stepObj, AbStep.class);
				abStep.parseDates();
				return abStep;

			case "action":
				ActionStep actionStep = deserializeActionStep(stepObj, gson);
				actionStep.parseDates();
				return actionStep;

			case "condition":
				ConditionStep conditionStep = deserializeConditionStep(stepObj, gson);
				conditionStep.parseDates();
				return conditionStep;
		}
		
		return null;
	}
	
	
	/**
	 * Deserialize action step.
	 *
	 * @param stepObj the step obj
	 * @param gson the gson
	 * @return the action step
	 */
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
	
	/**
	 * Deserialize condition step.
	 *
	 * @param stepObj the step obj
	 * @param gson the gson
	 * @return the condition step
	 */
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
