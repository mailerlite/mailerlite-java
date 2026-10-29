/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.exceptions;

/**
 * An idempotency key is not 1 to 255 printable ASCII characters, or it has a space at the start or the end.
 * The SDK sends no request when it throws this exception.
 */
public class InvalidIdempotencyKeyException extends IllegalArgumentException {

	/** The serial version UID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Makes the exception.
	 *
	 * @param message the message
	 */
	public InvalidIdempotencyKeyException(String message)
	{
		super(message);
	}
}
