/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.triggers;

import java.util.Map;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteResponse;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.util.JsonBody;
import com.mailerlite.sdk.automations.AutomationEndpoint;

/**
 * The triggers of an automation.
 */
public class AutomationTriggers {

	/** The automation endpoint. */
	private AutomationEndpoint automation;
	
	/**
	 * Instantiates a new automation triggers.
	 *
	 * @param apiRef the api ref
	 * @param forAutomationId the automation id
	 */
	public AutomationTriggers(MailerLite apiRef, String forAutomationId)
	{
		automation = new AutomationEndpoint(apiRef, forAutomationId);
	}
	
	/**
	 * Gets the triggers of the automation.
	 *
	 * @return the automation trigger list
	 * @throws MailerLiteException the mailer lite exception
	 */
	public AutomationTriggerList get() throws MailerLiteException
	{
		return automation.api().getRequest(automation.path("/triggers"), AutomationTriggerList.class);
	}
	
	/**
	 * Gets a single trigger.
	 *
	 * @param triggerId the trigger id
	 * @return the single automation trigger
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SingleAutomationTrigger getSingle(String triggerId) throws MailerLiteException
	{
		return automation.api().getRequest(automation.path("/triggers/".concat(triggerId)), SingleAutomationTrigger.class);
	}
	
	/**
	 * Creates a trigger.
	 *
	 * @param type the trigger type, one of the GenericAutomationTrigger TYPE_ constants
	 * @param data the trigger configuration, can be null
	 * @return the single automation trigger
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SingleAutomationTrigger create(String type, Map<String, Object> data) throws MailerLiteException
	{
		AutomationTriggerBody body = new AutomationTriggerBody();
		body.type = type;
		body.data = data;
		
		return automation.api().postRequest(automation.path("/triggers"), JsonBody.toJson(body), SingleAutomationTrigger.class);
	}
	
	/**
	 * Updates a trigger.
	 *
	 * @param triggerId the trigger id
	 * @param data the trigger configuration
	 * @return the updated trigger with the automation trigger data
	 * @throws MailerLiteException the mailer lite exception
	 */
	public UpdatedAutomationTrigger update(String triggerId, Map<String, Object> data) throws MailerLiteException
	{
		AutomationTriggerBody body = new AutomationTriggerBody();
		body.data = data;
		
		return automation.api().putRequest(automation.path("/triggers/".concat(triggerId)), JsonBody.toJson(body), UpdatedAutomationTrigger.class);
	}
	
	/**
	 * Deletes a trigger.
	 *
	 * @param triggerId the trigger id
	 * @return the mailer lite response
	 * @throws MailerLiteException the mailer lite exception
	 */
	public MailerLiteResponse delete(String triggerId) throws MailerLiteException
	{
		return automation.api().deleteRequest(automation.path("/triggers/".concat(triggerId)), MailerLiteResponse.class);
	}
	
	
}
