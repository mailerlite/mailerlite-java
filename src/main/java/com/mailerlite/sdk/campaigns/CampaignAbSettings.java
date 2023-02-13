package com.mailerlite.sdk.campaigns;

import java.util.HashMap;


public class CampaignAbSettings {

	private HashMap<String, Object> settings = new HashMap<String, Object>();
	
	public CampaignAbSettings testType(String testType)
	{
		
		this.settings.put("test_type", testType);
		return this;
	}
	
	public CampaignAbSettings selectWinnerBy(String selectWinnerBy)
	{
		this.settings.put("select_winner_by", selectWinnerBy);
		return this;
	}
	
	public CampaignAbSettings afterTimeAmount(int afterTimeAmount)
	{
		this.settings.put("after_time_amount", afterTimeAmount);
		return this;
	}
	
	public CampaignAbSettings afterTimeUnit(String afterTimeUnit)
	{
		this.settings.put("after_time_unit", afterTimeUnit);
		return this;
	}
	
	public CampaignAbSettings testSplit(int testSplit)
	{
		this.settings.put("test_split", testSplit);
		return this;
	}
	
	public CampaignAbSettings bValueSubject(String bValueSubject)
	{

		this.getBvalue().put("subject", bValueSubject);
		return this;
	}
	
	public CampaignAbSettings bValueFromName(String bValueFromName)
	{
		this.getBvalue().put("from_name", bValueFromName);
		return this;
	}
	
	public CampaignAbSettings bValueFrom(String bValueFrom)
	{
		this.getBvalue().put("from", bValueFrom);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	private HashMap<String, Object> getBvalue()
	{
		if (!this.settings.containsKey("b_value")) {
			this.settings.put("b_value", new HashMap<String, Object>());
		}
		
		return (HashMap<String, Object>)this.settings.get("b_value");
	}
	
	public HashMap<String, Object> getSettings()
	{
		return this.settings;
	}
	
	public void clear()
	{
		this.settings.clear();
	}
}
