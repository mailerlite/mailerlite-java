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
 * The Class CampaignResendSettings.
 */
public class CampaignResendSettings {

	/** The settings. */
	private HashMap<String, Object> settings = new HashMap<String, Object>();
	
	/**
	 * Test type.
	 *
	 * @param testType the test type
	 * @return the campaign resend settings
	 */
	public CampaignResendSettings testType(String testType)
	{
		this.settings.put("test_type", testType);
		return this;
	}
	
	/**
	 * Select winner by.
	 *
	 * @param selectWinnerBy the select winner by
	 * @return the campaign resend settings
	 */
	public CampaignResendSettings selectWinnerBy(String selectWinnerBy)
	{
		this.settings.put("select_winner_by", selectWinnerBy);
		return this;
	}
	
	/**
	 * B value subject.
	 *
	 * @param subject the subject
	 * @return the campaign resend settings
	 */
	public CampaignResendSettings bValueSubject(String subject)
	{
		this.getBvalue().put("subject", subject);
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
