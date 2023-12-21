/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.susbcribers;

import java.util.Date;
import java.util.HashMap;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.ApiResource;
import com.mailerlite.sdk.util.ParseDate;

/**
 * The Class Subscriber.
 */
public class Subscriber extends ApiResource {

	/** The id. */
	@SerializedName("id")
	public String id;
	
	/** The email. */
	@SerializedName("email")
	public String email;
	
	/** The status. */
	@SerializedName("status")
	public String status;
	
	/** The source. */
	@SerializedName("source")
	public String source;
	
	/** The sent. */
	@SerializedName("sent")
	public int sent;
	
	/** The opens count. */
	@SerializedName("opens_count")
	public int opensCount;
	
	/** The clicks count. */
	@SerializedName("clicks_count")
	public int clicksCount;
	
	/** The open rate. */
	@SerializedName("open_rate")
	public float openRate;
	
	/** The click rate. */
	@SerializedName("click_rate")
	public float clickRate;
	
	/** The ip address. */
	@SerializedName("ip_address")
	public String ipAddress;
	
	/** The subscribed at str. */
	@SerializedName("subscribed_at")
	public String subscribedAtStr;
	
	/** The subscribed at. */
	@ParseDate("subscribedAtStr")
	public Date subscribedAt;
	
	/** The unsubscribed at str. */
	@SerializedName("unsubscribed_at")
	public String unsubscribedAtStr;
	
	/** The unsubscribed at. */
	@ParseDate("unsubscribedAtStr")
	public Date unsubscribedAt;
	
	/** The created at str. */
	@SerializedName("created_at")
	public String createdAtStr;
	
	/** The created at. */
	@ParseDate("createdAtStr")
	public Date createdAt;
	
	/** The updated at str. */
	@SerializedName("updated_at")
	public String updatedAtStr;
	
	/** The updated at. */
	@ParseDate("updatedAtStr")
	public Date updatedAt;
	
	/** The opted in at str. */
	@SerializedName("opted_in_at")
	public String optedInAtStr;
	
	/** The opted in at. */
	@ParseDate("optedInAtStr")
	public Date optedInAt;
	
	/** The optin ip. */
	@SerializedName("optin_ip")
	public String optinIp;
	
	/** The fields. */
	@SerializedName("fields")
	public HashMap<String, Object> fields = new HashMap<String, Object>();
}
