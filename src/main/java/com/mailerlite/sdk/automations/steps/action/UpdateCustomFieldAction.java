package com.mailerlite.sdk.automations.steps.action;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.fields.Field;

public class UpdateCustomFieldAction extends ActionStepBase {

	@SerializedName("field_id")
	public String fieldId;
	
	@SerializedName("field")
	public Field field;
	
	@SerializedName("data_operation")
	public String dataOperation;
	
	@SerializedName("value")
	public String value;
}
