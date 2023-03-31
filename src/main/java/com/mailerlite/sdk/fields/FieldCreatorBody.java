package com.mailerlite.sdk.fields;

import com.google.gson.annotations.SerializedName;

public class FieldCreatorBody {
	
	@SerializedName("name")
	public String name;
	
	@SerializedName("type")
	public String type;
}
