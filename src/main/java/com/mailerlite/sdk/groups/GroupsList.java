package com.mailerlite.sdk.groups;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.PaginatedResponse;

public class GroupsList extends PaginatedResponse {

	@SerializedName("data")
	public Group[] groups;
	
	public void postDeserialise()
	{
		for (Group g : groups) {
			g.parseDates();
		}
	}
}
