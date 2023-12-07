package com.mailerlite.sdk.forms;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.PaginatedResponse;

public class FormsList extends PaginatedResponse {
	
	@SerializedName("data")
	public Form[] forms;
	
	public void postDeserialise()
	{
		for (Form f : forms) {
			f.parseDates();
		}
	}
}
