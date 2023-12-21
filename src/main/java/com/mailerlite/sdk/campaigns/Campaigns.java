/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.campaigns;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.MailerLiteResponse;
import com.mailerlite.sdk.exceptions.MailerLiteException;

/**
 * The Class Campaigns.
 */
public class Campaigns {

	/** The api object reference. */
	private MailerLite apiObjectReference;
	
	/**
	 * Instantiates a new campaigns.
	 *
	 * @param apiReference the api reference
	 */
	public Campaigns(MailerLite apiReference)
	{
		apiObjectReference = apiReference;
	}
	
	
	/**
	 * Retriever.
	 *
	 * @return the campaign retriever
	 */
	public CampaignRetriever retriever() {
		
		return new CampaignRetriever(apiObjectReference);
	}
	
	/**
	 * Builder.
	 *
	 * @return the campaign builder
	 */
	public CampaignBuilder builder() {
		return new CampaignBuilder(apiObjectReference);
	}
	
	/**
	 * Scheduler.
	 *
	 * @return the campaign scheduler
	 */
	public CampaignScheduler scheduler() {
		return new CampaignScheduler(apiObjectReference);
	}
	
	/**
	 * Subscriber activity.
	 *
	 * @return the subscriber activity
	 */
	public SubscriberActivity subscriberActivity()
	{
		return new SubscriberActivity(apiObjectReference);
	}
	
	/**
	 * Delete.
	 *
	 * @param campaignId the campaign id
	 * @return the mailer lite response
	 * @throws MailerLiteException the mailer lite exception
	 */
	public MailerLiteResponse delete(String campaignId) throws MailerLiteException
	{
		String endpoint = "/campaigns/".concat(campaignId);
		
		MailerLiteApi api = new MailerLiteApi();
        api.setToken(apiObjectReference.getToken());
        
        MailerLiteResponse response = api.deleteRequest(endpoint, MailerLiteResponse.class);
        
        return response;
	}

}
