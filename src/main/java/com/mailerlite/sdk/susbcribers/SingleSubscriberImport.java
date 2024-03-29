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

/**
 * The Class SingleSubscriberImport.
 */
public class SingleSubscriberImport extends MailerLiteResponse {

	/** The subscriber import. */
	@SerializedName("data")
	public SubscriberImport subscriberImport;
	
	/**
	 * Post desirialize.
	 */
	public void postDesirialize()
	{
		subscriberImport.parseDates();
	}
}
