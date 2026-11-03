/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.files;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.PaginatedResponse;

/**
 * The response of MediaFileRetriever.get.
 */
public class MediaFilesList extends PaginatedResponse {

	/** The files. */
	@SerializedName("data")
	public MediaFile[] files;
}
