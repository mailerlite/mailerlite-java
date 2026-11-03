/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.templates;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.util.CursorPaginatedRequest;

/**
 * The Class AutomationTemplateRetriever.
 */
public class AutomationTemplateRetriever extends CursorPaginatedRequest<AutomationTemplateRetriever> {

	/** The api object reference. */
	private MailerLite apiObjectReference;
	
	/**
	 * Instantiates a new automation template retriever.
	 *
	 * @param apiRef the api ref
	 */
	public AutomationTemplateRetriever(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}
	
	/**
	 * Adds a filter, for example "type" or "category".
	 *
	 * @param name the name
	 * @param value the value
	 * @return the automation template retriever
	 */
	public AutomationTemplateRetriever filter(String name, String value)
	{
		this.addQueryParameter("filter[".concat(name).concat("]"), value);
		
		return this;
	}
	
	/**
	 * Gets the automation templates.
	 *
	 * @return the automation template list
	 * @throws MailerLiteException the mailer lite exception
	 */
	public AutomationTemplateList get() throws MailerLiteException
	{
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		return api.getRequest("/automation-templates".concat(this.getQueryParameters()), AutomationTemplateList.class);
	}
	
	/**
	 * Gets the single instance of AutomationTemplateRetriever.
	 *
	 * @return single instance of AutomationTemplateRetriever
	 */
	@Override
	protected AutomationTemplateRetriever getInstance() {
		return this;
	}
}
