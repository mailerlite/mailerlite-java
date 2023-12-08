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

public class CampaignScheduler {

	private MailerLite apiObjectReference;
	
	private CampaignSchedulerBody campaignSchedulerBody = new CampaignSchedulerBody();
	
	private CampaignScheduleSettings scheduleSettings = new CampaignScheduleSettings();
	
	private CampaignScheduleSettings resendSettings = new CampaignScheduleSettings();
	
	public CampaignScheduler(MailerLite apiReference)
	{
		this.apiObjectReference = apiReference;
	}
	
	public CampaignScheduleSettings scheduleSettings()
	{
		return this.scheduleSettings;
	}
	
	public CampaignScheduleSettings resendSettings()
	{
		return this.resendSettings;
	}
	
	public CampaignScheduler delivery(String delivery)
	{
		this.campaignSchedulerBody.delivery = delivery;
		return this;
	}
	
	
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
	
	
	public SingleCampaign cancel(String campaignId) throws MailerLiteException
	{
		String endpoint = "/campaigns/".concat(campaignId).concat("/cancel");
		
		MailerLiteApi api = new MailerLiteApi();
        api.setToken(apiObjectReference.getToken());
        
        SingleCampaign campaign = api.postRequest(endpoint, "{}", SingleCampaign.class);
        
        return campaign;
	}
}
