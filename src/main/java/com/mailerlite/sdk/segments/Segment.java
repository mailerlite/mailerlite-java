/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.segments;

import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.ApiResource;
import com.mailerlite.sdk.util.ParseDate;
import com.mailerlite.sdk.util.RateValue;

/**
 * The Class Segment.
 */
public class Segment extends ApiResource {

	/** The id. */
	@SerializedName("id")
	public String id;
	
	/** The name. */
	@SerializedName("name")
	public String name;
	
	/** The total. */
	@SerializedName("total")
	public int total;
	
	/** The open rate. */
	@SerializedName("open_rate")
	public RateValue openRate;
	
	/** The click rate. */
	@SerializedName("click_rate")
	public RateValue clickRate;
	
	/** The created at str. */
	@SerializedName("created_at")
	public String createdAtStr;
	
	/** The created at. */
	@ParseDate("createdAtStr")
	public Date createdAt;
}
