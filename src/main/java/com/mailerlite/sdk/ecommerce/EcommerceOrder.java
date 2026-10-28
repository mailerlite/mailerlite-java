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
 * The Class EcommerceOrder.
 */
public class EcommerceOrder {

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
	public String createdAt;

	@SerializedName("updated_at")
	public String updatedAt;

	@SerializedName("completed_at")
	public String completedAt;

	@SerializedName("attribution")
	public EcommerceOrderAttribution attribution;
}
