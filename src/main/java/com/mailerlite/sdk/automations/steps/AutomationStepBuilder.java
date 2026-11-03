/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.steps;

import java.util.HashMap;
import java.util.Map;

import com.mailerlite.sdk.automations.SingleAutomation;
import com.mailerlite.sdk.automations.SingleAutomationDeserializer;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.util.JsonBody;
import com.mailerlite.sdk.automations.AutomationEndpoint;

/**
 * Creates, updates or copies an automation step.
 */
public class AutomationStepBuilder {

	/** The automation endpoint. */
	private AutomationEndpoint automation;
	
	/** The builder body. */
	private AutomationStepBuilderBody builderBody = new AutomationStepBuilderBody();
	
	/**
	 * Instantiates a new automation step builder.
	 *
	 * @param forAutomation the automation endpoint
	 */
	public AutomationStepBuilder(AutomationEndpoint forAutomation)
	{
		automation = forAutomation;
	}
	
	/**
	 * Clear.
	 */
	public void clear()
	{
		builderBody = new AutomationStepBuilderBody();
	}
	
	/**
	 * Type. Only sent on create.
	 *
	 * @param type the step type, one of the GenericAutomationStep TYPE_ constants
	 * @return the automation step builder
	 */
	public AutomationStepBuilder type(String type)
	{
		builderBody.type = type;
		return this;
	}
	
	/**
	 * Parent id.
	 *
	 * @param parentId the id of the step this step comes after
	 * @return the automation step builder
	 */
	public AutomationStepBuilder parentId(String parentId)
	{
		builderBody.parentId = parentId;
		return this;
	}
	
	/**
	 * Branch of the parent step. Sent on create and copy.
	 *
	 * @param branch one of the GenericAutomationStep BRANCH_ constants
	 * @return the automation step builder
	 */
	public AutomationStepBuilder branch(String branch)
	{
		builderBody.branch = branch;
		return this;
	}
	
	/**
	 * Branch that an existing step in the chosen condition branch moves to. Sent on create and copy.
	 *
	 * @param moveToBranch "yes" or "no"
	 * @return the automation step builder
	 */
	public AutomationStepBuilder moveToBranch(String moveToBranch)
	{
		builderBody.moveToBranch = moveToBranch;
		return this;
	}
	
	/**
	 * Step configuration. Sent on create and update.
	 *
	 * @param data the step configuration
	 * @return the automation step builder
	 */
	public AutomationStepBuilder data(Map<String, Object> data)
	{
		builderBody.data = data;
		return this;
	}
	
	/**
	 * Adds a value to the step configuration. Sent on create and update.
	 *
	 * @param key the key, for example "action_type"
	 * @param value the value
	 * @return the automation step builder
	 */
	public AutomationStepBuilder addData(String key, Object value)
	{
		if (builderBody.data == null) {
			builderBody.data = new HashMap<String, Object>();
		}
		
		builderBody.data.put(key, value);
		return this;
	}
	
	/**
	 * For delay steps, recalculates the wait of subscribers already in the delay. Only sent on update.
	 *
	 * @param rescheduleWaitingSubscribers the reschedule waiting subscribers
	 * @return the automation step builder
	 */
	public AutomationStepBuilder rescheduleWaitingSubscribers(Boolean rescheduleWaitingSubscribers)
	{
		builderBody.rescheduleWaitingSubscribers = rescheduleWaitingSubscribers;
		return this;
	}
	
	/**
	 * Creates the step.
	 *
	 * @return the single automation step
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SingleAutomationStep create() throws MailerLiteException
	{
		AutomationStepBuilderBody body = new AutomationStepBuilderBody();
		body.type = builderBody.type;
		body.parentId = builderBody.parentId;
		body.branch = builderBody.branch;
		body.moveToBranch = builderBody.moveToBranch;
		body.data = builderBody.data;
		
		return automation.api().postRequest(automation.path("/steps"), JsonBody.toJson(body), SingleAutomationStep.class);
	}
	
	/**
	 * Updates a step.
	 *
	 * @param stepId the step id
	 * @return the single automation step
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SingleAutomationStep update(String stepId) throws MailerLiteException
	{
		AutomationStepBuilderBody body = new AutomationStepBuilderBody();
		body.parentId = builderBody.parentId;
		body.data = builderBody.data;
		body.rescheduleWaitingSubscribers = builderBody.rescheduleWaitingSubscribers;
		
		return automation.api().putRequest(automation.path("/steps/".concat(stepId)), JsonBody.toJson(body), SingleAutomationStep.class);
	}
	
	/**
	 * Copies a step to the place set with parentId, branch and moveToBranch.
	 *
	 * @param stepId the id of the step to copy
	 * @return the updated automation
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SingleAutomation copy(String stepId) throws MailerLiteException
	{
		AutomationStepBuilderBody body = new AutomationStepBuilderBody();
		body.parentId = builderBody.parentId;
		body.branch = builderBody.branch;
		body.moveToBranch = builderBody.moveToBranch;
		
		return automation.api().postRequest(automation.path("/steps/".concat(stepId)).concat("/copy"), JsonBody.toJson(body), SingleAutomation.class, new SingleAutomationDeserializer());
	}
	
	
}
