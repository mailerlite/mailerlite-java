package com.mailerlite.sdk.campaigns;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.MailerLiteResponse;
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
	
	public CampaignScheduler scheduler() {
		return new CampaignScheduler(apiObjectReference);
	}
	
	public SubscriberActivity subscriberActivity()
	{
		return new SubscriberActivity(apiObjectReference);
	}
	
	public MailerLiteResponse delete(String campaignId) throws MailerLiteException
	{
		String endpoint = "/campaigns/".concat(campaignId);
		
		MailerLiteApi api = new MailerLiteApi();
        api.setToken(apiObjectReference.getToken());
        
        MailerLiteResponse response = api.deleteRequest(endpoint, MailerLiteResponse.class);
        
        return response;
	}

}
