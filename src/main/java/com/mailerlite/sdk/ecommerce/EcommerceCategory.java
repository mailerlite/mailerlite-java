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
 * The Class EcommerceCategory.
 */
public class EcommerceCategory {

	/** The id. */
	@SerializedName("id")
	public String id;
	
	/** The name. */
	@SerializedName("name")
	public String name;

	@SerializedName("resource_id")
	public String resourceId;

	@SerializedName("exclude_from_automations")
	public Boolean excludeFromAutomations;

	@SerializedName("created_at")
	public String createdAt;

	@SerializedName("updated_at")
	public String updatedAt;
}
