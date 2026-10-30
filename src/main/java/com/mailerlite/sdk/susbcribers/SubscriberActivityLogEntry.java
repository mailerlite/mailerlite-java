/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.susbcribers;

import java.util.Date;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.ApiResource;
import com.mailerlite.sdk.util.ParseDate;

/**
 * The Class SubscriberActivityLogEntry.
 */
public class SubscriberActivityLogEntry extends ApiResource {

	/** The id. */
	@SerializedName("id")
	public String id;
	
	/** The log name. */
	@SerializedName("log_name")
	public String logName;
	
	/** The subject id. */
	@SerializedName("subject_id")
	public String subjectId;
	
	/** The subject type. */
	@SerializedName("subject_type")
	public String subjectType;
	
	/** The properties. */
	@SerializedName("properties")
	public JsonElement properties;
	
	/** The created at. */
	@SerializedName("created_at")
	public String createdAtStr;
	
	/** The created at. */
	@ParseDate("createdAtStr")
	public Date createdAt;
	
	/** The updated at. */
	@SerializedName("updated_at")
	public String updatedAtStr;
	
	/** The updated at. */
	@ParseDate("updatedAtStr")
	public Date updatedAt;
}
