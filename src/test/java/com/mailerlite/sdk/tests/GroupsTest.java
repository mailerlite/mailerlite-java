/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import com.mailerlite.sdk.MailerLiteResponse;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.groups.Group;
import com.mailerlite.sdk.groups.GroupSubscribersList;
import com.mailerlite.sdk.groups.GroupsList;
import com.mailerlite.sdk.groups.SingleGroup;
import com.mailerlite.sdk.susbcribers.Subscriber;
import com.mailerlite.sdk.susbcribers.SubscribersList;
import com.mailerlite.sdk.vcr.VcrRecorder;

/**
 * The Class GroupsTest.
 */
public class GroupsTest extends TestBase {

	/**
	 * Sets the up each.
	 *
	 * @param info the new up each
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@BeforeEach
	public void setupEach(TestInfo info) throws IOException
	{
		VcrRecorder.useRecording("GroupsTest_" + info.getDisplayName());
	}
	
	/**
	 * After each.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@AfterEach
	public void afterEach() throws IOException
	{
		VcrRecorder.stopRecording();
	}
	
	/**
	 * Test get groups.
	 */
	@Test
	public void TestGetGroups()
	{
		
		try {
			
			GroupsList groups = this.getMailerLite().groups().retriever().get();
			
			for (Group g : groups.groups) {
				
				System.out.println(g.id);
				System.out.println(g.name);
			}
			
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
	
	
	/**
	 * Test get single group.
	 */
	@Test
	public void TestGetSingleGroup()
	{
		
		try {
			
			GroupsList groups = this.getMailerLite().groups().retriever().get();
			
			SingleGroup group = this.getMailerLite().groups().retriever().getSingle(groups.groups[0].id);
			
			System.out.println(group.group.id);
			System.out.println(group.group.name);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * Test create group.
	 */
	@Test
	public void TestCreateGroup()
	{
		try {
			
			SingleGroup group = this.getMailerLite().groups().builder().create("create new group name");
			
			System.out.println(group.group.id);
			System.out.println(group.group.name);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
	
	
	/**
	 * Test update group.
	 */
	@Test
	public void TestUpdateGroup()
	{
		try {
			
			GroupsList groups = this.getMailerLite().groups().retriever().get();
			
			SingleGroup group = this.getMailerLite().groups().builder().update(groups.groups[0].id, "updated a group name");
			
			System.out.println(group.group.id);
			System.out.println(group.group.name);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
	
	
	/**
	 * Test delete group.
	 */
	@Test
	public void TestDeleteGroup()
	{
		try {
			
			GroupsList groups = this.getMailerLite().groups().retriever().get();
			
			MailerLiteResponse deleteResponse = this.getMailerLite().groups().delete(groups.groups[0].id);
			
			System.out.println(deleteResponse.responseStatusCode);
			assertEquals(204, deleteResponse.responseStatusCode);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * Test get group subscribers.
	 */
	@Test
	public void TestGetGroupSubscribers()
	{
		try {
			
			GroupsList groups = this.getMailerLite().groups().retriever().get();
			
			GroupSubscribersList subscribers = this.getMailerLite().groups().subscribers(groups.groups[0].id).get();
			
			int a = 0;
			for (Subscriber subscriber : subscribers.subscribers) {
				System.out.println(subscriber.email);
				a++;
				if (a > 10) {
					break;
				}
			}
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * Test assign subscriber to group.
	 */
	@Test
	public void TestAssignSubscriberToGroup()
	{
		try {
			
			GroupsList groups = this.getMailerLite().groups().retriever().get();
			SubscribersList subscribers = this.getMailerLite().subscribers().retriever().filter("status", "active").get();
			
			SingleGroup group = this.getMailerLite().groups().subscribers(groups.groups[0].id).assign(subscribers.subscribers[0].id);
			
			System.out.println(group.group.name);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * Test un assign subscriber to group.
	 */
	@Test
	public void TestUnAssignSubscriberToGroup()
	{
		try {
			
			GroupsList groups = this.getMailerLite().groups().retriever().get();
			GroupSubscribersList subscribers = this.getMailerLite().groups().subscribers(groups.groups[0].id).get();
			
			MailerLiteResponse response = this.getMailerLite().groups().subscribers(groups.groups[0].id).unassign(subscribers.subscribers[0].id);
			
			assertEquals(204, response.responseStatusCode);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
}
