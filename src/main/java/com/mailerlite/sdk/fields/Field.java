package com.mailerlite.sdk.fields;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.ApiResource;

public class Field extends ApiResource {

	@SerializedName("id")
	public String id;
	
	@SerializedName("name")
	public String name;
	
	@SerializedName("key")
	public String key;
	
	@SerializedName("type")
	public String type;
}
