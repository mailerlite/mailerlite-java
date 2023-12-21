/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.steps;

import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.ApiResource;
import com.mailerlite.sdk.util.ParseDate;

/**
 * The Class AutomationStep.
 */
public abstract class AutomationStep extends ApiResource {

	/** The id. */
	@SerializedName("id")
	public String id;
	
	/** The type. */
	@SerializedName("type")
	public String type;
	
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
	
	/** The complete. */
	@SerializedName("complete")
	public Boolean complete;
	
	/** The parent id. */
	@SerializedName("parent_id")
	public String parentId;
	
	/** The broken. */
	@SerializedName("broken")
	public Boolean broken;
}
