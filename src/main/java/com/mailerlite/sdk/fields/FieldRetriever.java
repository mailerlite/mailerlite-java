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

public class FieldRetriever extends PaginatedRequest<FieldRetriever> {

	private MailerLite apiObjectReference;
	
	public FieldRetriever(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}
	
	public FieldRetriever filter(String name, String value)
	{
		this.addQueryParameter("filter[".concat(name).concat("]"), value);
		
		return this;
	}
	
	public FieldRetriever sort(String sort)
	{
		this.addQueryParameter("sort", sort);
		return this;
	}
	
	public FieldList get() throws MailerLiteException
	{
		String endpoint = "/fields".concat(this.getQueryParameters());
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		FieldList list = api.getRequest(endpoint, FieldList.class);
		
		list.postDeserialize();
		
		return list;
	}
	
	@Override
	protected FieldRetriever getInstance() {
		return this;
	}
}
