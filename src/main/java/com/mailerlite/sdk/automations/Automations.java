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

public class Automations {
	
	private MailerLite apiObjectReference;
	
	public Automations(MailerLite apiReference)
	{
		apiObjectReference = apiReference;
	}
	
	public AutomationRetriever retriever()
	{
		return new AutomationRetriever(apiObjectReference);
	}
	
	public AutomationSubscriberActivityRetriever subscriberActivityRetriever()
	{
		return new AutomationSubscriberActivityRetriever(apiObjectReference);
	}
}
