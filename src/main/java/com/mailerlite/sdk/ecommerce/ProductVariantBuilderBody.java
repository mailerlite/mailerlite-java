/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.ecommerce;

import com.google.gson.annotations.SerializedName;
import java.util.HashMap;

/**
 * The Class ProductVariantBuilderBody.
 */
public class ProductVariantBuilderBody {

	@SerializedName("name")
	public String name;

	@SerializedName("attributes")
	public HashMap<String, String> attributes = new HashMap<String, String>();

	@SerializedName("price_type")
	public String priceType;

	@SerializedName("price")
	public Double price;

	@SerializedName("resource_id")
	public String resourceId;

	@SerializedName("image_url")
	public String imageUrl;
}
