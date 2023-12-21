/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.util;

/**
 * The Class CursorPaginatedRequest.
 *
 * @param <T> the generic type
 */
public abstract class CursorPaginatedRequest<T extends CursorPaginatedRequest<T>> extends ApiRequest {

	/**
	 * Gets the single instance of CursorPaginatedRequest.
	 *
	 * @return single instance of CursorPaginatedRequest
	 */
	protected abstract T getInstance();
	
	/**
	 * Page.
	 *
	 * @param page the page
	 * @return the t
	 */
	public T page(int page)
	{
		this.addQueryParameter("page", Integer.toString(page));

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
