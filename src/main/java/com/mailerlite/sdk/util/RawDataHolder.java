/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.util;

import com.google.gson.JsonObject;

/**
 * Implemented by API objects that keep the raw JSON object they were parsed from.
 */
public interface RawDataHolder {

	/**
	 * Sets the raw JSON object.
	 *
	 * @param data the raw JSON object
	 */
	void setData(JsonObject data);
}
