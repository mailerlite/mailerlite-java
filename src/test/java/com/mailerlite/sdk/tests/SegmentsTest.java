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
import com.mailerlite.sdk.segments.Segment;
import com.mailerlite.sdk.segments.SegmentSubscribersList;
import com.mailerlite.sdk.segments.SegmentsList;
import com.mailerlite.sdk.segments.SingleSegment;
import com.mailerlite.sdk.susbcribers.Subscriber;
import com.mailerlite.sdk.vcr.VcrRecorder;


/**
 * The Class SegmentsTest.
 */
public class SegmentsTest extends TestBase {

	/**
	 * Sets the up each.
	 *
	 * @param info the new up each
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@BeforeEach
	public void setupEach(TestInfo info) throws IOException
	{
		VcrRecorder.useRecording("SegmentsTest_" + info.getDisplayName());
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
	 * Test get segments.
	 */
	@Test
	public void TestGetSegments()
	{
		
		try {
			
			SegmentsList segments = this.getMailerLite().segments().retriever().get();
			
			for (Segment s : segments.segments) {
				
				System.out.println(s.id);
				System.out.println(s.name);
			}
			
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
	
	
	/**
	 * Test get single segment.
	 */
	@Test
	public void TestGetSingleSegment()
	{
		
		try {
			
			SegmentsList segments = this.getMailerLite().segments().retriever().get();
			
			SingleSegment segment = this.getMailerLite().segments().retriever().getSingle(segments.segments[0].id);
						
			System.out.println(segment.segment.id);
			System.out.println(segment.segment.name);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * Test get segment subscribers.
	 */
	@Test
	public void TestGetSegmentSubscribers()
	{
		try {
			
			SegmentsList segments = this.getMailerLite().segments().retriever().get();
			
			SegmentSubscribersList list = this.getMailerLite().segments().subscribers(segments.segments[0].id).get();
						
			int count = 0;
			for (Subscriber s : list.subscribers) {
				
				System.out.println(s.email);
				
				count++;
				if (count > 10) {
					break;
				}
			}
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * Test update segment.
	 */
	@Test
	public void TestUpdateSegment()
	{
		try {
			
			SegmentsList segments = this.getMailerLite().segments().retriever().get();
			
			SingleSegment segment = this.getMailerLite().segments().update(segments.segments[0].id, "New segment name");
			
			System.out.println(segment.segment.name);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * Test delete segment.
	 */
	@Test
	public void TestDeleteSegment()
	{
		try {
			
			SegmentsList segments = this.getMailerLite().segments().retriever().get();
			
			MailerLiteResponse response = this.getMailerLite().segments().delete(segments.segments[0].id);
			
			assertEquals(204, response.responseStatusCode);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
	
}
