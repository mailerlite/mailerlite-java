/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.forms;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.PaginatedResponse;

/**
 * The Class FormsList.
 */
public class FormsList extends PaginatedResponse {
	
	/** The forms. */
	@SerializedName("data")
	public Form[] forms;
	
	/**
	 * Post deserialise.
	 */
	public void postDeserialise()
	{
		for (Form f : forms) {
			f.parseDates();
		}
	}
}
