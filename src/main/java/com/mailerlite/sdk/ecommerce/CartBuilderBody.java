/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.ecommerce;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/**
 * The Class CartBuilderBody.
 */
public class CartBuilderBody {

	@SerializedName("checkout_url")
	public String checkoutUrl;

	@SerializedName("cart_total")
	public Double cartTotal;

	@SerializedName("ecommerce_customer_id")
	public String customerId;

	@SerializedName("resource_id")
	public String resourceId;

	@SerializedName("items")
	public ArrayList<CartItemBuilderBody> items;
}
