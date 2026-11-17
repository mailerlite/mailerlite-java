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
 * The Class EcommerceSettings.
 */
public class EcommerceSettings {

	@SerializedName("ecommerce_tracking_in_campaigns")
	public Boolean trackingInCampaigns;

	@SerializedName("ecommerce_tracking_in_automations")
	public Boolean trackingInAutomations;

	@SerializedName("ecommerce_attribution_window")
	public Integer attributionWindow;
}
