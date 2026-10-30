/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations;

import java.util.ArrayList;
import java.util.Map;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.util.JsonBody;

/**
 * The Class AutomationBuilder.
 */
public class AutomationBuilder {

	/** The api object reference. */
	private MailerLite apiObjectReference;
	
	/** The builder body. */
	private AutomationBuilderBody builderBody = new AutomationBuilderBody();
	
	/**
	 * Instantiates a new automation builder.
	 *
	 * @param apiRef the api ref
	 */
	public AutomationBuilder(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}
	
	/**
	 * Clear.
	 */
	public void clear()
	{
		builderBody = new AutomationBuilderBody();
	}
	
	/**
	 * Name.
	 *
	 * @param name the name
	 * @return the automation builder
	 */
	public AutomationBuilder name(String name)
	{
		builderBody.name = name;
		return this;
	}
	
	/**
	 * Trigger data. Only sent on update, create() rejects it.
	 *
	 * @param triggerData the trigger data, for example "repeatable"
	 * @return the automation builder
	 */
	public AutomationBuilder triggerData(Map<String, Object> triggerData)
	{
		builderBody.triggerData = triggerData;
		return this;
	}
	
	/**
	 * Adds a trigger. Only sent on update, create() rejects it.
	 *
	 * @param trigger the trigger, with "type" and "data"
	 * @return the automation builder
	 */
	public AutomationBuilder addTrigger(Map<String, Object> trigger)
	{
		if (builderBody.triggers == null) {
			builderBody.triggers = new ArrayList<Map<String, Object>>();
		}
		
		builderBody.triggers.add(trigger);
		return this;
	}
	
	/**
	 * Creates a draft automation with the given name. The create endpoint accepts only the name,
	 * so add triggers with update() or the triggers endpoints.
	 *
	 * @return the single automation
	 * @throws MailerLiteException the mailer lite exception
	 * @throws IllegalStateException if triggerData or a trigger was set
	 */
	public SingleAutomation create() throws MailerLiteException
	{
		if (builderBody.triggerData != null || builderBody.triggers != null) {
			throw new IllegalStateException("POST /automations accepts only name. Set triggerData and triggers with update().");
		}
		
		MailerLiteApi api = new MailerLiteApi();
        api.setToken(apiObjectReference.getToken());
        
        return api.postRequest("/automations", JsonBody.toJson(builderBody), SingleAutomation.class, new SingleAutomationDeserializer());
	}
	
	/**
	 * Updates an automation. The API requires the name on every update.
	 *
	 * @param automationId the automation id
	 * @return the single automation
	 * @throws MailerLiteException the mailer lite exception
	 * @throws IllegalStateException if name was not set
	 */
	public SingleAutomation update(String automationId) throws MailerLiteException
	{
		if (builderBody.name == null || builderBody.name.isEmpty()) {
			throw new IllegalStateException("PUT /automations/{automation} requires name.");
		}
		
		MailerLiteApi api = new MailerLiteApi();
        api.setToken(apiObjectReference.getToken());
        
        return api.putRequest("/automations/".concat(automationId), JsonBody.toJson(builderBody), SingleAutomation.class, new SingleAutomationDeserializer());
	}
}
