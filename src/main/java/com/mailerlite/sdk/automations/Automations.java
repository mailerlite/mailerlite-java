/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations;

import java.util.List;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.MailerLiteResponse;
import com.mailerlite.sdk.automations.activity.AutomationSubscriberActivityRetriever;
import com.mailerlite.sdk.automations.reports.AutomationEmails;
import com.mailerlite.sdk.automations.reports.AutomationReports;
import com.mailerlite.sdk.automations.steps.AutomationSteps;
import com.mailerlite.sdk.automations.templates.AutomationTemplateRetriever;
import com.mailerlite.sdk.automations.triggers.AutomationTriggers;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.util.JsonBody;

/**
 * The Class Automations.
 */
public class Automations {
	
	/** The api object reference. */
	private MailerLite apiObjectReference;
	
	/**
	 * Instantiates a new automations.
	 *
	 * @param apiReference the api reference
	 */
	public Automations(MailerLite apiReference)
	{
		apiObjectReference = apiReference;
	}
	
	/**
	 * Retriever.
	 *
	 * @return the automation retriever
	 */
	public AutomationRetriever retriever()
	{
		return new AutomationRetriever(apiObjectReference);
	}
	
	/**
	 * Subscriber activity retriever.
	 *
	 * @return the automation subscriber activity retriever
	 */
	public AutomationSubscriberActivityRetriever subscriberActivityRetriever()
	{
		return new AutomationSubscriberActivityRetriever(apiObjectReference);
	}
	
	/**
	 * Builder.
	 *
	 * @return the automation builder
	 */
	public AutomationBuilder builder()
	{
		return new AutomationBuilder(apiObjectReference);
	}
	
	/**
	 * Triggers of an automation.
	 *
	 * @param automationId the automation id
	 * @return the automation triggers
	 */
	public AutomationTriggers triggers(String automationId)
	{
		return new AutomationTriggers(apiObjectReference, automationId);
	}
	
	/**
	 * Steps of an automation.
	 *
	 * @param automationId the automation id
	 * @return the automation steps
	 */
	public AutomationSteps steps(String automationId)
	{
		return new AutomationSteps(apiObjectReference, automationId);
	}
	
	/**
	 * Emails of an automation.
	 *
	 * @param automationId the automation id
	 * @return the automation emails
	 */
	public AutomationEmails emails(String automationId)
	{
		return new AutomationEmails(apiObjectReference, automationId);
	}
	
	/**
	 * Reports of an automation.
	 *
	 * @param automationId the automation id
	 * @return the automation reports
	 */
	public AutomationReports reports(String automationId)
	{
		return new AutomationReports(apiObjectReference, automationId);
	}
	
	/**
	 * Automation templates.
	 *
	 * @return the automation template retriever
	 */
	public AutomationTemplateRetriever templates()
	{
		return new AutomationTemplateRetriever(apiObjectReference);
	}
	
	/**
	 * Creates an automation from a template.
	 *
	 * @param templateId the automation template id
	 * @return the single automation
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SingleAutomation selectTemplate(String templateId) throws MailerLiteException
	{
		return postForAutomation("/automations/select-template/".concat(templateId));
	}
	
	/**
	 * Delete.
	 *
	 * @param automationId the automation id
	 * @return the mailer lite response
	 * @throws MailerLiteException the mailer lite exception
	 */
	public MailerLiteResponse delete(String automationId) throws MailerLiteException
	{
		return api().deleteRequest("/automations/".concat(automationId), MailerLiteResponse.class);
	}
	
	/**
	 * Copies an automation.
	 *
	 * @param automationId the automation id
	 * @return the copied automation
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SingleAutomation copy(String automationId) throws MailerLiteException
	{
		return postForAutomation("/automations/".concat(automationId).concat("/copy"));
	}
	
	/**
	 * Enables an automation.
	 *
	 * @param automationId the automation id
	 * @return the single automation
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SingleAutomation enable(String automationId) throws MailerLiteException
	{
		return postForAutomation("/automations/".concat(automationId).concat("/enable"));
	}
	
	/**
	 * Disables an automation.
	 *
	 * @param automationId the automation id
	 * @return the single automation
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SingleAutomation disable(String automationId) throws MailerLiteException
	{
		return postForAutomation("/automations/".concat(automationId).concat("/disable"));
	}
	
	/**
	 * Sends a test of the automation emails to an address.
	 *
	 * @param automationId the automation id
	 * @param email the email address
	 * @return the mailer lite response
	 * @throws MailerLiteException the mailer lite exception
	 */
	public MailerLiteResponse test(String automationId, String email) throws MailerLiteException
	{
		AutomationTestBody body = new AutomationTestBody();
		body.email = email;
		
		return postForResponse("/automations/".concat(automationId).concat("/test"), body);
	}
	
	/**
	 * Adds subscribers to an automation.
	 *
	 * @param automationId the automation id
	 * @param emails the subscriber emails, can be null
	 * @param groupIds the ids of groups whose subscribers to add, can be null
	 * @param stepId the step to start them at, null for the first step
	 * @return the mailer lite response
	 * @throws MailerLiteException the mailer lite exception
	 */
	public MailerLiteResponse addSubscribers(String automationId, List<String> emails, List<String> groupIds, String stepId) throws MailerLiteException
	{
		AutomationAddSubscribersBody body = new AutomationAddSubscribersBody();
		body.emails = emails;
		body.groups = groupIds;
		body.stepId = stepId;
		
		return postForResponse("/automations/".concat(automationId).concat("/add-subscribers"), body);
	}
	
	/**
	 * Adds subscribers who completed, were canceled or failed back to the automation.
	 *
	 * @param automationId the automation id
	 * @param readdSubscribersTo where to add them back: "last_completed_step", "selected_step" or "last_failed_step"
	 * @param selectedStepId the step id, used with "selected_step"
	 * @param filter the filter selecting the subscribers
	 * @return the mailer lite response
	 * @throws MailerLiteException the mailer lite exception
	 */
	public MailerLiteResponse readd(String automationId, String readdSubscribersTo, String selectedStepId, AutomationActivityFilter filter) throws MailerLiteException
	{
		AutomationReaddBody body = new AutomationReaddBody();
		body.readdSubscribersTo = readdSubscribersTo;
		body.selectedStepId = selectedStepId;
		body.filter = filter;
		
		return postForResponse("/automations/".concat(automationId).concat("/readd"), body);
	}
	
	/**
	 * Adds the subscribers of the filtered automation activity to groups.
	 *
	 * @param automationId the automation id
	 * @param destinationGroupIds the group ids
	 * @param filter the filter selecting the activity
	 * @return the mailer lite response
	 * @throws MailerLiteException the mailer lite exception
	 */
	public MailerLiteResponse addActivityToGroups(String automationId, List<String> destinationGroupIds, AutomationActivityFilter filter) throws MailerLiteException
	{
		AutomationActivityActionBody body = new AutomationActivityActionBody();
		body.destinationGroups = destinationGroupIds;
		body.filter = filter;
		
		return postForResponse("/automations/".concat(automationId).concat("/activity/add-to-group"), body);
	}
	
	/**
	 * Cancels the filtered active automation runs.
	 *
	 * @param automationId the automation id
	 * @param filter the filter selecting the activity
	 * @return the mailer lite response
	 * @throws MailerLiteException the mailer lite exception
	 */
	public MailerLiteResponse cancelActivity(String automationId, AutomationActivityFilter filter) throws MailerLiteException
	{
		AutomationActivityActionBody body = new AutomationActivityActionBody();
		body.filter = filter;
		
		return postForResponse("/automations/".concat(automationId).concat("/activity/cancel-automation"), body);
	}
	
	/**
	 * Skips the current delay of the filtered active automation runs.
	 *
	 * @param automationId the automation id
	 * @param filter the filter selecting the activity
	 * @return the mailer lite response
	 * @throws MailerLiteException the mailer lite exception
	 */
	public MailerLiteResponse skipActivityDelay(String automationId, AutomationActivityFilter filter) throws MailerLiteException
	{
		AutomationActivityActionBody body = new AutomationActivityActionBody();
		body.filter = filter;
		
		return postForResponse("/automations/".concat(automationId).concat("/activity/skip-delay"), body);
	}
	
	private SingleAutomation postForAutomation(String endpoint) throws MailerLiteException
	{
		return api().postRequest(endpoint, "", SingleAutomation.class, new SingleAutomationDeserializer());
	}
	
	private MailerLiteResponse postForResponse(String endpoint, Object body) throws MailerLiteException
	{
		return api().postRequest(endpoint, JsonBody.toJson(body), MailerLiteResponse.class);
	}
	
	private MailerLiteApi api()
	{
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		return api;
	}
}
