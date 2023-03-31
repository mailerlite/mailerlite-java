package com.mailerlite.sdk.fields;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.MailerLiteResponse;
import com.mailerlite.sdk.exceptions.MailerLiteException;

public class Fields {

	private MailerLite apiObjectReference;
	
	public Fields(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}
	
	public FieldRetriever retriever()
	{
		return new FieldRetriever(apiObjectReference);
	}
	
	public FieldBuilder builder()
	{
		return new FieldBuilder(apiObjectReference);
	}
	
	public MailerLiteResponse delete(String fieldId) throws MailerLiteException
	{
		String endpoint = "/fields/".concat(fieldId);
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		MailerLiteResponse response = api.deleteRequest(endpoint, MailerLiteResponse.class);
		
		return response;
	}
}
