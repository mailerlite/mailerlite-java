/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.groups;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.PaginatedResponse;

/**
 * The Class GroupsList.
 */
public class GroupsList extends PaginatedResponse {

	/** The groups. */
	@SerializedName("data")
	public Group[] groups;
	
	/**
	 * Post deserialise.
	 */
	public void postDeserialise()
	{
		for (Group g : groups) {
			g.parseDates();
		}
	}
}
