/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.reports;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.util.CursorPaginatedRequest;

/**
 * Lists the orders attributed to an automation.
 */
public class AutomationOrderActivityRetriever extends CursorPaginatedRequest<AutomationOrderActivityRetriever> {

	/** The api object reference. */
	private MailerLite apiObjectReference;
	
	/** The endpoint. */
	private String endpoint;
	
	/**
	 * Instantiates a new automation order activity retriever.
	 *
	 * @param apiRef the api ref
	 * @param forEndpoint the endpoint
	 */
	public AutomationOrderActivityRetriever(MailerLite apiRef, String forEndpoint)
	{
		apiObjectReference = apiRef;
		endpoint = forEndpoint;
	}
	
	/**
	 * Time frame.
	 *
	 * @param timeFrame for example "month", "year", "all_time" or "custom"
	 * @return the automation order activity retriever
	 */
	public AutomationOrderActivityRetriever timeFrame(String timeFrame)
	{
		this.addQueryParameter("time_frame", timeFrame);
		
		return this;
	}
	
	/**
	 * Start of a custom time frame.
	 *
	 * @param timeFrameStart the time frame start
	 * @return the automation order activity retriever
	 */
	public AutomationOrderActivityRetriever timeFrameStart(String timeFrameStart)
	{
		this.addQueryParameter("time_frame_start", timeFrameStart);
		
		return this;
	}
	
	/**
	 * End of a custom time frame.
	 *
	 * @param timeFrameEnd the time frame end
	 * @return the automation order activity retriever
	 */
	public AutomationOrderActivityRetriever timeFrameEnd(String timeFrameEnd)
	{
		this.addQueryParameter("time_frame_end", timeFrameEnd);
		
		return this;
	}
	
	/**
	 * Returns only orders from this shop.
	 *
	 * @param ecommerceShopId the e-commerce shop id
	 * @return the automation order activity retriever
	 */
	public AutomationOrderActivityRetriever ecommerceShopId(String ecommerceShopId)
	{
		this.addQueryParameter("ecommerce_shop_id", ecommerceShopId);
		
		return this;
	}
	
	/**
	 * Gets the orders.
	 *
	 * @return the order activity list
	 * @throws MailerLiteException the mailer lite exception
	 */
	public OrderActivityList get() throws MailerLiteException
	{
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		return api.getRequest(endpoint.concat(this.getQueryParameters()), OrderActivityList.class);
	}
	
	/**
	 * Gets the single instance of AutomationOrderActivityRetriever.
	 *
	 * @return single instance of AutomationOrderActivityRetriever
	 */
	@Override
	protected AutomationOrderActivityRetriever getInstance() {
		return this;
	}
}
