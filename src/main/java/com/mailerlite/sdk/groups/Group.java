/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.groups;

import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.ApiResource;
import com.mailerlite.sdk.util.ParseDate;
import com.mailerlite.sdk.util.RateValue;

/**
 * The Class Group.
 */
public class Group extends ApiResource {

	/** The id. */
	@SerializedName("id")
	public String id;
	
	/** The name. */
	@SerializedName("name")
	public String name;
	
	/** The active count. */
	@SerializedName("active_count")
	public int activeCount;
	
	/** The sent count. */
	@SerializedName("sent_count")
	public int sentCount;
	
	/** The opens count. */
	@SerializedName("opens_count")
	public int opensCount;
	
	/** The open rate. */
	@SerializedName("open_rate")
	public RateValue openRate;
	
	/** The clicks count. */
	@SerializedName("clicks_count")
	public int clicksCount;
	
	/** The click rate. */
	@SerializedName("click_rate")
	public RateValue clickRate;
	
	/** The unsubscribed count. */
	@SerializedName("unsubscribed_count")
	public int unsubscribedCount;
	
	/** The unconfirmed count. */
	@SerializedName("unconfirmed_count")
	public int unconfirmedCount;
	
	/** The bounced count. */
	@SerializedName("bounced_count")
	public int bouncedCount;
	
	/** The junk count. */
	@SerializedName("junk_count")
	public int junkCount;
	
	/** The created at str. */
	@SerializedName("created_at")
	public String createdAtStr;
	
	/** The created at. */
	@ParseDate("createdAtStr")
	public Date createdAt;
}
