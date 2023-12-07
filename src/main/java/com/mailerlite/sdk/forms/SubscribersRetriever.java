package com.mailerlite.sdk.forms;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.susbcribers.SubscribersList;
import com.mailerlite.sdk.util.PaginatedRequest;

public class SubscribersRetriever  extends PaginatedRequest<SubscribersRetriever> {

	private MailerLite apiObjectReference;
	
	public SubscribersRetriever(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}
	
	public SubscribersRetriever statusFilter(String statusFilter)
	{
		this.addQueryParameter("filter[status]", statusFilter);
		
		return this;
	}
	
	public SubscribersRetriever cursor(String cursor)
	{
		this.addQueryParameter("cursor", cursor);
		return this;
	}
	
	public SubscribersRetriever limit(int limit)
	{
		this.addQueryParameter("limit", String.valueOf(limit));
		return this;
	}
	
	public SubscribersList get(String formId) throws MailerLiteException
	{
		String endpoint = "/forms/".concat(formId).concat("/subscribers").concat(this.getQueryParameters());
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		SubscribersList list = api.getRequest(endpoint, SubscribersList.class);
		
		list.postDeserialize();
		
		return list;
	}
	

	@Override
	protected SubscribersRetriever getInstance() {
		return this;
	}
}
