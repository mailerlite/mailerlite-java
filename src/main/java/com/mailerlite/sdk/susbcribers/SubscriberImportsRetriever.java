package com.mailerlite.sdk.susbcribers;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.exceptions.MailerLiteException;

public class SubscriberImportsRetriever {

	private MailerLite apiObjectReference;
	
	public SubscriberImportsRetriever(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}
	
	public SubscriberImportList get() throws MailerLiteException
	{
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		SubscriberImportList response = api.getRequest("/subscribers/import", SubscriberImportList.class);
		
		response.postDeserialize();
		
		return response;
	}
	
	public SingleSubscriberImport getSingle(String importId) throws MailerLiteException
	{
		String endpoint = "/subscribers/import/".concat(importId);
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		SingleSubscriberImport response = api.getRequest(endpoint, SingleSubscriberImport.class);
		
		response.postDeserialize();
		
		return response;
	}
}
