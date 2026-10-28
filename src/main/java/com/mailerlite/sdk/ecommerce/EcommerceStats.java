/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.ecommerce;

import com.google.gson.annotations.SerializedName;

/**
 * The Class EcommerceStats.
 */
public class EcommerceStats {

	@SerializedName("orders_count")
	public Integer ordersCount;

	@SerializedName("revenue")
	public Double revenue;

	@SerializedName("average_order_value")
	public Double averageOrderValue;

	@SerializedName("conversion_rate")
	public Double conversionRate;
}
