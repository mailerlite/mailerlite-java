package com.mailerlite.sdk.susbcribers;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.MailerLiteResponse;
import com.mailerlite.sdk.exceptions.MailerLiteException;

public class Subscribers {

	private MailerLite apiObjectReference;
	
	public Subscribers(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}
	
	public SubscriberRetriever retriever()
	{
		return new SubscriberRetriever(apiObjectReference);
	}
	
	public SubscriberBuilder builder()
	{
		return new SubscriberBuilder(apiObjectReference);
	}
	
	public MailerLiteResponse delete(String subscriberId) throws MailerLiteException
	{
		String endpoint = "/subscribers/".concat(subscriberId);
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		MailerLiteResponse response = api.deleteRequest(endpoint, MailerLiteResponse.class);
		
		return response;
	}
	
	public SubscriberImportsRetriever imports()
	{
		return new SubscriberImportsRetriever(apiObjectReference);
	}
}
