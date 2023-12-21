/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.triggers;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.fields.Field;
import com.mailerlite.sdk.groups.Group;

/**
 * The Class TriggerDate.
 */
public class TriggerDate extends AutomationTrigger {

	/** The group ids. */
	@SerializedName("group_ids")
	public String[] groupIds;
	
	/** The groups. */
	@SerializedName("groups")
	public Group[] groups;
	
	/** The exclude group ids. */
	@SerializedName("exclude_group_ids")
	public String[] excludeGroupIds;
	
	/** The excluded groups. */
	@SerializedName("excluded_groups")
	public Group[] excludedGroups;
	
	/** The field id. */
	@SerializedName("field_id")
	public String fieldId;
	
	/** The field. */
	@SerializedName("field")
	public Field field;
	
	/** The start before time. */
	@SerializedName("start_before_time")
	public String startBeforeTime;
	
	/** The start before time unit. */
	@SerializedName("start_before_time_unit")
	public String startBeforeTimeUnit;
	
	/** The start after time. */
	@SerializedName("start_after_time")
	public String startAfterTime;
	
	/** The start after time unit. */
	@SerializedName("start_after_time_unit")
	public String startAfterTimeUnit;
	
	/** The send hour. */
	@SerializedName("send_hour")
	public String sendHour;
	
	/** The send minute. */
	@SerializedName("send_minute")
	public String sendMinute;
}
