/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.reports;

import com.google.gson.JsonObject;
import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.automations.AutomationEndpoint;
import com.mailerlite.sdk.emails.SingleEmail;
import com.mailerlite.sdk.exceptions.MailerLiteException;

/**
 * The emails of an automation. The date and shop setters apply to the stats methods only.
 */
public class AutomationEmails extends AutomationStatsRequest<AutomationEmails> {

	/** The automation endpoint. */
	private AutomationEndpoint automation;
	
	/**
	 * Instantiates a new automation emails.
	 *
	 * @param apiRef the api ref
	 * @param forAutomationId the automation id
	 */
	public AutomationEmails(MailerLite apiRef, String forAutomationId)
	{
		automation = new AutomationEndpoint(apiRef, forAutomationId);
	}
	
	/**
	 * Gets the HTML content of an email.
	 *
	 * @param emailId the email id
	 * @return the single automation email content
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SingleAutomationEmailContent getContent(String emailId) throws MailerLiteException
	{
		return automation.api().getRequest(automation.path("/emails/".concat(emailId).concat("/content")), SingleAutomationEmailContent.class);
	}
	
	/**
	 * Replaces the HTML content of an email.
	 *
	 * @param emailId the email id
	 * @param html the html
	 * @return the updated email
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SingleEmail updateContent(String emailId, String html) throws MailerLiteException
	{
		JsonObject body = new JsonObject();
		body.addProperty("html", html);
		
		SingleEmail response = automation.api().putRequest(automation.path("/emails/".concat(emailId).concat("/content")), body.toString(), SingleEmail.class);
		
		if (response.email != null) {
			response.email.parseDates();
		}
		
		return response;
	}
	
	/**
	 * Gets the engagement and deliverability stats of an email.
	 *
	 * @param emailId the email id
	 * @return the single automation email stats
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SingleAutomationEmailStats reportStats(String emailId) throws MailerLiteException
	{
		return automation.api().getRequest(automation.path("/emails/".concat(emailId).concat("/report-stats")).concat(this.getQueryParameters()), SingleAutomationEmailStats.class);
	}
	
	/**
	 * Gets the e-commerce stats of an email.
	 *
	 * @param emailId the email id
	 * @return the single automation ecommerce stats
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SingleAutomationEcommerceStats ecommerceOverviewStats(String emailId) throws MailerLiteException
	{
		return automation.api().getRequest(automation.path("/emails/".concat(emailId).concat("/ecommerce-overview-stats")).concat(this.getQueryParameters()), SingleAutomationEcommerceStats.class);
	}
	
	/**
	 * Gets the reasons subscribers gave for unsubscribing from an email.
	 *
	 * @param emailId the email id
	 * @return the unsubscribe reason list
	 * @throws MailerLiteException the mailer lite exception
	 */
	public UnsubscribeReasonList unsubscribeReasons(String emailId) throws MailerLiteException
	{
		return automation.api().getRequest(automation.path("/emails/".concat(emailId).concat("/unsubscribe-reasons")), UnsubscribeReasonList.class);
	}
	
	
	
	/**
	 * Gets the single instance of AutomationEmails.
	 *
	 * @return single instance of AutomationEmails
	 */
	@Override
	protected AutomationEmails getInstance() {
		return this;
	}
}
