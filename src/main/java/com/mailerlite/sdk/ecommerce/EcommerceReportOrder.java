/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.ecommerce;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

/**
 * The Class EcommerceReportOrder.
 */
public class EcommerceReportOrder {

	@SerializedName("subscriber")
	public JsonElement subscriber;

	@SerializedName("total")
	public Double total;

	@SerializedName("order")
	public String order;

	@SerializedName("email")
	public JsonElement email;

	@SerializedName("updated_at")
	public String updatedAt;
}
