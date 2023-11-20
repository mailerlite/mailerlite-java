package com.mailerlite.sdk.automations.steps;

import java.time.LocalDateTime;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.ParseDate;

public abstract class AutomationStep {

	@SerializedName("id")
	public String id;
	
	@SerializedName("type")
	public String type;
	
	@SerializedName("created_at")
	public String createdAtStr;
	
	@ParseDate("createdAtStr")
	public LocalDateTime createdAt;
	
	
	@SerializedName("updated_at")
	public String updatedAtStr;
	
	@ParseDate("updatedAtStr")
	public LocalDateTime updatedAt;
	
	@SerializedName("complete")
	public Boolean complete;
	
	@SerializedName("parent_id")
	public String parentId;
	
	@SerializedName("broken")
	public Boolean broken;
}
