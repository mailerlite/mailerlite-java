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
 * The Class EcommerceProductVariant.
 */
public class EcommerceProductVariant {

	@SerializedName("id")
	public String id;

	@SerializedName("resource_id")
	public String resourceId;

	@SerializedName("name")
	public String name;

	@SerializedName("attributes")
	public JsonElement attributes;

	@SerializedName("price")
	public Double price;

	@SerializedName("price_type")
	public String priceType;

	@SerializedName("image_url")
	public String imageUrl;
}
