/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.util;

import com.google.gson.annotations.SerializedName;

/**
 * The Class CursorResponseMeta.
 */
public class CursorResponseMeta {

	
	/** The path. */
	@SerializedName("path")
	public String path;
	
	/** The next page. */
	@SerializedName("next_page")
	public int nextPage;
	
	/** The next cursor. */
	@SerializedName("next_cursor")
	public String nextCursor;
	
	/** The previous cursor. */
	@SerializedName("prev_cursor")
	public String previousCursor;
}
