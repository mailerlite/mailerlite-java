/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.reports;

import com.google.gson.annotations.SerializedName;

/**
 * The Class UnsubscribeReason.
 */
public class UnsubscribeReason {

	/** The label. */
	@SerializedName("label")
	public String label;
	
	/** The count. */
	@SerializedName("count")
	public int count;
	
	/** The percentage. */
	@SerializedName("percentage")
	public String percentage;
}
