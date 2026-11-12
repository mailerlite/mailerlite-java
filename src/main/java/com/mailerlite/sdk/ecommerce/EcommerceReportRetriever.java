/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.ecommerce;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.automations.reports.OrderActivityList;
import com.mailerlite.sdk.exceptions.MailerLiteException;

/**
 * The Class EcommerceReportRetriever.
 */
public class EcommerceReportRetriever extends EcommerceRequest<EcommerceReportRetriever> {

	private MailerLite apiObjectReference;

	private String statsEndpoint;

	private String ordersEndpoint;

	public EcommerceReportRetriever(MailerLite apiRef, String statsEndpoint, String ordersEndpoint)
	{
		apiObjectReference = apiRef;
		this.statsEndpoint = statsEndpoint;
		this.ordersEndpoint = ordersEndpoint;
	}

	public EcommerceReportRetriever shopId(String shopId)
	{
		this.addQueryParameter("shop_id", shopId);
		return this;
	}

	public EcommerceReportRetriever startDate(String startDate)
	{
		this.addQueryParameter("start_date", startDate);
		return this;
	}

	public EcommerceReportRetriever endDate(String endDate)
	{
		this.addQueryParameter("end_date", endDate);
		return this;
	}

	public EcommerceReportRetriever timeFrame(String timeFrame)
	{
		this.addQueryParameter("time_frame", timeFrame);
		return this;
	}

	public EcommerceReportRetriever timeFrameStart(String timeFrameStart)
	{
		this.addQueryParameter("time_frame_start", timeFrameStart);
		return this;
	}

	public EcommerceReportRetriever timeFrameEnd(String timeFrameEnd)
	{
		this.addQueryParameter("time_frame_end", timeFrameEnd);
		return this;
	}

	public SingleEcommerceReport stats() throws MailerLiteException
	{
		return EcommerceApi.create(apiObjectReference).getRequest(statsEndpoint.concat(this.getQueryParameters()), SingleEcommerceReport.class);
	}

	public OrderActivityList orders() throws MailerLiteException
	{
		if (ordersEndpoint == null) {
			throw new MailerLiteException("This report has no orders list. Use stats() instead.");
		}

		return EcommerceApi.create(apiObjectReference).getRequest(ordersEndpoint.concat(this.getQueryParameters()), OrderActivityList.class);
	}

	@Override
	protected EcommerceReportRetriever getInstance() {
		return this;
	}
}
