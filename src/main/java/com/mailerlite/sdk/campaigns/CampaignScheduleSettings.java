package com.mailerlite.sdk.campaigns;

import java.util.HashMap;

public class CampaignScheduleSettings {

	private HashMap<String, Object> settings = new HashMap<String, Object>();
	
	public CampaignScheduleSettings delivery(String delivery)
	{
		this.settings.put("delivery", delivery);
		return this;
	}
	
	public CampaignScheduleSettings date(String date)
	{
		this.settings.put("date", date);
		return this;
	}
	
	public CampaignScheduleSettings hours(String hours)
	{
		this.settings.put("hours", hours);
		return this;
	}
	
	public CampaignScheduleSettings minutes(String minutes)
	{
		this.settings.put("minutes", minutes);
		return this;
	}
	
	public CampaignScheduleSettings timezoneId(int timezoneId)
	{
		this.settings.put("timezone_id", timezoneId);
		return this;
	}
	
	public HashMap<String, Object> getSettings()
	{
		return this.settings;
	}
}
