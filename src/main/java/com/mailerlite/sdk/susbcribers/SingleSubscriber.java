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
 * The Class SingleSubscriber.
 */
public class SingleSubscriber extends MailerLiteResponse {

	/** The subscriber. */
	@SerializedName("data")
	public Subscriber subscriber;
}
