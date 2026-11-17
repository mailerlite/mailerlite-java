/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.ecommerce;

import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.ApiResource;
import com.mailerlite.sdk.util.ParseDate;

/**
 * The Class EcommerceCategory.
 */
public class EcommerceCategory extends ApiResource {

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
	public String createdAtStr;

	@ParseDate("createdAtStr")
	public Date createdAt;

	@SerializedName("updated_at")
	public String updatedAtStr;

	@ParseDate("updatedAtStr")
	public Date updatedAt;
}
