/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.util;

import com.mailerlite.sdk.exceptions.EmptyIdException;
import com.mailerlite.sdk.exceptions.InvalidIdException;

/**
 * Checks the IDs in the URL path of a request before the SDK sends it.
 * Each check throws an exception that extends IllegalArgumentException, and the SDK then sends no request.
 */
public final class RequestChecks {

	private RequestChecks()
	{
	}

	/**
	 * Checks an ID that goes into the URL path of a request.
	 * <p>
	 * The ID must contain only the ASCII digits 0 to 9. URL encoding is not sufficient, because the server
	 * decodes "%2F" to "/". Other Unicode digits, such as "²", are not accepted.
	 *
	 * @param value the ID
	 * @param name the name of the parameter, for the error message
	 * @return the ID
	 * @throws EmptyIdException if the ID is null or empty
	 * @throws InvalidIdException if the ID contains a character that is not an ASCII digit
	 */
	public static String pathId(String value, String name)
	{
		if (value == null || value.isEmpty()) {
			throw new EmptyIdException(name + " must not be empty");
		}

		for (int i = 0; i < value.length(); i++) {

			char c = value.charAt(i);

			if (c < '0' || c > '9') {
				throw new InvalidIdException(name + " must contain only the digits 0 to 9");
			}
		}

		return value;
	}
}
