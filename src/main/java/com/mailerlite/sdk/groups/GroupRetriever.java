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

/**
 * The Class GroupRetriever.
 */
public class GroupRetriever extends PaginatedRequest<GroupRetriever> {

	/** The api object reference. */
	private MailerLite apiObjectReference;
	
	/**
	 * Instantiates a new group retriever.
	 *
	 * @param apiRef the api ref
	 */
	public GroupRetriever(MailerLite apiRef)
	{
		apiObjectReference = apiRef;
	}
	
	/**
	 * Name filter.
	 *
	 * @param nameFilter the name filter
	 * @return the group retriever
	 */
	public GroupRetriever nameFilter(String nameFilter)
	{
		this.addQueryParameter("filter[name]", nameFilter);
		
		return this;
	}
	
	/**
	 * Sort.
	 *
	 * @param sort the sort
	 * @return the group retriever
	 */
	public GroupRetriever sort(String sort)
	{
		this.addQueryParameter("sort", sort);
		return this;
	}
	
	/**
	 * Gets the.
	 *
	 * @return the groups list
	 * @throws MailerLiteException the mailer lite exception
	 */
	public GroupsList get() throws MailerLiteException
	{
		String endpoint = "/groups".concat(this.getQueryParameters());
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		GroupsList list = api.getRequest(endpoint, GroupsList.class);
		
		list.postDeserialize();
		
		return list;
	}
	
	/**
	 * Gets the single.
	 *
	 * @param groupId the group id
	 * @return the single
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SingleGroup getSingle(String groupId) throws MailerLiteException
	{
		String endpoint = "/groups/".concat(groupId);
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		SingleGroup group = api.getRequest(endpoint, SingleGroup.class);
		
		group.group.parseDates();
		
		return group;
	}

	/**
	 * Gets the single instance of GroupRetriever.
	 *
	 * @return single instance of GroupRetriever
	 */
	@Override
	protected GroupRetriever getInstance() {
		return this;
	}
}
