/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.ecommerce;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.groups.Group;

/**
 * The Class EcommerceShop.
 */
public class EcommerceShop {

	/** The id. */
	@SerializedName("id")
	public String id;
	
	/** The name. */
	@SerializedName("name")
	public String name;
	
	/** The url. */
	@SerializedName("url")
	public String url;

	@SerializedName("currency")
	public String currency;

	@SerializedName("currency_sign")
	public String currencySign;

	@SerializedName("platform")
	public String platform;

	@SerializedName("enabled")
	public Boolean enabled;

	@SerializedName("enable_popups")
	public Boolean enablePopups;

	@SerializedName("enable_resubscribe")
	public Boolean enableResubscribe;

	@SerializedName("group")
	public Group group;

	@SerializedName("created_at")
	public String createdAt;

	@SerializedName("updated_at")
	public String updatedAt;
}
