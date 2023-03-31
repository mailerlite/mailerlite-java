package com.mailerlite.sdk.fields;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.groups.NameCreatorBody;
import com.mailerlite.sdk.util.JsonSerializationDeserializationStrategy;

public class FieldBuilder {

	private MailerLite apiObjectReference;
	
	public FieldBuilder(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}
	
	public SingleField create(String name, String type) throws MailerLiteException
	{
		FieldCreatorBody requestBody = new FieldCreatorBody();
		requestBody.name = name;
		requestBody.type = type;
		
		MailerLiteApi api = new MailerLiteApi();
        api.setToken(apiObjectReference.getToken());
        
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();
       
        String json = gson.toJson(requestBody);
        
        SingleField field = api.postRequest("/fields", json, SingleField.class);
        
        field.field.parseDates();
        
        return field;
	}
	
	public SingleField update(String fieldId, String newFieldName) throws MailerLiteException
	{
		MailerLiteApi api = new MailerLiteApi();
        api.setToken(apiObjectReference.getToken());
        
        NameCreatorBody requestBody = new NameCreatorBody();
        requestBody.name = newFieldName;
        
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();
       
        String json = gson.toJson(requestBody);
        
        SingleField field = api.putRequest("/fields/".concat(fieldId), json, SingleField.class);
        
        field.field.parseDates();
        
        return field;
	}
}
