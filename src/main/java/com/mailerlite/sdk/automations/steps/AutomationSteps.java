/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.steps;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteResponse;
import com.mailerlite.sdk.automations.SingleAutomation;
import com.mailerlite.sdk.automations.SingleAutomationDeserializer;
import com.mailerlite.sdk.automations.reports.SingleAbTestStats;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.util.ApiRequest;
import com.mailerlite.sdk.util.JsonBody;
import com.mailerlite.sdk.automations.AutomationEndpoint;

/**
 * The steps of an automation.
 */
public class AutomationSteps {

	/** The automation endpoint. */
	private AutomationEndpoint automation;
	
	/**
	 * Instantiates a new automation steps.
	 *
	 * @param apiRef the api ref
	 * @param forAutomationId the automation id
	 */
	public AutomationSteps(MailerLite apiRef, String forAutomationId)
	{
		automation = new AutomationEndpoint(apiRef, forAutomationId);
	}
	
	/**
	 * Builder to create, update or copy a step.
	 *
	 * @return the automation step builder
	 */
	public AutomationStepBuilder builder()
	{
		return new AutomationStepBuilder(automation);
	}
	
	/**
	 * Gets the steps of the automation.
	 *
	 * @return the automation step list
	 * @throws MailerLiteException the mailer lite exception
	 */
	public AutomationStepList get() throws MailerLiteException
	{
		return automation.api().getRequest(automation.path("/steps/index"), AutomationStepList.class);
	}
	
	/**
	 * Gets a single step.
	 *
	 * @param stepId the step id
	 * @return the single automation step
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SingleAutomationStep getSingle(String stepId) throws MailerLiteException
	{
		return automation.api().getRequest(automation.path("/steps/".concat(stepId)), SingleAutomationStep.class);
	}
	
	/**
	 * Deletes a step.
	 *
	 * @param stepId the step id
	 * @return the mailer lite response
	 * @throws MailerLiteException the mailer lite exception
	 */
	public MailerLiteResponse delete(String stepId) throws MailerLiteException
	{
		return automation.api().deleteRequest(automation.path("/steps/".concat(stepId)), MailerLiteResponse.class);
	}
	
	/**
	 * Deletes a condition step and the given branch below it.
	 *
	 * @param stepId the step id
	 * @param branchToDelete "all", "yes" or "no"
	 * @return the mailer lite response
	 * @throws MailerLiteException the mailer lite exception
	 */
	public MailerLiteResponse delete(String stepId, String branchToDelete) throws MailerLiteException
	{
		ApiRequest query = new ApiRequest();
		query.addQueryParameter("branch_to_delete", branchToDelete);
		
		return automation.api().deleteRequest(automation.path("/steps/".concat(stepId)).concat(query.getQueryParameters()), MailerLiteResponse.class);
	}
	
	/**
	 * Moves a step to a new place in the workflow.
	 *
	 * @param draggedStepId the id of the step to move
	 * @param newParentStepId the step to place it after, null for the start of the workflow
	 * @param branch the branch of the new parent, one of the GenericAutomationStep BRANCH_ constants, can be null
	 * @param branchForChild the branch of the moved condition that the steps below it go to, "yes" or "no", can be null
	 * @return the updated automation
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SingleAutomation reorder(String draggedStepId, String newParentStepId, String branch, String branchForChild) throws MailerLiteException
	{
		AutomationStepReorderBody body = new AutomationStepReorderBody();
		body.draggedStepId = draggedStepId;
		body.newParentStepId = newParentStepId;
		body.branch = branch;
		body.branchForChild = branchForChild;
		
		return automation.api().postRequest(automation.path("/steps/reorder"), JsonBody.toJson(body), SingleAutomation.class, new SingleAutomationDeserializer());
	}
	
	/**
	 * Selects the winner of an A/B test step.
	 *
	 * @param stepId the A/B test step id
	 * @param abTestWinner the winning path
	 * @return the A/B test stats
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SingleAbTestStats selectAbTestWinner(String stepId, String abTestWinner) throws MailerLiteException
	{
		AbTestWinnerBody body = new AbTestWinnerBody();
		body.abTestWinner = abTestWinner;
		
		return automation.api().putRequest(automation.path("/steps/".concat(stepId)).concat("/select-ab-test-winner"), JsonBody.toJson(body), SingleAbTestStats.class);
	}
	
	
}
