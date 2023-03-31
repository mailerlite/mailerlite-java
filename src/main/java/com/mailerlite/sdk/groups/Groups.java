package com.mailerlite.sdk.groups;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.MailerLiteResponse;
import com.mailerlite.sdk.exceptions.MailerLiteException;

public class Groups {
	private MailerLite apiObjectReference;
	
	public Groups(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}
	
	public GroupRetriever retriever()
	{
		return new GroupRetriever(apiObjectReference);
	}

	public GroupBuilder builder()
	{
		return new GroupBuilder(apiObjectReference);
	}
	
	public GroupSubscribers subscribers(String groupId)
	{
		return new GroupSubscribers(apiObjectReference, groupId);
	}
	
	public MailerLiteResponse delete(String groupId) throws MailerLiteException
	{
		String endpoint = "/groups/".concat(groupId);
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		MailerLiteResponse response = api.deleteRequest(endpoint, MailerLiteResponse.class);
		
		return response;
	}
}
