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

public class TriggerDate extends AutomationTrigger {

	@SerializedName("group_ids")
	public String[] groupIds;
	
	@SerializedName("groups")
	public Group[] groups;
	
	@SerializedName("exclude_group_ids")
	public String[] excludeGroupIds;
	
	@SerializedName("excluded_groups")
	public Group[] excludedGroups;
	
	@SerializedName("field_id")
	public String fieldId;
	
	@SerializedName("field")
	public Field field;
	
	@SerializedName("start_before_time")
	public String startBeforeTime;
	
	@SerializedName("start_before_time_unit")
	public String startBeforeTimeUnit;
	
	@SerializedName("start_after_time")
	public String startAfterTime;
	
	@SerializedName("start_after_time_unit")
	public String startAfterTimeUnit;
	
	@SerializedName("send_hour")
	public String sendHour;
	
	@SerializedName("send_minute")
	public String sendMinute;
}
