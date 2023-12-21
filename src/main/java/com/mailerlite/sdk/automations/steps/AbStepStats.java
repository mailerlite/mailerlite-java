/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.steps;

import com.google.gson.annotations.SerializedName;

/**
 * The Class AbStepStats.
 */
public class AbStepStats {

	/** The subscribers. */
	@SerializedName("subscribers")
	public int subscribers;
	
	/** The sent. */
	@SerializedName("sent")
	public int sent;
	
	/** The opens. */
	@SerializedName("opens")
	public int opens;
	
	/** The clicks. */
	@SerializedName("clicks")
	public int clicks;
}
