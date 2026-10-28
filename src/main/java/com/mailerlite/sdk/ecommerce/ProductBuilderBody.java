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
 * The Class ProductBuilderBody.
 */
public class ProductBuilderBody {

	@SerializedName("name")
	public String name;

	@SerializedName("resource_id")
	public String resourceId;

	@SerializedName("price")
	public Double price;

	@SerializedName("url")
	public String url;

	@SerializedName("image")
	public String image;

	@SerializedName("short_description")
	public String shortDescription;

	@SerializedName("description")
	public String description;

	@SerializedName("exclude_from_automations")
	public Boolean excludeFromAutomations;

	@SerializedName("enabled")
	public Boolean enabled;

	@SerializedName("type")
	public String type;

	@SerializedName("categories")
	public ArrayList<String> categories;

	@SerializedName("variants")
	public ArrayList<ProductVariantBuilderBody> variants;
}
