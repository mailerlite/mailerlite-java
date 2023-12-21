/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.susbcribers;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.exceptions.MailerLiteException;

/**
 * The Class SubscriberImportsRetriever.
 */
public class SubscriberImportsRetriever {

	/** The api object reference. */
	private MailerLite apiObjectReference;
	
	/**
	 * Instantiates a new subscriber imports retriever.
	 *
	 * @param apiRef the api ref
	 */
	public SubscriberImportsRetriever(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}
	
	/**
	 * Gets the.
	 *
	 * @return the subscriber import list
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SubscriberImportList get() throws MailerLiteException
	{
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		SubscriberImportList response = api.getRequest("/subscribers/import", SubscriberImportList.class);
		
		response.postDeserialize();
		
		return response;
	}
	
	/**
	 * Gets the single.
	 *
	 * @param importId the import id
	 * @return the single
	 * @throws MailerLiteException the mailer lite exception
	 */
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
