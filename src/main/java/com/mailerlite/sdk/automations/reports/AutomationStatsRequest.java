/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.reports;

import com.mailerlite.sdk.util.ApiRequest;

/**
 * The date range and shop filters shared by the automation stats endpoints.
 *
 * @param <T> the generic type
 */
public abstract class AutomationStatsRequest<T extends AutomationStatsRequest<T>> extends ApiRequest {

	/**
	 * Gets the single instance.
	 *
	 * @return the instance
	 */
	protected abstract T getInstance();
	
	/**
	 * First day to report on. Send together with endDate.
	 *
	 * @param startDate the start date, as YYYY-MM-DD
	 * @return the request
	 */
	public T startDate(String startDate)
	{
		this.addQueryParameter("start_date", startDate);
		
		return getInstance();
	}
	
	/**
	 * Last day to report on. Send together with startDate.
	 *
	 * @param endDate the end date, as YYYY-MM-DD
	 * @return the request
	 */
	public T endDate(String endDate)
	{
		this.addQueryParameter("end_date", endDate);
		
		return getInstance();
	}
	
	/**
	 * Reports only on orders from this shop.
	 *
	 * @param shopId the e-commerce shop id
	 * @return the request
	 */
	public T shopId(String shopId)
	{
		this.addQueryParameter("shop_id", shopId);
		
		return getInstance();
	}
}
