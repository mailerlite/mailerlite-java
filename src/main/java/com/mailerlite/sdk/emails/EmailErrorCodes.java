/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.emails;

import com.mailerlite.sdk.exceptions.MailerLiteException;

/**
 * The values of {@link MailerLiteException#errorCode} that the email endpoints send.
 */
public final class EmailErrorCodes {

	/**
	 * The email changed after you read the etag that the write sent. The exception has the current etag.
	 * Get the email again, and then send the write again with the current etag.
	 * The value is the same as {@link MailerLiteException#VERSION_CONFLICT}.
	 */
	public static final String VERSION_CONFLICT = MailerLiteException.VERSION_CONFLICT;

	private EmailErrorCodes()
	{
	}
}
