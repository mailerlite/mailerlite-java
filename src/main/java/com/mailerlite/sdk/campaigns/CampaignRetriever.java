package com.mailerlite.sdk.campaigns;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.util.PaginatedRequest;


public class CampaignRetriever extends PaginatedRequest<CampaignRetriever> {

	private MailerLite apiObjectReference;
	
	public CampaignRetriever(MailerLite apiRefernce)
	{
		apiObjectReference = apiRefernce;
	}
	
	
	public CampaignRetriever filter(String name, String value)
	{
		this.addQueryParameter("filter[".concat(name).concat("]"), value);
		
		return this;
	}
		
	public CampaignsList get() throws MailerLiteException
	{
		String endpoint = "/campaigns/".concat(this.getQueryParameters());
	
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		CampaignsList campaigns = api.getRequest(endpoint, CampaignsList.class);
		
		campaigns.postDeserialize();
		
		return campaigns;
	}
	
	public SingleCampaign getSingle(String id) throws MailerLiteException
	{
		String endpoint = "/campaigns/".concat(id);
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		SingleCampaign campaign = api.getRequest(endpoint, SingleCampaign.class);
		
		return campaign;
	}


	@Override
	protected CampaignRetriever getInstance() {
		
		return this;
	}
}
