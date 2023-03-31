package com.mailerlite.sdk.fields;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.PaginatedResponse;

public class FieldList extends PaginatedResponse {

	@SerializedName("data")
	public Field[] fields;
	
	public void postDeserialise()
	{
		for (Field f : fields) {
			f.parseDates();
		}
	}
}
