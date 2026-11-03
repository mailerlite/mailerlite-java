/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.reports;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.MailerLiteResponse;

/**
 * The Class AbTestStatsList.
 */
public class AbTestStatsList extends MailerLiteResponse {

	/** The ab test stats. */
	@SerializedName("data")
	public AbTestStats[] abTestStats;
}
