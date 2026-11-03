/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.reports;

import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.automations.activity.ActivitySubscriber;
import com.mailerlite.sdk.util.ApiResource;
import com.mailerlite.sdk.util.ParseDate;

/**
 * The Class RunSubscriber.
 */
public class RunSubscriber extends ApiResource {

	/** The id. */
	@SerializedName("id")
	public String id;
	
	/** The subscriber. */
	@SerializedName("subscriber")
	public ActivitySubscriber subscriber;
	
	/** The date str. */
	@SerializedName("date")
	public String dateStr;
	
	/** The run date. */
	@ParseDate("dateStr")
	public Date runDate;
}
