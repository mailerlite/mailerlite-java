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
 * The Class CampaignRetriever.
 */
public class CampaignRetriever extends PaginatedRequest<CampaignRetriever> {

	/** The api object reference. */
	private MailerLite apiObjectReference;
	
	/**
	 * Instantiates a new campaign retriever.
	 *
	 * @param apiRefernce the api refernce
	 */
	public CampaignRetriever(MailerLite apiRefernce)
	{
		apiObjectReference = apiRefernce;
	}
	
	
	/**
	 * Filter.
	 *
	 * @param name the name
	 * @param value the value
	 * @return the campaign retriever
	 */
	public CampaignRetriever filter(String name, String value)
	{
		this.addQueryParameter("filter[".concat(name).concat("]"), value);
		
		return this;
	}
		
	/**
	 * Gets the.
	 *
	 * @return the campaigns list
	 * @throws MailerLiteException the mailer lite exception
	 */
	public CampaignsList get() throws MailerLiteException
	{
		String endpoint = "/campaigns/".concat(this.getQueryParameters());
	
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		CampaignsList campaigns = api.getRequest(endpoint, CampaignsList.class);
		
		campaigns.postDeserialize();
		
		return campaigns;
	}
	
	/**
	 * Gets the single.
	 *
	 * @param id the id
	 * @return the single
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SingleCampaign getSingle(String id) throws MailerLiteException
	{
		String endpoint = "/campaigns/".concat(id);
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		SingleCampaign campaign = api.getRequest(endpoint, SingleCampaign.class);
		
		campaign.campaign.parseDates();
		
		return campaign;
	}


	/**
	 * Gets the single instance of CampaignRetriever.
	 *
	 * @return single instance of CampaignRetriever
	 */
	@Override
	protected CampaignRetriever getInstance() {
		
		return this;
	}
}
