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
 * The Class OrderBuilderBody.
 */
public class OrderBuilderBody {

	@SerializedName("customer")
	public CustomerBuilderBody customer;

	@SerializedName("cart")
	public CartBuilderBody cart;

	@SerializedName("status")
	public String status;

	@SerializedName("stage")
	public String stage;

	@SerializedName("total_price")
	public Double totalPrice;

	@SerializedName("resource_id")
	public String resourceId;

	@SerializedName("created_at")
	public String createdAt;

	@SerializedName("without_automations")
	public Boolean withoutAutomations;
}
