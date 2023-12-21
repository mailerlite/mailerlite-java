/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.forms;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.util.PaginatedRequest;

/**
 * The Class FormsRetriever.
 */
public class FormsRetriever  extends PaginatedRequest<FormsRetriever> {

/** The api object reference. */
private MailerLite apiObjectReference;
	
	/**
	 * Instantiates a new forms retriever.
	 *
	 * @param apiRef the api ref
	 */
	public FormsRetriever(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}
	
	/**
	 * Name filter.
	 *
	 * @param nameFilter the name filter
	 * @return the forms retriever
	 */
	public FormsRetriever nameFilter(String nameFilter)
	{
		this.addQueryParameter("filter[name]", nameFilter);
		
		return this;
	}
	
	/**
	 * Sort.
	 *
	 * @param sort the sort
	 * @return the forms retriever
	 */
	public FormsRetriever sort(String sort)
	{
		this.addQueryParameter("sort", sort);
		return this;
	}
	
	/**
	 * Gets the.
	 *
	 * @param type the type
	 * @return the forms list
	 * @throws MailerLiteException the mailer lite exception
	 */
	public FormsList get(String type) throws MailerLiteException
	{
		String endpoint = "/forms/".concat(type).concat(this.getQueryParameters());
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		FormsList list = api.getRequest(endpoint, FormsList.class);
		
		list.postDeserialize();
		
		return list;
	}
	
	/**
	 * Gets the single.
	 *
	 * @param formId the form id
	 * @return the single
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SingleForm getSingle(String formId) throws MailerLiteException
	{
		String endpoint = "/forms/".concat(formId);
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		SingleForm form = api.getRequest(endpoint, SingleForm.class);
		
		form.form.parseDates();
		
		return form;
	}
	

	/**
	 * Gets the single instance of FormsRetriever.
	 *
	 * @return single instance of FormsRetriever
	 */
	@Override
	protected FormsRetriever getInstance() {
		return this;
	}
}
