/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.exceptions;

/**
 * An email, block or version ID is empty or null.
 * The SDK sends no request when it throws this exception.
 */
public class EmptyIdException extends IllegalArgumentException {

	/** The serial version UID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Makes the exception.
	 *
	 * @param message the message
	 */
	public EmptyIdException(String message)
	{
		super(message);
	}
}
