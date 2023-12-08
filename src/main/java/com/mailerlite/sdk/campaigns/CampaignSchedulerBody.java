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

public class CampaignSchedulerBody {

	@SerializedName("delivery")
	public String delivery;
	
	@SerializedName("schedule")
	public HashMap<String, Object> schedule;
	
	@SerializedName("resend")
	public HashMap<String, Object> resend;
	
	
}
