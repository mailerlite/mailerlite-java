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
import com.mailerlite.sdk.groups.Group;
import com.mailerlite.sdk.util.ApiResource;
import com.mailerlite.sdk.util.ParseDate;

/**
 * The Class EcommerceShop.
 */
public class EcommerceShop extends ApiResource {

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
	public String createdAtStr;

	@ParseDate("createdAtStr")
	public Date createdAt;

	@SerializedName("updated_at")
	public String updatedAtStr;

	@ParseDate("updatedAtStr")
	public Date updatedAt;

	@Override
	public void parseDates()
	{
		super.parseDates();

		if (group != null) {
			group.parseDates();
		}
	}
}
