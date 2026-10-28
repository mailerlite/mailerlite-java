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
 * The Class EcommerceCart.
 */
public class EcommerceCart {

	@SerializedName("id")
	public String id;

	@SerializedName("resource_id")
	public String resourceId;

	@SerializedName("checkout_url")
	public String checkoutUrl;

	@SerializedName("cart_total")
	public Double cartTotal;

	@SerializedName("items")
	public EcommerceCartItem[] items;
}
