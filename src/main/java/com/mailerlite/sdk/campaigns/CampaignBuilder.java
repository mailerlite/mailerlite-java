/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.campaigns;

import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.emails.EmailBase;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.util.JsonSerializationDeserializationStrategy;

public class CampaignBuilder {

	private MailerLite apiObjectReference;
	
	private CampaignBuilderBody builderBody = new CampaignBuilderBody();
	
	private CampaignAbSettings abSettings = new CampaignAbSettings();
	private CampaignResendSettings resendSettings = new CampaignResendSettings();
	
	public CampaignBuilder(MailerLite apiRefernce)
	{
		apiObjectReference = apiRefernce;
	}
	
	public CampaignAbSettings abSettings()
	{
		return this.abSettings;
	}
	
	public CampaignResendSettings resendSettings()
	{
		return this.resendSettings;
	}
	
	public void clear()
	{
		this.builderBody = new CampaignBuilderBody();
		this.abSettings.clear();
		this.resendSettings.clear();
	}
	
	public CampaignBuilder email(EmailBase email)
	{
		this.builderBody.emails.add(email);
		return this;
	}
	
	public CampaignBuilder name(String name)
	{
		this.builderBody.name = name;
		return this;
	}
	
	public CampaignBuilder type(String type)
	{
		this.builderBody.type = type;
		return this;
	}
	
	public CampaignBuilder groupId(String groupId)
	{
		this.builderBody.groupIds.add(groupId);
		return this;
	}
	
	public CampaignBuilder groupIds(String[] groupIds)
	{
		this.builderBody.groupIds.addAll(Arrays.asList(groupIds));
		return this;
	}
	
	public CampaignBuilder segmentId(String segmentId)
	{
		this.builderBody.segmentIds.add(segmentId);
		return this;
	}
	
	public CampaignBuilder segmentIds(String[] segmentIds)
	{
		this.builderBody.segmentIds.addAll(Arrays.asList(segmentIds));
		return this;
	}
	
	
	public SingleCampaign create() throws MailerLiteException
	{
		MailerLiteApi api = new MailerLiteApi();
        api.setToken(apiObjectReference.getToken());
        
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();
        
        builderBody.abSettings = abSettings.getSettings();
        builderBody.resendSettings = resendSettings.getSettings();
        
        String json = gson.toJson(builderBody);
        
        SingleCampaign campaign = api.postRequest("/campaigns", json, SingleCampaign.class);
        
        return campaign;
	}
	
	
	public SingleCampaign update(String campaignId) throws MailerLiteException
	{
		MailerLiteApi api = new MailerLiteApi();
        api.setToken(apiObjectReference.getToken());
        
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();
        
        builderBody.abSettings = abSettings.getSettings();
        builderBody.resendSettings = resendSettings.getSettings();
        
        String json = gson.toJson(builderBody);
        
        SingleCampaign campaign = api.putRequest("/campaigns/".concat(campaignId), json, SingleCampaign.class);
        
        return campaign;
	}
}
