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
 * The Class CartItemBuilderBody.
 */
public class CartItemBuilderBody {

	@SerializedName("ecommerce_product_id")
	public String productId;

	@SerializedName("product_resource_id")
	public String productResourceId;

	@SerializedName("ecommerce_product_variant_id")
	public String productVariantId;

	@SerializedName("variant")
	public String variant;

	@SerializedName("quantity")
	public Integer quantity;

	@SerializedName("price")
	public Double price;

	@SerializedName("resource_id")
	public String resourceId;
}
