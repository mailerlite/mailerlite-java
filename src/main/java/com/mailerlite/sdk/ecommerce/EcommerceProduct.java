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
 * The Class EcommerceProduct.
 */
public class EcommerceProduct {

	/** The id. */
	@SerializedName("id")
	public String id;
	
	/** The name. */
	@SerializedName("name")
	public String name;

	@SerializedName("resource_id")
	public String resourceId;

	@SerializedName("price")
	public Double price;

	@SerializedName("currency")
	public String currency;

	@SerializedName("url")
	public String url;

	@SerializedName("image")
	public String image;

	@SerializedName("short_description")
	public String shortDescription;

	@SerializedName("description")
	public String description;

	@SerializedName("type")
	public String type;

	@SerializedName("enabled")
	public Boolean enabled;

	@SerializedName("exclude_from_automations")
	public Boolean excludeFromAutomations;

	@SerializedName("categories")
	public EcommerceCategory[] categories;

	@SerializedName("variants")
	public EcommerceProductVariant[] variants;

	@SerializedName("created_at")
	public String createdAt;
}
