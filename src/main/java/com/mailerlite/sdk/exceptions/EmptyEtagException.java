/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.exceptions;

/**
 * A write has an empty or null etag.
 * The SDK sends no request when it throws this exception.
 */
public class EmptyEtagException extends IllegalArgumentException {

	/** The serial version UID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Makes the exception.
	 *
	 * @param message the message
	 */
	public EmptyEtagException(String message)
	{
		super(message);
	}
}
