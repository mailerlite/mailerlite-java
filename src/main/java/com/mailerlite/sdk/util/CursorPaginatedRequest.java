/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.util;

public abstract class CursorPaginatedRequest<T extends CursorPaginatedRequest<T>> extends ApiRequest {

	protected abstract T getInstance();
	
	public T page(int page)
	{
		this.addQueryParameter("page", Integer.toString(page));

		return getInstance();
	}
	
	public T limit(int limit)
	{
		this.addQueryParameter("limit", Integer.toString(limit));
		
		return getInstance();
	}
}
