/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.reports;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.automations.AutomationEndpoint;
import com.mailerlite.sdk.exceptions.MailerLiteException;

/**
 * The reports of an automation. The date and shop setters apply to the monthly totals only.
 */
public class AutomationReports extends AutomationStatsRequest<AutomationReports> {

	/** The api object reference. */
	private MailerLite apiObjectReference;
	
	/** The automation endpoint. */
	private AutomationEndpoint automation;
	
	/**
	 * Instantiates a new automation reports.
	 *
	 * @param apiRef the api ref
	 * @param forAutomationId the automation id
	 */
	public AutomationReports(MailerLite apiRef, String forAutomationId)
	{
		apiObjectReference = apiRef;
		automation = new AutomationEndpoint(apiRef, forAutomationId);
	}
	
	/**
	 * Gets the completed and queued run counts of every step.
	 *
	 * @return the step run count list
	 * @throws MailerLiteException the mailer lite exception
	 */
	public StepRunCountList stepRunCounts() throws MailerLiteException
	{
		return automation.api().getRequest(automation.path("/steps/run-counts"), StepRunCountList.class);
	}
	
	/**
	 * Gets the completed and queued run counts of a step.
	 *
	 * @param stepId the step id
	 * @return the single step run count
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SingleStepRunCount stepRunCount(String stepId) throws MailerLiteException
	{
		return automation.api().getRequest(automation.path("/steps/run-counts/".concat(stepId)), SingleStepRunCount.class);
	}
	
	/**
	 * Gets the run counts of every trigger and of manual runs.
	 *
	 * @return the single triggers run counts
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SingleTriggersRunCounts triggerRunCounts() throws MailerLiteException
	{
		return automation.api().getRequest(automation.path("/triggers/run-counts"), SingleTriggersRunCounts.class);
	}
	
	/**
	 * Gets the run counts of a trigger.
	 *
	 * @param triggerId the trigger id
	 * @return the single trigger run count
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SingleTriggerRunCount triggerRunCount(String triggerId) throws MailerLiteException
	{
		return automation.api().getRequest(automation.path("/triggers/run-counts/".concat(triggerId)), SingleTriggerRunCount.class);
	}
	
	/**
	 * Gets the stats of every A/B test step.
	 *
	 * @return the A/B test stats list
	 * @throws MailerLiteException the mailer lite exception
	 */
	public AbTestStatsList abStepsStats() throws MailerLiteException
	{
		return automation.api().getRequest(automation.path("/ab-steps-stats"), AbTestStatsList.class);
	}
	
	/**
	 * Gets the stats of an A/B test step.
	 *
	 * @param stepId the A/B test step id
	 * @return the single A/B test stats
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SingleAbTestStats abStepStats(String stepId) throws MailerLiteException
	{
		return automation.api().getRequest(automation.path("/ab-steps-stats/".concat(stepId)), SingleAbTestStats.class);
	}
	
	/**
	 * Subscribers who reached a step.
	 *
	 * @param stepId the step id
	 * @return the run subscribers retriever
	 */
	public AutomationRunSubscribersRetriever stepSubscribers(String stepId)
	{
		return new AutomationRunSubscribersRetriever(apiObjectReference, automation.path("/activity/steps/".concat(stepId).concat("/subscribers")));
	}
	
	/**
	 * Subscribers who entered through a trigger.
	 *
	 * @param triggerId the trigger id
	 * @return the run subscribers retriever
	 */
	public AutomationRunSubscribersRetriever triggerSubscribers(String triggerId)
	{
		return new AutomationRunSubscribersRetriever(apiObjectReference, automation.path("/activity/triggers/".concat(triggerId).concat("/subscribers")));
	}
	
	/**
	 * Orders attributed to the automation.
	 *
	 * @return the automation order activity retriever
	 */
	public AutomationOrderActivityRetriever orderActivity()
	{
		return new AutomationOrderActivityRetriever(apiObjectReference, automation.path("/order-activity"));
	}
	
	/**
	 * Gets the email totals of the automation.
	 *
	 * @return the single automation email stats
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SingleAutomationEmailStats monthlyEmailTotals() throws MailerLiteException
	{
		return automation.api().getRequest(automation.path("/monthly-totals/email").concat(this.getQueryParameters()), SingleAutomationEmailStats.class);
	}
	
	/**
	 * Gets the e-commerce totals of the automation.
	 *
	 * @return the single automation ecommerce stats
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SingleAutomationEcommerceStats monthlyEcommerceTotals() throws MailerLiteException
	{
		return automation.api().getRequest(automation.path("/monthly-totals/ecommerce").concat(this.getQueryParameters()), SingleAutomationEcommerceStats.class);
	}
	
	
	
	/**
	 * Gets the single instance of AutomationReports.
	 *
	 * @return single instance of AutomationReports
	 */
	@Override
	protected AutomationReports getInstance() {
		return this;
	}
}
