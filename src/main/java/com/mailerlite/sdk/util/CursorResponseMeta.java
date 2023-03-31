package com.mailerlite.sdk.util;

import com.google.gson.annotations.SerializedName;

public class CursorResponseMeta {

	
	@SerializedName("path")
	public String path;
	
	@SerializedName("next_page")
	public int nextPage;
	
	@SerializedName("next_cursor")
	public String nextCursor;
	
	@SerializedName("prev_cursor")
	public String previousCursor;
}
