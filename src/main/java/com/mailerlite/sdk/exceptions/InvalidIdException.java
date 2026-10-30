/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.exceptions;

/**
 * An email, block or version ID contains a character that is not an ASCII digit (0 to 9).
 * The SDK sends no request when it throws this exception.
 */
public class InvalidIdException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	/**
	 * Makes the exception.
	 *
	 * @param message the message
	 */
	public InvalidIdException(String message)
	{
		super(message);
	}
}
