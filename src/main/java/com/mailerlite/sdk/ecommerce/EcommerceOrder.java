/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.ecommerce;

import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.ApiResource;
import com.mailerlite.sdk.util.ParseDate;

/**
 * The Class EcommerceOrder.
 */
public class EcommerceOrder extends ApiResource {

	@SerializedName("id")
	public String id;

	@SerializedName("resource_id")
	public String resourceId;

	@SerializedName("customer")
	public EcommerceCustomer customer;

	@SerializedName("cart")
	public EcommerceCart cart;

	@SerializedName("total")
	public Double total;

	@SerializedName("status")
	public String status;

	@SerializedName("stage")
	public String stage;

	@SerializedName("created_at")
	public String createdAtStr;

	@ParseDate("createdAtStr")
	public Date createdAt;

	@SerializedName("updated_at")
	public String updatedAtStr;

	@ParseDate("updatedAtStr")
	public Date updatedAt;

	@SerializedName("completed_at")
	public String completedAtStr;

	@ParseDate("completedAtStr")
	public Date completedAt;

	@SerializedName("attribution")
	public EcommerceOrderAttribution attribution;

	@Override
	public void parseDates()
	{
		super.parseDates();

		if (customer != null) {
			customer.parseDates();
		}

		if (cart != null) {
			cart.parseDates();
		}
	}
}
