/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.reports;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.automations.FloatStringValue;
import com.mailerlite.sdk.automations.steps.AbStepStats;

/**
 * The Class AbTestVariantStats.
 */
public class AbTestVariantStats extends AbStepStats {

	/** The open rate. */
	@SerializedName("open_rate")
	public FloatStringValue openRate;
	
	/** The click rate. */
	@SerializedName("click_rate")
	public FloatStringValue clickRate;
	
	/** The click to open rate. */
	@SerializedName("click_to_open_rate")
	public FloatStringValue clickToOpenRate;
}
