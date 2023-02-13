package com.mailerlite.sdk.campaigns;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.emails.EmailBase;

public class CampaignBuilderBody {

	@SerializedName("name")
	public String name;
	
	@SerializedName("language_id")
	public int languageId = 4;
	
	@SerializedName("type")
	public String type;
	
	@SerializedName("emails")
	public ArrayList<EmailBase> emails = new ArrayList<EmailBase>();
	
	@SerializedName("groups")
	public ArrayList<String> groupIds = new ArrayList<String>();
	
	@SerializedName("segments")
	public ArrayList<String> segmentIds = new ArrayList<String>();
	
	@SerializedName("ab_settings")
	public HashMap<String, Object> abSettings;
	
	@SerializedName("resend_settings")
	public HashMap<String, Object> resendSettings;
}
