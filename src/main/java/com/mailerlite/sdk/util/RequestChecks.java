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
import com.mailerlite.sdk.exceptions.InvalidIdempotencyKeyException;

/**
 * Checks the IDs in the URL path and the idempotency key of a request before the SDK sends it.
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

	/**
	 * Checks an idempotency key.
	 * <p>
	 * The key must contain only printable ASCII characters (0x20 to 0x7E). The Java HTTP client sends each character
	 * that is not ASCII as "?", so the API would get a different key, and two different keys could become the same
	 * key. The API checks the length and the other rules of the key.
	 *
	 * @param key the idempotency key, or null
	 * @return the key
	 * @throws InvalidIdempotencyKeyException if the key contains a character outside 0x20 to 0x7E
	 */
	public static String idempotencyKey(String key)
	{
		if (key == null) {
			return null;
		}

		for (int i = 0; i < key.length(); i++) {

			char c = key.charAt(i);

			if (c < 0x20 || c > 0x7E) {
				throw new InvalidIdempotencyKeyException("idempotency key must contain only printable ASCII characters"
						+ " (0x20 to 0x7E), because the Java HTTP client changes the other characters");
			}
		}

		return key;
	}
}
