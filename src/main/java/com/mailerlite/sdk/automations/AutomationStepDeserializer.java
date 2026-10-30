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
import com.mailerlite.sdk.automations.steps.GenericAutomationStep;
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
import com.mailerlite.sdk.util.SdkGson;

/**
 * The Class AutomationStepDeserializer.
 */
public class AutomationStepDeserializer implements JsonDeserializer<AutomationStep> {

	/**
	 * Deserializes any automation step into its typed class, or GenericAutomationStep for unknown types.
	 *
	 * @param json the json
	 * @param typeOfT the type of T
	 * @param context the context
	 * @return the automation step
	 * @throws JsonParseException the json parse exception
	 */
	@Override
	public AutomationStep deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		return deserializeAutomationStep(json.getAsJsonObject(), SdkGson.plain());
	}
	
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
		
			case GenericAutomationStep.TYPE_EMAIL:
				EmailStep emailStep = gson.fromJson(stepObj, EmailStep.class);
				emailStep.parseDates();
				return emailStep;
				
			case GenericAutomationStep.TYPE_DELAY:
				DelayStep delayStep = gson.fromJson(stepObj, DelayStep.class);
				delayStep.parseDates();
				return delayStep;
				
			case GenericAutomationStep.TYPE_AB_TEST:
				AbStep abStep = gson.fromJson(stepObj, AbStep.class);
				abStep.parseDates();
				return abStep;

			case GenericAutomationStep.TYPE_ACTION:
				AutomationStep actionStep = deserializeActionStep(stepObj, gson);
				actionStep.parseDates();
				return actionStep;

			case GenericAutomationStep.TYPE_CONDITION:
				ConditionStep conditionStep = deserializeConditionStep(stepObj, gson);
				conditionStep.parseDates();
				return conditionStep;
		}
		
		return gson.fromJson(stepObj, GenericAutomationStep.class);
	}
	
	
	/**
	 * Deserialize action step.
	 *
	 * @param stepObj the step obj
	 * @param gson the gson
	 * @return the action step
	 */
	private AutomationStep deserializeActionStep(JsonObject stepObj, Gson gson)
	{
		if (stepObj.get("action_type") == null || stepObj.get("action_type").isJsonNull()) {
			return gson.fromJson(stepObj, GenericAutomationStep.class);
		}
		
		String actionType = stepObj.get("action_type").getAsString();
		
		switch (actionType) {
		
			case GenericAutomationStep.ACTION_TYPE_UPDATE_CUSTOM_FIELD:
				
				return gson.fromJson(stepObj, UpdateCustomFieldAction.class);
				
			case GenericAutomationStep.ACTION_TYPE_MARK_AS_UNSUBSCRIBED:
				
				return gson.fromJson(stepObj, MarkUnsubscribedAction.class);
				
			case GenericAutomationStep.ACTION_TYPE_REMOVE_FROM_GROUP:
				
				return gson.fromJson(stepObj, RemoveFromGroupAction.class);
				
			case GenericAutomationStep.ACTION_TYPE_GO_TO_STEP:
				
				return gson.fromJson(stepObj, GoToStepAction.class);
				
			case GenericAutomationStep.ACTION_TYPE_COPY_TO_GROUP:
				
				return gson.fromJson(stepObj, CopyToGroupAction.class);
				
			case GenericAutomationStep.ACTION_TYPE_MOVE_TO_GROUP:
				
				return gson.fromJson(stepObj, MoveToGroupAction.class);
		}
		
		return gson.fromJson(stepObj, GenericAutomationStep.class);
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
		
		JsonArray conditions = stepObj.has("conditions") && stepObj.get("conditions").isJsonArray() ? stepObj.getAsJsonArray("conditions") : new JsonArray();
		
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
				
			default:
				conditionItems.add(gson.fromJson(conditionObj, ConditionBase.class));
				break;
			}
		}
		
		
		step.conditions = conditionItems.toArray();
		
		return step;
	}
}
