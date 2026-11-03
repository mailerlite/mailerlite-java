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
 * The Class SubscriberTimezone.
 */
public class SubscriberTimezone {

	/** The id. */
	@SerializedName("id")
	public String id;
	
	/** The name. */
	@SerializedName("name")
	public String name;
	
	/** The name for humans. */
	@SerializedName("name_for_humans")
	public String nameForHumans;
	
	/** The offset name. */
	@SerializedName("offset_name")
	public String offsetName;
	
	/** The offset. */
	@SerializedName("offset")
	public Integer offset;
}
