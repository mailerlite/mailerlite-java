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

public class DelayValue extends ApiResource {

	@SerializedName("unit")
	public String unit;
	
	@SerializedName("value")
	public String value;
	
	@SerializedName("date")
	public String dateStr;
	
	@ParseDate("dateStr")
	public Date delayDate;
}
