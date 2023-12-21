/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.campaigns;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.util.JsonSerializationDeserializationStrategy;

/**
 * The Class CampaignScheduler.
 */
public class CampaignScheduler {

	/** The api object reference. */
	private MailerLite apiObjectReference;
	
	/** The campaign scheduler body. */
	private CampaignSchedulerBody campaignSchedulerBody = new CampaignSchedulerBody();
	
	/** The schedule settings. */
	private CampaignScheduleSettings scheduleSettings = new CampaignScheduleSettings();
	
	/** The resend settings. */
	private CampaignScheduleSettings resendSettings = new CampaignScheduleSettings();
	
	/**
	 * Instantiates a new campaign scheduler.
	 *
	 * @param apiReference the api reference
	 */
	public CampaignScheduler(MailerLite apiReference)
	{
		this.apiObjectReference = apiReference;
	}
	
	/**
	 * Schedule settings.
	 *
	 * @return the campaign schedule settings
	 */
	public CampaignScheduleSettings scheduleSettings()
	{
		return this.scheduleSettings;
	}
	
	/**
	 * Resend settings.
	 *
	 * @return the campaign schedule settings
	 */
	public CampaignScheduleSettings resendSettings()
	{
		return this.resendSettings;
	}
	
	/**
	 * Delivery.
	 *
	 * @param delivery the delivery
	 * @return the campaign scheduler
	 */
	public CampaignScheduler delivery(String delivery)
	{
		this.campaignSchedulerBody.delivery = delivery;
		return this;
	}
	
	
	/**
	 * Schedule.
	 *
	 * @param campaignId the campaign id
	 * @return the single campaign
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SingleCampaign schedule(String campaignId) throws MailerLiteException
	{
		String endpoint = "/campaigns/".concat(campaignId).concat("/schedule");
		
		MailerLiteApi api = new MailerLiteApi();
        api.setToken(apiObjectReference.getToken());
        
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();
        
        campaignSchedulerBody.resend = resendSettings.getSettings();
        campaignSchedulerBody.schedule = scheduleSettings.getSettings();
        
        String json = gson.toJson(campaignSchedulerBody);
        
        SingleCampaign campaign = api.postRequest(endpoint, json, SingleCampaign.class);
        
        return campaign;
	}
	
	
	/**
	 * Cancel.
	 *
	 * @param campaignId the campaign id
	 * @return the single campaign
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SingleCampaign cancel(String campaignId) throws MailerLiteException
	{
		String endpoint = "/campaigns/".concat(campaignId).concat("/cancel");
		
		MailerLiteApi api = new MailerLiteApi();
        api.setToken(apiObjectReference.getToken());
        
        SingleCampaign campaign = api.postRequest(endpoint, "{}", SingleCampaign.class);
        
        return campaign;
	}
}
