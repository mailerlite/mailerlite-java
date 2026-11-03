/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.susbcribers;

import com.google.gson.annotations.SerializedName;

/**
 * The Class ForgottenSubscriber.
 */
public class ForgottenSubscriber extends SingleSubscriber {

	/** The message. */
	@SerializedName("message")
	public String message;
}
