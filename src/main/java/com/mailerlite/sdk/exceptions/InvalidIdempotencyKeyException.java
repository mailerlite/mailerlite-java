/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.exceptions;

/**
 * An idempotency key contains a character that is not printable ASCII (0x20 to 0x7E).
 * The Java HTTP client sends each character that is not ASCII as "?", so the API would get a different key.
 * The SDK sends no request when it throws this exception.
 */
public class InvalidIdempotencyKeyException extends IllegalArgumentException {

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
