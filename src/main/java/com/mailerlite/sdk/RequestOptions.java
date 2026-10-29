/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk;

import com.mailerlite.sdk.exceptions.InvalidIdempotencyKeyException;
import com.mailerlite.sdk.util.RequestChecks;

/**
 * Options for one request, for example an idempotency key.
 * <p>
 * The email writes, {@code Emails.createTemplate} and {@code Emails.createVersion} accept the options as the last
 * argument.
 */
public final class RequestOptions {

	private final String idempotencyKey;

	private RequestOptions(String idempotencyKey)
	{
		this.idempotencyKey = idempotencyKey;
	}

	/**
	 * Makes options that send the key in the Idempotency-Key header.
	 * <p>
	 * Use a new key for each write, and the same key when you send that write again. If the first request was a
	 * success, the API returns the stored response and does not do the write two times. Then
	 * {@link MailerLiteResponse#isIdempotentReplay()} is true. The API keeps a key for 24 hours.
	 * <p>
	 * The key must have 1 to 255 printable ASCII characters (0x20 to 0x7E), with no space at the start or the end.
	 * A null key sends no header.
	 *
	 * @param key the idempotency key, or null for no key
	 * @return the options
	 * @throws InvalidIdempotencyKeyException if the key does not obey the rules
	 */
	public static RequestOptions idempotencyKey(String key)
	{
		if (key != null) {
			RequestChecks.idempotencyKey(key);
		}

		return new RequestOptions(key);
	}

	/**
	 * Gets the idempotency key.
	 *
	 * @return the idempotency key, or null
	 */
	public String getIdempotencyKey()
	{
		return idempotencyKey;
	}
}
