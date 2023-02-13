package com.mailerlite.sdk.campaigns;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.exceptions.MailerLiteException;

public class Campaigns {

	private MailerLite apiObjectReference;
	
	public Campaigns(MailerLite apiReference)
	{
		apiObjectReference = apiReference;
	}
	
	
	public CampaignRetriever retriever() {
		
		return new CampaignRetriever(apiObjectReference);
	}
	
	public CampaignBuilder builder() {
		return new CampaignBuilder(apiObjectReference);
	}
}
