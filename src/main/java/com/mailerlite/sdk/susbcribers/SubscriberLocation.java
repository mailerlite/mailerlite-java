/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.susbcribers;

import com.google.gson.annotations.SerializedName;

/**
 * The Class SubscriberLocation.
 */
public class SubscriberLocation {

	/** The id. */
	@SerializedName("id")
	public String id;
	
	/** The title. */
	@SerializedName("title")
	public String title;
	
	/** The country name. */
	@SerializedName("country_name")
	public String countryName;
	
	/** The continent name. */
	@SerializedName("continent_name")
	public String continentName;
	
	/** The state name. */
	@SerializedName("state_name")
	public String stateName;
	
	/** The time zone. */
	@SerializedName("time_zone")
	public String timeZone;
}
