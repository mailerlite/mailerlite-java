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
 * The Class EcommerceReport.
 */
public class EcommerceReport {

	@SerializedName("ecommerce")
	public EcommerceStats ecommerce;

	@SerializedName("best_performing_email")
	public JsonElement bestPerformingEmail;

	@SerializedName("series")
	public JsonElement series;
}
