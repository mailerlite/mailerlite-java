/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.reports;

import com.google.gson.annotations.SerializedName;

/**
 * The Class EcommerceStats.
 */
public class EcommerceStats {

	/** The orders count. */
	@SerializedName("orders_count")
	public int ordersCount;
	
	/** The revenue. */
	@SerializedName("revenue")
	public double revenue;
	
	/** The average order value. */
	@SerializedName("average_order_value")
	public String averageOrderValue;
	
	/** The conversion rate. */
	@SerializedName("conversion_rate")
	public String conversionRate;
}
