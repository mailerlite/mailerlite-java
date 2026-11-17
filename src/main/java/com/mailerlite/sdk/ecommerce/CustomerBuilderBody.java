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
 * The Class CustomerBuilderBody.
 */
public class CustomerBuilderBody {

	@SerializedName("email")
	public String email;

	@SerializedName("name")
	public String name;

	@SerializedName("last_name")
	public String lastName;

	@SerializedName("accepts_marketing")
	public Boolean acceptsMarketing;

	@SerializedName("total_spent")
	public Double totalSpent;

	@SerializedName("orders_count")
	public Integer ordersCount;

	@SerializedName("create_subscriber")
	public Boolean createSubscriber;

	@SerializedName("resource_id")
	public String resourceId;
}
