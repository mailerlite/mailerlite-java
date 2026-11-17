/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.ecommerce;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.ApiResource;

/**
 * The Class EcommerceCartItem.
 */
public class EcommerceCartItem extends ApiResource {

	@SerializedName("id")
	public String id;

	@SerializedName("resource_id")
	public String resourceId;

	@SerializedName("product")
	public EcommerceProduct product;

	@SerializedName("ecommerce_product_variant_id")
	public String productVariantId;

	@SerializedName("variant")
	public String variant;

	@SerializedName("quantity")
	public Integer quantity;

	@SerializedName("price")
	public Double price;

	@Override
	public void parseDates()
	{
		if (product != null) {
			product.parseDates();
		}
	}
}
