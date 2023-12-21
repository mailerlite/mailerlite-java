/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.campaigns;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.emails.EmailBase;

/**
 * The Class CampaignBuilderBody.
 */
public class CampaignBuilderBody {

	/** The name. */
	@SerializedName("name")
	public String name;
	
	/** The language id. */
	@SerializedName("language_id")
	public int languageId = 4;
	
	/** The type. */
	@SerializedName("type")
	public String type;
	
	/** The emails. */
	@SerializedName("emails")
	public ArrayList<EmailBase> emails = new ArrayList<EmailBase>();
	
	/** The group ids. */
	@SerializedName("groups")
	public ArrayList<String> groupIds = new ArrayList<String>();
	
	/** The segment ids. */
	@SerializedName("segments")
	public ArrayList<String> segmentIds = new ArrayList<String>();
	
	/** The ab settings. */
	@SerializedName("ab_settings")
	public HashMap<String, Object> abSettings;
	
	/** The resend settings. */
	@SerializedName("resend_settings")
	public HashMap<String, Object> resendSettings;
}
