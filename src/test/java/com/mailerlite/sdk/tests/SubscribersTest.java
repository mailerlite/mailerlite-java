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
import com.mailerlite.sdk.susbcribers.SingleSubscriberImport;
import com.mailerlite.sdk.susbcribers.Subscriber;
import com.mailerlite.sdk.susbcribers.SubscriberCount;
import com.mailerlite.sdk.susbcribers.SubscriberImport;
import com.mailerlite.sdk.susbcribers.SubscriberImportList;
import com.mailerlite.sdk.susbcribers.SubscribersList;
import com.mailerlite.sdk.vcr.VcrRecorder;

/**
 * The Class SubscribersTest.
 */
public class SubscribersTest extends TestBase {

	/**
	 * Sets the up each.
	 *
	 * @param info the new up each
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@BeforeEach
	public void setupEach(TestInfo info) throws IOException
	{
		VcrRecorder.useRecording("SubscribersTest_" + info.getDisplayName());
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
	 * Test get subscribers.
	 */
	@Test
	public void TestGetSubscribers()
	{
		
		try {
			
			SubscribersList subscribers = this.getMailerLite().subscribers().retriever().filter("status", "active").get();
			
			for (Subscriber s : subscribers.subscribers) {
				
				System.out.println(s.id);
				System.out.println(s.email);
				System.out.println(s.createdAt.toString());
			}
			
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
	
	
	/**
	 * Test get single subscriber.
	 */
	@Test
	public void TestGetSingleSubscriber()
	{
		try {
			
			SubscribersList subscribers = this.getMailerLite().subscribers().retriever().filter("status", "active").get();
			
			Subscriber subscriber = this.getMailerLite().subscribers().retriever().getSingle(subscribers.subscribers[0].id).subscriber;
			
			System.out.println(subscriber.email);
			
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * Test create subscriber.
	 */
	@Test
	public void TestCreateSubscriber()
	{
		try {
			
			Subscriber subscriber = this.getMailerLite().subscribers().builder().email("test+email2@mailerlite.com")
					.addField("name", "test name")
					.create()
					.subscriber;
			
			System.out.println(subscriber.email);
			System.out.println(subscriber.fields.get("name"));
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * Test update subscriber.
	 */
	@Test
	public void TestUpdateSubscriber()
	{
		try {
			
			SubscribersList subscribers = this.getMailerLite().subscribers().retriever().filter("status", "active").get();
			
			Subscriber subscriber = this.getMailerLite().subscribers().builder()
					.addField("name", "test name2")
					.update(subscribers.subscribers[0].id)
					.subscriber;
			
			System.out.println(subscriber.email);
			System.out.println(subscriber.fields.get("name"));
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
	
	
	/**
	 * Test subscriber count.
	 */
	@Test
	public void TestSubscriberCount()
	{
		try {
			
			SubscriberCount count = this.getMailerLite().subscribers().retriever().filter("status", "active").count();
			
			System.out.println(count.count);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
	
	
	/**
	 * Test delete subscriber.
	 */
	@Test
	public void TestDeleteSubscriber()
	{
		try {
			
			SubscribersList subscribers = this.getMailerLite().subscribers().retriever().filter("status", "active").get();
			
			MailerLiteResponse deleteResponse = this.getMailerLite().subscribers().delete(subscribers.subscribers[0].id);
			
			System.out.println(deleteResponse.responseStatusCode);
			assertEquals(204, deleteResponse.responseStatusCode);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * Test get subscriber imports.
	 */
	@Test
	public void TestGetSubscriberImports()
	{
		try {
			
			SubscriberImportList importList = this.getMailerLite().subscribers().imports().get();
			
			for (SubscriberImport i : importList.imports) {
				
				System.out.println(i.id);
				System.out.println(i.done);
			}
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * Test get single subscriber import.
	 */
	@Test
	public void TestGetSingleSubscriberImport()
	{
		try {
			
			SubscriberImportList importList = this.getMailerLite().subscribers().imports().get();
			
			SingleSubscriberImport singleImport = this.getMailerLite().subscribers().imports().getSingle(importList.imports[0].id);
			
			System.out.println(singleImport.subscriberImport.id);
			System.out.println(singleImport.subscriberImport.done);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
}
