package com.mailerlite.sdk.automations;

import com.mailerlite.sdk.MailerLite;

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
}
