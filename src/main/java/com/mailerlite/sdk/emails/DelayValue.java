/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.emails;

import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.ApiResource;
import com.mailerlite.sdk.util.ParseDate;

/**
 * The Class DelayValue.
 */
public class DelayValue extends ApiResource {

	/** The unit. */
	@SerializedName("unit")
	public String unit;
	
	/** The value. */
	@SerializedName("value")
	public String value;
	
	/** The date str. */
	@SerializedName("date")
	public String dateStr;
	
	/** The delay date. */
	@ParseDate("dateStr")
	public Date delayDate;
}
