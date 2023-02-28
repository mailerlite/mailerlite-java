package com.mailerlite.sdk.util;

public abstract class PaginatedRequest<T extends PaginatedRequest<T>> extends ApiRequest {

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
