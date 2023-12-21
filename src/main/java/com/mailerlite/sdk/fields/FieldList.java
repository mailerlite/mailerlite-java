/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.fields;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.PaginatedResponse;

/**
 * The Class FieldList.
 */
public class FieldList extends PaginatedResponse {

	/** The fields. */
	@SerializedName("data")
	public Field[] fields;
	
	/**
	 * Post deserialise.
	 */
	public void postDeserialise()
	{
		for (Field f : fields) {
			f.parseDates();
		}
	}
}
