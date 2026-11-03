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
 * The Class EcommerceOrderAttribution.
 */
public class EcommerceOrderAttribution {

	@SerializedName("email_id")
	public String emailId;

	@SerializedName("type")
	public String type;
}
