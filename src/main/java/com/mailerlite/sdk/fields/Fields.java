/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.fields;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.MailerLiteResponse;
import com.mailerlite.sdk.exceptions.MailerLiteException;

/**
 * The Class Fields.
 */
public class Fields {

	/** The api object reference. */
	private MailerLite apiObjectReference;
	
	/**
	 * Instantiates a new fields.
	 *
	 * @param apiRef the api ref
	 */
	public Fields(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}
	
	/**
	 * Retriever.
	 *
	 * @return the field retriever
	 */
	public FieldRetriever retriever()
	{
		return new FieldRetriever(apiObjectReference);
	}
	
	/**
	 * Builder.
	 *
	 * @return the field builder
	 */
	public FieldBuilder builder()
	{
		return new FieldBuilder(apiObjectReference);
	}
	
	/**
	 * Delete.
	 *
	 * @param fieldId the field id
	 * @return the mailer lite response
	 * @throws MailerLiteException the mailer lite exception
	 */
	public MailerLiteResponse delete(String fieldId) throws MailerLiteException
	{
		String endpoint = "/fields/".concat(fieldId);
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		MailerLiteResponse response = api.deleteRequest(endpoint, MailerLiteResponse.class);
		
		return response;
	}
}
