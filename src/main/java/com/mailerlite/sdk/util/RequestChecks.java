/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.util;

import com.mailerlite.sdk.exceptions.EmptyEtagException;
import com.mailerlite.sdk.exceptions.EmptyIdException;
import com.mailerlite.sdk.exceptions.InvalidIdException;
import com.mailerlite.sdk.exceptions.InvalidIdempotencyKeyException;

/**
 * Checks the input of a request before the SDK sends it.
 * Each check throws an exception that extends IllegalArgumentException, and the SDK then sends no request.
 */
public final class RequestChecks {

	private static final int MAX_IDEMPOTENCY_KEY_LENGTH = 255;

	private RequestChecks()
	{
	}

	/**
	 * Checks an ID that goes into the path or the body of a request.
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
	 * Checks the etag of a write.
	 *
	 * @param value the etag from the last response for the email
	 * @return the etag
	 * @throws EmptyEtagException if the etag is null or empty
	 */
	public static String etag(String value)
	{
		if (value == null || value.isEmpty()) {
			throw new EmptyEtagException("etag must not be empty");
		}

		return value;
	}

	/**
	 * Checks an idempotency key.
	 * <p>
	 * The key must have 1 to 255 characters. Each character must be printable ASCII (0x20 to 0x7E).
	 * The key must not start or end with a space, because HTTP removes these spaces and the API then gets
	 * a different key.
	 *
	 * @param key the key
	 * @return the key
	 * @throws InvalidIdempotencyKeyException if the key is null or does not obey these rules
	 */
	public static String idempotencyKey(String key)
	{
		if (key == null || key.isEmpty() || key.length() > MAX_IDEMPOTENCY_KEY_LENGTH
				|| key.charAt(0) == ' ' || key.charAt(key.length() - 1) == ' ') {
			throw invalidKey();
		}

		for (int i = 0; i < key.length(); i++) {

			char c = key.charAt(i);

			if (c < 0x20 || c > 0x7E) {
				throw invalidKey();
			}
		}

		return key;
	}

	private static InvalidIdempotencyKeyException invalidKey()
	{
		return new InvalidIdempotencyKeyException(
				"the idempotency key must be 1 to 255 printable ASCII characters, with no space at the start or the end");
	}
}
