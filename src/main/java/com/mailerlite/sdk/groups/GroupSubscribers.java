package com.mailerlite.sdk.groups;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.MailerLiteResponse;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.util.PaginatedRequest;

public class GroupSubscribers extends PaginatedRequest<GroupSubscribers> {

	private MailerLite apiObjectReference;
	
	private String groupId;
	
	public GroupSubscribers(MailerLite apiRef, String forGroupId)
	{
		apiObjectReference = apiRef;
		groupId = forGroupId;
	}
	
	public GroupSubscribers statusFilter(String statusFilter)
	{
		this.addQueryParameter("filter[status]", statusFilter);
		
		return this;
	}
	
	public GroupSubscribersList get() throws MailerLiteException
	{
		String endpoint = "/groups/".concat(this.groupId).concat("/subscribers").concat(this.getQueryParameters());
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		GroupSubscribersList list = api.getRequest(endpoint, GroupSubscribersList.class);
		
		list.postDeserialize();
		
		return list;
	}
	
	public SingleGroup assign(String subscriberId) throws MailerLiteException
	{
		String endpoint = "/subscribers/".concat(subscriberId).concat("/groups/").concat(groupId);
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		SingleGroup group = api.postRequest(endpoint, "", SingleGroup.class);
		
		group.group.parseDates();
		
		return group;
	}
	
	public MailerLiteResponse unassign(String subscriberId) throws MailerLiteException
	{
		String endpoint = "/subscribers/".concat(subscriberId).concat("/groups/").concat(groupId);
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		MailerLiteResponse response = api.deleteRequest(endpoint, MailerLiteResponse.class);
		
		return response;
	}

	@Override
	protected GroupSubscribers getInstance() {
		return this;
	}
}
