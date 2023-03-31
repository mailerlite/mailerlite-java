package com.mailerlite.sdk.segments;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.PaginatedResponse;

public class SegmentsList extends PaginatedResponse {

	@SerializedName("data")
	public Segment[] segments;
	
	public void postDeserialise()
	{
		for (Segment s : segments) {
			s.parseDates();
		}
	}
}
