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
 * The Class ShopBuilderBody.
 */
public class ShopBuilderBody {

	@SerializedName("name")
	public String name;

	@SerializedName("url")
	public String url;

	@SerializedName("currency")
	public String currency;

	@SerializedName("platform")
	public String platform;

	@SerializedName("group_id")
	public String groupId;

	@SerializedName("enable_popups")
	public Boolean enablePopups;

	@SerializedName("enabled")
	public Boolean enabled;

	@SerializedName("access_data")
	public String accessData;
}
