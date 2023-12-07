package com.mailerlite.sdk.forms;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.util.PaginatedRequest;

public class FormsRetriever  extends PaginatedRequest<FormsRetriever> {

private MailerLite apiObjectReference;
	
	public FormsRetriever(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}
	
	public FormsRetriever nameFilter(String nameFilter)
	{
		this.addQueryParameter("filter[name]", nameFilter);
		
		return this;
	}
	
	public FormsRetriever sort(String sort)
	{
		this.addQueryParameter("sort", sort);
		return this;
	}
	
	public FormsList get() throws MailerLiteException
	{
		String endpoint = "/forms".concat(this.getQueryParameters());
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		FormsList list = api.getRequest(endpoint, FormsList.class);
		
		list.postDeserialize();
		
		return list;
	}
	
	public SingleForm getSingle(String formId) throws MailerLiteException
	{
		String endpoint = "/forms/".concat(formId);
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		SingleForm form = api.getRequest(endpoint, SingleForm.class);
		
		form.form.parseDates();
		
		return form;
	}
	

	@Override
	protected FormsRetriever getInstance() {
		return this;
	}
}
