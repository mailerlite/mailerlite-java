/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.campaigns;

import java.util.HashMap;

public class CampaignResendSettings {

	private HashMap<String, Object> settings = new HashMap<String, Object>();
	
	public CampaignResendSettings testType(String testType)
	{
		this.settings.put("test_type", testType);
		return this;
	}
	
	public CampaignResendSettings selectWinnerBy(String selectWinnerBy)
	{
		this.settings.put("select_winner_by", selectWinnerBy);
		return this;
	}
	
	public CampaignResendSettings bValueSubject(String subject)
	{
		this.getBvalue().put("subject", subject);
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
