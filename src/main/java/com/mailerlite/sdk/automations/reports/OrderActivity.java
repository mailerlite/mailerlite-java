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
import com.mailerlite.sdk.emails.Email;
import com.mailerlite.sdk.util.ApiResource;
import com.mailerlite.sdk.util.ParseDate;

/**
 * The Class OrderActivity.
 */
public class OrderActivity extends ApiResource {

	/** The subscriber. */
	@SerializedName("subscriber")
	public ActivitySubscriber subscriber;
	
	/** The total. */
	@SerializedName("total")
	public double total;
	
	/** The updated at. */
	@SerializedName("updated_at")
	public String updatedAtStr;
	
	/** The updated at. */
	@ParseDate("updatedAtStr")
	public Date updatedAt;
	
	/** The order. */
	@SerializedName("order")
	public String order;
	
	/** The email. */
	@SerializedName("email")
	public Email email;
}
