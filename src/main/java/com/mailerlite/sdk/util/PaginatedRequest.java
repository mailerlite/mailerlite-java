/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.util;

/**
 * The Class PaginatedRequest.
 *
 * @param <T> the generic type
 */
public abstract class PaginatedRequest<T extends PaginatedRequest<T>> extends ApiRequest {

	/**
	 * Gets the single instance of PaginatedRequest.
	 *
	 * @return single instance of PaginatedRequest
	 */
	protected abstract T getInstance();
	
	/**
	 * Cursor.
	 *
	 * @param cursor the cursor
	 * @return the t
	 */
	public T cursor(String cursor)
	{
		this.addQueryParameter("cursor", cursor);

		return getInstance();
	}
	
	/**
	 * Limit.
	 *
	 * @param limit the limit
	 * @return the t
	 */
	public T limit(int limit)
	{
		this.addQueryParameter("limit", Integer.toString(limit));
		
		return getInstance();
	}
}
