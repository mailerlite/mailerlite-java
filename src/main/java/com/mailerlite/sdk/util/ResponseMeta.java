package com.mailerlite.sdk.util;

import java.util.HashMap;

import com.google.gson.annotations.SerializedName;

public class ResponseMeta {

	@SerializedName("current_page")
	public int currentPage;
	
	@SerializedName("from")
	public int from;
	
	@SerializedName("last_page")
	public int lastPage;
	
	@SerializedName("links")
	public ResponseMetaLink[] links;
	
	@SerializedName("path")
	public String path;
	
	@SerializedName("per_page")
	public int perPage;
	
	@SerializedName("to")
	public int to;
	
	@SerializedName("total")
	public int total;
	
	@SerializedName("aggregations")
	public HashMap<String, Object> aggregations = new HashMap<String, Object>();
}
