/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.webhooks;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.MailerLiteResponse;

/**
 * The Class SingleWebhook.
 */
public class SingleWebhook extends MailerLiteResponse {

	/** The webhook. */
	@SerializedName("data")
	public Webhook webhook;
}
