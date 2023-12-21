/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.util;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.MailerLiteResponse;

/**
 * The Class PaginatedResponse.
 */
public class PaginatedResponse extends MailerLiteResponse {

	/** The links. */
	@SerializedName("links")
	public ResponseLinks links;
	
	/** The meta. */
	@SerializedName("meta")
	public ResponseMeta meta;
}
