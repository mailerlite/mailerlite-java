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
 * The Class CampaignScheduleSettings.
 */
public class CampaignScheduleSettings {

	/** The settings. */
	private HashMap<String, Object> settings = new HashMap<String, Object>();
	
	/**
	 * Delivery.
	 *
	 * @param delivery the delivery
	 * @return the campaign schedule settings
	 */
	public CampaignScheduleSettings delivery(String delivery)
	{
		this.settings.put("delivery", delivery);
		return this;
	}
	
	/**
	 * Date.
	 *
	 * @param date the date
	 * @return the campaign schedule settings
	 */
	public CampaignScheduleSettings date(String date)
	{
		this.settings.put("date", date);
		return this;
	}
	
	/**
	 * Hours.
	 *
	 * @param hours the hours
	 * @return the campaign schedule settings
	 */
	public CampaignScheduleSettings hours(String hours)
	{
		this.settings.put("hours", hours);
		return this;
	}
	
	/**
	 * Minutes.
	 *
	 * @param minutes the minutes
	 * @return the campaign schedule settings
	 */
	public CampaignScheduleSettings minutes(String minutes)
	{
		this.settings.put("minutes", minutes);
		return this;
	}
	
	/**
	 * Timezone id.
	 *
	 * @param timezoneId the timezone id
	 * @return the campaign schedule settings
	 */
	public CampaignScheduleSettings timezoneId(int timezoneId)
	{
		this.settings.put("timezone_id", timezoneId);
		return this;
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
}
