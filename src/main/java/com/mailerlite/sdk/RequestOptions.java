/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk;

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
	 * The SDK sends the key as you give it, and the API checks it. A null or empty key sends no header.
	 *
	 * @param key the idempotency key, or null for no key
	 * @return the options
	 */
	public static RequestOptions idempotencyKey(String key)
	{
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
