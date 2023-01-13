package com.mailerlite.sdk.util;

public abstract class PaginatedRequest<T extends PaginatedRequest<T>> extends ApiRequest {

	protected abstract T getInstance();
	
	public T page(int page)
	{
		this.AddQueryParameter("page", Integer.toString(page));

		return getInstance();
	}
	
	public T limit(int limit)
	{
		this.AddQueryParameter("limit", Integer.toString(limit));
		
		return getInstance();
	}
}
