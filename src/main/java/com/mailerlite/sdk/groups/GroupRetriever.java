/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.groups;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.util.PaginatedRequest;

public class GroupRetriever extends PaginatedRequest<GroupRetriever> {

	private MailerLite apiObjectReference;
	
	public GroupRetriever(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}
	
	public GroupRetriever nameFilter(String nameFilter)
	{
		this.addQueryParameter("filter[name]", nameFilter);
		
		return this;
	}
	
	public GroupRetriever sort(String sort)
	{
		this.addQueryParameter("sort", sort);
		return this;
	}
	
	public GroupsList get() throws MailerLiteException
	{
		String endpoint = "/groups".concat(this.getQueryParameters());
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		GroupsList list = api.getRequest(endpoint, GroupsList.class);
		
		list.postDeserialize();
		
		return list;
	}
	
	public SingleGroup getSingle(String groupId) throws MailerLiteException
	{
		String endpoint = "/groups/".concat(groupId);
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		SingleGroup group = api.getRequest(endpoint, SingleGroup.class);
		
		group.group.parseDates();
		
		return group;
	}

	@Override
	protected GroupRetriever getInstance() {
		return this;
	}
}
