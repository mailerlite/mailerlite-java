package com.mailerlite.sdk.susbcribers;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.util.CursorPaginatedRequest;

public class SubscriberRetriever extends CursorPaginatedRequest<SubscriberRetriever> {

	private MailerLite apiObjectReference;
	
	public SubscriberRetriever(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}
	
	public SubscriberRetriever filter(String name, String value)
	{
		this.addQueryParameter("filter[".concat(name).concat("]"), value);
		
		return this;
	}
	
	public SubscribersList get() throws MailerLiteException
	{
		String endpoint = "/subscribers".concat(this.getQueryParameters());
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		SubscribersList list = api.getRequest(endpoint, SubscribersList.class);
		
		list.postDeserialize();
		
		return list;
	}
	
	public SingleSubscriber getSingle(String idOrEmail) throws MailerLiteException
	{
		String endpoint = "/subscribers/".concat(idOrEmail);
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		SingleSubscriber subscriber = api.getRequest(endpoint, SingleSubscriber.class);
		
		subscriber.subscriber.parseDates();
		
		return subscriber;
	}
	
	public SubscriberCount count() throws MailerLiteException
	{
		this.limit(0);
		
		String endpoint = "/subscribers".concat(this.getQueryParameters());
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		SubscriberCount count = api.getRequest(endpoint, SubscriberCount.class);
		
		return count;
	}

	@Override
	protected SubscriberRetriever getInstance() {
		return this;
	}
}
