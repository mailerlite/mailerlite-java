/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.ecommerce;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.MailerLiteResponse;

/**
 * The Class SingleEcommerceReport.
 */
public class SingleEcommerceReport extends MailerLiteResponse {

	@SerializedName("data")
	public EcommerceReport report;
}
