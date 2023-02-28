package com.mailerlite.sdk.campaigns;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.util.PaginatedRequest;

public class SubscriberActivity extends PaginatedRequest<SubscriberActivity> {


	private MailerLite apiObjectReference;
	
	public SubscriberActivity(MailerLite apiRefernce)
	{
		apiObjectReference = apiRefernce;
	}
	
	public SubscriberActivity filter(String filter)
	{
		this.addQueryParameter("filter", filter);
		return this;
	}
	
	public SubscriberActivity search(String search)
	{
		this.addQueryParameter("search", search);
		return this;
	}
	
	public SubscriberActivity sort(String sort)
	{
		this.addQueryParameter("sort", sort);
		return this;
	}
	
	
	public CampaignSubscriberActivityList get(String campaignId) throws MailerLiteException
	{
		String endpoint = "/campaigns/".concat(campaignId).concat("/reports/subscriber-activity").concat(this.getQueryParameters());
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		CampaignSubscriberActivityList activity = api.getRequest(endpoint, CampaignSubscriberActivityList.class);
		
		return activity;
	}

	@Override
	protected SubscriberActivity getInstance() {
		return this;
	}
}
