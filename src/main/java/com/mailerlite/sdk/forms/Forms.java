package com.mailerlite.sdk.forms;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.MailerLiteResponse;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.groups.NameCreatorBody;
import com.mailerlite.sdk.util.JsonSerializationDeserializationStrategy;

public class Forms {

	private MailerLite apiObjectReference;
	
	public Forms(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}
	
	
	public FormsRetriever retriever()
	{
		return new FormsRetriever(apiObjectReference);
	}
	
	
	public SubscribersRetriever subscribers()
	{
		return new SubscribersRetriever(apiObjectReference);
	}
	
	
	public SingleForm update(String formId, String newFormName) throws MailerLiteException
	{
		MailerLiteApi api = new MailerLiteApi();
        api.setToken(apiObjectReference.getToken());
        
        NameCreatorBody requestBody = new NameCreatorBody();
        requestBody.name = newFormName;
        
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();
       
        String json = gson.toJson(requestBody);
        
        SingleForm form = api.putRequest("/forms/".concat(formId), json, SingleForm.class);
        
        form.form.parseDates();
        
        return form;
	}
	
	
	public MailerLiteResponse delete(String formId) throws MailerLiteException
	{
		String endpoint = "/forms/".concat(formId);
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		MailerLiteResponse response = api.deleteRequest(endpoint, MailerLiteResponse.class);
		
		return response;
	}
}
