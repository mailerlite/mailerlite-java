/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.susbcribers;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.annotations.SerializedName;

/**
 * The Class SubscriberBuilderBody.
 */
public class SubscriberBuilderBody {

	/** The email. */
	@SerializedName("email")
	public String email;
	
	/** The fields. */
	@SerializedName("fields")
	public HashMap<String, Object> fields;
	
	/** The groups. */
	@SerializedName("groups")
	public ArrayList<String> groups;
	
	/** The status. */
	@SerializedName("status")
	public String status;
	
	/** The subscribed at. */
	@SerializedName("subscribed_at")
	public String subscribedAt;
	
	/** The ip address. */
	@SerializedName("ip_address")
	public String ipAddress;
	
	/** The opted in at. */
	@SerializedName("opted_in_at")
	public String optedInAt;
	
	/** The optin ip. */
	@SerializedName("optin_ip")
	public String optinIp;
	
	/** The unsubscribed at. */
	@SerializedName("unsubscribed_at")
	public String unsubscribedAt;
}
