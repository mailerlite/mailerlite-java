/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.campaigns;

import java.util.HashMap;

import com.google.gson.annotations.SerializedName;

/**
 * The Class CampaignSchedulerBody.
 */
public class CampaignSchedulerBody {

	/** The delivery. */
	@SerializedName("delivery")
	public String delivery;
	
	/** The schedule. */
	@SerializedName("schedule")
	public HashMap<String, Object> schedule;
	
	/** The resend. */
	@SerializedName("resend")
	public HashMap<String, Object> resend;
	
	
}
