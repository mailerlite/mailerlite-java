/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.fields;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.groups.NameCreatorBody;
import com.mailerlite.sdk.util.JsonSerializationDeserializationStrategy;

/**
 * The Class FieldBuilder.
 */
public class FieldBuilder {

	/** The api object reference. */
	private MailerLite apiObjectReference;
	
	/**
	 * Instantiates a new field builder.
	 *
	 * @param apiRef the api ref
	 */
	public FieldBuilder(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}
	
	/**
	 * Creates the.
	 *
	 * @param name the name
	 * @param type the type
	 * @return the single field
	 * @throws MailerLiteException the mailer lite exception
	 */
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
	
	/**
	 * Update.
	 *
	 * @param fieldId the field id
	 * @param newFieldName the new field name
	 * @return the single field
	 * @throws MailerLiteException the mailer lite exception
	 */
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
