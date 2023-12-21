/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.campaigns;

import java.util.HashMap;


/**
 * The Class CampaignAbSettings.
 */
public class CampaignAbSettings {

	/** The settings. */
	private HashMap<String, Object> settings = new HashMap<String, Object>();
	
	/**
	 * Test type.
	 *
	 * @param testType the test type
	 * @return the campaign ab settings
	 */
	public CampaignAbSettings testType(String testType)
	{
		
		this.settings.put("test_type", testType);
		return this;
	}
	
	/**
	 * Select winner by.
	 *
	 * @param selectWinnerBy the select winner by
	 * @return the campaign ab settings
	 */
	public CampaignAbSettings selectWinnerBy(String selectWinnerBy)
	{
		this.settings.put("select_winner_by", selectWinnerBy);
		return this;
	}
	
	/**
	 * After time amount.
	 *
	 * @param afterTimeAmount the after time amount
	 * @return the campaign ab settings
	 */
	public CampaignAbSettings afterTimeAmount(int afterTimeAmount)
	{
		this.settings.put("after_time_amount", afterTimeAmount);
		return this;
	}
	
	/**
	 * After time unit.
	 *
	 * @param afterTimeUnit the after time unit
	 * @return the campaign ab settings
	 */
	public CampaignAbSettings afterTimeUnit(String afterTimeUnit)
	{
		this.settings.put("after_time_unit", afterTimeUnit);
		return this;
	}
	
	/**
	 * Test split.
	 *
	 * @param testSplit the test split
	 * @return the campaign ab settings
	 */
	public CampaignAbSettings testSplit(int testSplit)
	{
		this.settings.put("test_split", testSplit);
		return this;
	}
	
	/**
	 * B value subject.
	 *
	 * @param bValueSubject the b value subject
	 * @return the campaign ab settings
	 */
	public CampaignAbSettings bValueSubject(String bValueSubject)
	{

		this.getBvalue().put("subject", bValueSubject);
		return this;
	}
	
	/**
	 * B value from name.
	 *
	 * @param bValueFromName the b value from name
	 * @return the campaign ab settings
	 */
	public CampaignAbSettings bValueFromName(String bValueFromName)
	{
		this.getBvalue().put("from_name", bValueFromName);
		return this;
	}
	
	/**
	 * B value from.
	 *
	 * @param bValueFrom the b value from
	 * @return the campaign ab settings
	 */
	public CampaignAbSettings bValueFrom(String bValueFrom)
	{
		this.getBvalue().put("from", bValueFrom);
		return this;
	}
	
	/**
	 * Gets the bvalue.
	 *
	 * @return the bvalue
	 */
	@SuppressWarnings("unchecked")
	private HashMap<String, Object> getBvalue()
	{
		if (!this.settings.containsKey("b_value")) {
			this.settings.put("b_value", new HashMap<String, Object>());
		}
		
		return (HashMap<String, Object>)this.settings.get("b_value");
	}
	
	/**
	 * Gets the settings.
	 *
	 * @return the settings
	 */
	public HashMap<String, Object> getSettings()
	{
		return this.settings;
	}
	
	/**
	 * Clear.
	 */
	public void clear()
	{
		this.settings.clear();
	}
}
