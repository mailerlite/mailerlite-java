/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.automations.activity.AutomationSubscriberActivityRetriever;

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
}
