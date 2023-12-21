/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.forms;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.MailerLiteResponse;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.groups.NameCreatorBody;
import com.mailerlite.sdk.util.JsonSerializationDeserializationStrategy;

/**
 * The Class Forms.
 */
public class Forms {

	/** The api object reference. */
	private MailerLite apiObjectReference;
	
	/**
	 * Instantiates a new forms.
	 *
	 * @param apiRef the api ref
	 */
	public Forms(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}
	
	
	/**
	 * Retriever.
	 *
	 * @return the forms retriever
	 */
	public FormsRetriever retriever()
	{
		return new FormsRetriever(apiObjectReference);
	}
	
	
	/**
	 * Subscribers.
	 *
	 * @return the subscribers retriever
	 */
	public SubscribersRetriever subscribers()
	{
		return new SubscribersRetriever(apiObjectReference);
	}
	
	
	/**
	 * Update.
	 *
	 * @param formId the form id
	 * @param newFormName the new form name
	 * @return the single form
	 * @throws MailerLiteException the mailer lite exception
	 */
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
	
	
	/**
	 * Delete.
	 *
	 * @param formId the form id
	 * @return the mailer lite response
	 * @throws MailerLiteException the mailer lite exception
	 */
	public MailerLiteResponse delete(String formId) throws MailerLiteException
	{
		String endpoint = "/forms/".concat(formId);
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		MailerLiteResponse response = api.deleteRequest(endpoint, MailerLiteResponse.class);
		
		return response;
	}
}
