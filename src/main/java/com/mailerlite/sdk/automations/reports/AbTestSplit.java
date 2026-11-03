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
 * The Class AbTestSplit.
 */
public class AbTestSplit {

	/** The label. */
	@SerializedName("label")
	public String label;
	
	/** The path. */
	@SerializedName("path")
	public String path;
	
	/** The value. */
	@SerializedName("value")
	public int value;
}
