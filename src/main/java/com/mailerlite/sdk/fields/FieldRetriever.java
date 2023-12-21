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
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.util.PaginatedRequest;

/**
 * The Class FieldRetriever.
 */
public class FieldRetriever extends PaginatedRequest<FieldRetriever> {

	/** The api object reference. */
	private MailerLite apiObjectReference;
	
	/**
	 * Instantiates a new field retriever.
	 *
	 * @param apiRef the api ref
	 */
	public FieldRetriever(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}
	
	/**
	 * Filter.
	 *
	 * @param name the name
	 * @param value the value
	 * @return the field retriever
	 */
	public FieldRetriever filter(String name, String value)
	{
		this.addQueryParameter("filter[".concat(name).concat("]"), value);
		
		return this;
	}
	
	/**
	 * Sort.
	 *
	 * @param sort the sort
	 * @return the field retriever
	 */
	public FieldRetriever sort(String sort)
	{
		this.addQueryParameter("sort", sort);
		return this;
	}
	
	/**
	 * Gets the.
	 *
	 * @return the field list
	 * @throws MailerLiteException the mailer lite exception
	 */
	public FieldList get() throws MailerLiteException
	{
		String endpoint = "/fields".concat(this.getQueryParameters());
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		FieldList list = api.getRequest(endpoint, FieldList.class);
		
		list.postDeserialize();
		
		return list;
	}
	
	/**
	 * Gets the single instance of FieldRetriever.
	 *
	 * @return single instance of FieldRetriever
	 */
	@Override
	protected FieldRetriever getInstance() {
		return this;
	}
}
