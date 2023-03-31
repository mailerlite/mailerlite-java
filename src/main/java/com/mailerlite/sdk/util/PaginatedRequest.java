package com.mailerlite.sdk.util;


public abstract class PaginatedRequest<T extends PaginatedRequest<T>> extends ApiRequest {

	protected abstract T getInstance();
	
	public T cursor(String cursor)
	{
		this.addQueryParameter("cursor", cursor);

		return getInstance();
	}
	
	public T limit(int limit)
	{
		this.addQueryParameter("limit", Integer.toString(limit));
		
		return getInstance();
	}
}
