/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.susbcribers;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.MailerLiteResponse;

public class SingleSubscriberImport extends MailerLiteResponse {

	@SerializedName("data")
	public SubscriberImport subscriberImport;
	
	public void postDesirialize()
	{
		subscriberImport.parseDates();
	}
}
