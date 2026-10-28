/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.ecommerce;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.susbcribers.Subscriber;

/**
 * The Class EcommerceCustomer.
 */
public class EcommerceCustomer {

	@SerializedName("id")
	public String id;

	@SerializedName("resource_id")
	public String resourceId;

	@SerializedName("email")
	public String email;

	@SerializedName("accepts_marketing")
	public Boolean acceptsMarketing;

	@SerializedName("total_spent")
	public Double totalSpent;

	@SerializedName("orders_count")
	public Integer ordersCount;

	@SerializedName("subscriber")
	public Subscriber subscriber;
}
