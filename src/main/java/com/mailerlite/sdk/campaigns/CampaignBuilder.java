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

/**
 * The Class CampaignBuilder.
 */
public class CampaignBuilder {

	/** The api object reference. */
	private MailerLite apiObjectReference;
	
	/** The builder body. */
	private CampaignBuilderBody builderBody = new CampaignBuilderBody();
	
	/** The ab settings. */
	private CampaignAbSettings abSettings = new CampaignAbSettings();
	
	/** The resend settings. */
	private CampaignResendSettings resendSettings = new CampaignResendSettings();
	
	/**
	 * Instantiates a new campaign builder.
	 *
	 * @param apiRefernce the api refernce
	 */
	public CampaignBuilder(MailerLite apiRefernce)
	{
		apiObjectReference = apiRefernce;
	}
	
	/**
	 * Ab settings.
	 *
	 * @return the campaign ab settings
	 */
	public CampaignAbSettings abSettings()
	{
		return this.abSettings;
	}
	
	/**
	 * Resend settings.
	 *
	 * @return the campaign resend settings
	 */
	public CampaignResendSettings resendSettings()
	{
		return this.resendSettings;
	}
	
	/**
	 * Clear.
	 */
	public void clear()
	{
		this.builderBody = new CampaignBuilderBody();
		this.abSettings.clear();
		this.resendSettings.clear();
	}
	
	/**
	 * Email.
	 *
	 * @param email the email
	 * @return the campaign builder
	 */
	public CampaignBuilder email(EmailBase email)
	{
		this.builderBody.emails.add(email);
		return this;
	}
	
	/**
	 * Name.
	 *
	 * @param name the name
	 * @return the campaign builder
	 */
	public CampaignBuilder name(String name)
	{
		this.builderBody.name = name;
		return this;
	}
	
	/**
	 * Type.
	 *
	 * @param type the type
	 * @return the campaign builder
	 */
	public CampaignBuilder type(String type)
	{
		this.builderBody.type = type;
		return this;
	}
	
	/**
	 * Group id.
	 *
	 * @param groupId the group id
	 * @return the campaign builder
	 */
	public CampaignBuilder groupId(String groupId)
	{
		this.builderBody.groupIds.add(groupId);
		return this;
	}
	
	/**
	 * Group ids.
	 *
	 * @param groupIds the group ids
	 * @return the campaign builder
	 */
	public CampaignBuilder groupIds(String[] groupIds)
	{
		this.builderBody.groupIds.addAll(Arrays.asList(groupIds));
		return this;
	}
	
	/**
	 * Segment id.
	 *
	 * @param segmentId the segment id
	 * @return the campaign builder
	 */
	public CampaignBuilder segmentId(String segmentId)
	{
		this.builderBody.segmentIds.add(segmentId);
		return this;
	}
	
	/**
	 * Segment ids.
	 *
	 * @param segmentIds the segment ids
	 * @return the campaign builder
	 */
	public CampaignBuilder segmentIds(String[] segmentIds)
	{
		this.builderBody.segmentIds.addAll(Arrays.asList(segmentIds));
		return this;
	}
	
	
	/**
	 * Creates the.
	 *
	 * @return the single campaign
	 * @throws MailerLiteException the mailer lite exception
	 */
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
	
	
	/**
	 * Update.
	 *
	 * @param campaignId the campaign id
	 * @return the single campaign
	 * @throws MailerLiteException the mailer lite exception
	 */
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
