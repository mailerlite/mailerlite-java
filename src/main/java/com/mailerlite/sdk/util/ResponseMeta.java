/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.util;

import java.util.HashMap;

import com.google.gson.annotations.SerializedName;

/**
 * The Class ResponseMeta.
 */
public class ResponseMeta {

	/** The current page. */
	@SerializedName("current_page")
	public int currentPage;
	
	/** The from. */
	@SerializedName("from")
	public int from;
	
	/** The last page. */
	@SerializedName("last_page")
	public int lastPage;
	
	/** The links. */
	@SerializedName("links")
	public ResponseMetaLink[] links;
	
	/** The path. */
	@SerializedName("path")
	public String path;
	
	/** The per page. */
	@SerializedName("per_page")
	public int perPage;
	
	/** The to. */
	@SerializedName("to")
	public int to;
	
	/** The total. */
	@SerializedName("total")
	public int total;
	
	/** The aggregations. */
	@SerializedName("aggregations")
	public HashMap<String, Object> aggregations = new HashMap<String, Object>();
}
