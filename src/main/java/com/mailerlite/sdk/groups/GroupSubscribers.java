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
import com.mailerlite.sdk.MailerLiteResponse;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.util.PaginatedRequest;

/**
 * The Class GroupSubscribers.
 */
public class GroupSubscribers extends PaginatedRequest<GroupSubscribers> {

	/** The api object reference. */
	private MailerLite apiObjectReference;
	
	/** The group id. */
	private String groupId;
	
	/**
	 * Instantiates a new group subscribers.
	 *
	 * @param apiRef the api ref
	 * @param forGroupId the for group id
	 */
	public GroupSubscribers(MailerLite apiRef, String forGroupId)
	{
		apiObjectReference = apiRef;
		groupId = forGroupId;
	}
	
	/**
	 * Status filter.
	 *
	 * @param statusFilter the status filter
	 * @return the group subscribers
	 */
	public GroupSubscribers statusFilter(String statusFilter)
	{
		this.addQueryParameter("filter[status]", statusFilter);
		
		return this;
	}
	
	/**
	 * Gets the.
	 *
	 * @return the group subscribers list
	 * @throws MailerLiteException the mailer lite exception
	 */
	public GroupSubscribersList get() throws MailerLiteException
	{
		String endpoint = "/groups/".concat(this.groupId).concat("/subscribers").concat(this.getQueryParameters());
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		GroupSubscribersList list = api.getRequest(endpoint, GroupSubscribersList.class);
		
		list.postDeserialize();
		
		return list;
	}
	
	/**
	 * Assign.
	 *
	 * @param subscriberId the subscriber id
	 * @return the single group
	 * @throws MailerLiteException the mailer lite exception
	 */
	public SingleGroup assign(String subscriberId) throws MailerLiteException
	{
		String endpoint = "/subscribers/".concat(subscriberId).concat("/groups/").concat(groupId);
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		SingleGroup group = api.postRequest(endpoint, "", SingleGroup.class);
		
		group.group.parseDates();
		
		return group;
	}
	
	/**
	 * Unassign.
	 *
	 * @param subscriberId the subscriber id
	 * @return the mailer lite response
	 * @throws MailerLiteException the mailer lite exception
	 */
	public MailerLiteResponse unassign(String subscriberId) throws MailerLiteException
	{
		String endpoint = "/subscribers/".concat(subscriberId).concat("/groups/").concat(groupId);
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		MailerLiteResponse response = api.deleteRequest(endpoint, MailerLiteResponse.class);
		
		return response;
	}

	/**
	 * Gets the single instance of GroupSubscribers.
	 *
	 * @return single instance of GroupSubscribers
	 */
	@Override
	protected GroupSubscribers getInstance() {
		return this;
	}
}
