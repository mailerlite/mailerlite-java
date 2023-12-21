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
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.util.PaginatedRequest;

/**
 * The Class SubscriberActivity.
 */
public class SubscriberActivity extends PaginatedRequest<SubscriberActivity> {


	/** The api object reference. */
	private MailerLite apiObjectReference;
	
	/**
	 * Instantiates a new subscriber activity.
	 *
	 * @param apiRefernce the api refernce
	 */
	public SubscriberActivity(MailerLite apiRefernce)
	{
		apiObjectReference = apiRefernce;
	}
	
	/**
	 * Filter.
	 *
	 * @param filter the filter
	 * @return the subscriber activity
	 */
	public SubscriberActivity filter(String filter)
	{
		this.addQueryParameter("filter", filter);
		return this;
	}
	
	/**
	 * Search.
	 *
	 * @param search the search
	 * @return the subscriber activity
	 */
	public SubscriberActivity search(String search)
	{
		this.addQueryParameter("search", search);
		return this;
	}
	
	/**
	 * Sort.
	 *
	 * @param sort the sort
	 * @return the subscriber activity
	 */
	public SubscriberActivity sort(String sort)
	{
		this.addQueryParameter("sort", sort);
		return this;
	}
	
	
	/**
	 * Gets the.
	 *
	 * @param campaignId the campaign id
	 * @return the campaign subscriber activity list
	 * @throws MailerLiteException the mailer lite exception
	 */
	public CampaignSubscriberActivityList get(String campaignId) throws MailerLiteException
	{
		String endpoint = "/campaigns/".concat(campaignId).concat("/reports/subscriber-activity").concat(this.getQueryParameters());
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		CampaignSubscriberActivityList activity = api.getRequest(endpoint, CampaignSubscriberActivityList.class);
		
		return activity;
	}

	/**
	 * Gets the single instance of SubscriberActivity.
	 *
	 * @return single instance of SubscriberActivity
	 */
	@Override
	protected SubscriberActivity getInstance() {
		return this;
	}
}
