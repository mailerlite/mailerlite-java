/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.segments.SegmentSubscribersList;
import com.mailerlite.sdk.segments.SegmentsList;

/**
 * The Class SegmentsRequestTest.
 */
public class SegmentsRequestTest extends MockTestBase {

	/**
	 * Test list sends page, sort and with_filter, and parses the new fields.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestListSendsPageSortAndWithFilter() throws MailerLiteException, ParseException
	{
		http.respondWith(200, "{\"data\":[{\"id\":\"1\",\"name\":\"S\",\"filter\":[[{\"operator\":\"in_any\"}]],\"automations_using_segment_count\":2,\"created_at\":\"2024-05-01 12:43:29\"}],\"links\":{},\"meta\":{}}");
		
		SegmentsList list = this.getMailerLite().segments().retriever().page(2).limit(10).sort("-total").withFilter(true).get();
		
		Map<String, String> query = http.query();
		
		assertEquals("GET", http.method());
		assertEquals("/segments", http.path());
		assertEquals("2", query.get("page"));
		assertEquals("10", query.get("limit"));
		assertEquals("-total", query.get("sort"));
		assertEquals("1", query.get("with_filter"));
		assertEquals(2, (int) list.segments[0].automationsUsingSegmentCount);
		assertEquals(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2024-05-01 12:43:29"), list.segments[0].createdAt);
		assertEquals("in_any", list.segments[0].filter.getAsJsonArray().get(0).getAsJsonArray().get(0).getAsJsonObject().get("operator").getAsString());
	}
	
	/**
	 * Test update sends the filter.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestUpdateSendsFilter() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":{\"id\":\"1\",\"name\":\"New\",\"created_at\":\"2024-01-01 00:00:00\"}}");
		
		JsonArray filter = JsonParser.parseString("[[{\"operator\":\"in_any\",\"args\":[\"groups\",[\"5\"]]}]]").getAsJsonArray();
		
		this.getMailerLite().segments().update("1", "New", filter);
		
		JsonObject body = http.body();
		
		assertEquals("PUT", http.method());
		assertEquals("/segments/1", http.path());
		assertEquals("New", body.get("name").getAsString());
		assertEquals(filter, body.getAsJsonArray("filter"));
	}
	
	/**
	 * Test the name only update body is byte for byte what it always sent.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestUpdateNameBodyIsUnchanged() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":{\"id\":\"1\",\"name\":\"New\",\"created_at\":\"2024-01-01 00:00:00\"}}");
		
		this.getMailerLite().segments().update("1", "New");
		
		assertEquals("PUT", http.method());
		assertEquals("{\"name\":\"New\"}", http.rawBody());
	}
	
	/**
	 * Test segment subscribers sends limit and cursor.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestSegmentSubscribersSendsLimitAndCursor() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":[],\"links\":{\"next\":\"https://example.com?cursor=next\"},\"meta\":{\"path\":\"x\",\"per_page\":5,\"next_cursor\":\"next\",\"prev_cursor\":\"prev\"}}");
		
		SegmentSubscribersList list = this.getMailerLite().segments().subscribers("1")
			.limit(5)
			.cursor("abc")
			.statusFilter("active")
			.get();
		
		Map<String, String> query = http.query();
		
		assertEquals("GET", http.method());
		assertEquals("/segments/1/subscribers", http.path());
		assertEquals("5", query.get("limit"));
		assertFalse(query.containsKey("limig"));
		assertEquals("abc", query.get("cursor"));
		assertFalse(query.containsKey("after"));
		assertEquals("active", query.get("filter[status]"));
		assertEquals("next", list.meta.nextCursor);
		assertEquals("prev", list.meta.previousCursor);
		assertEquals(5, list.meta.perPage);
		assertEquals("https://example.com?cursor=next", list.links.next);
	}
	
	/**
	 * Test the old after setter still sends after.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestSegmentSubscribersAfterStillSendsAfter() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":[],\"meta\":{}}");
		
		this.getMailerLite().segments().subscribers("1").after("123").get();
		
		Map<String, String> query = http.query();
		
		assertEquals("GET", http.method());
		assertEquals("/segments/1/subscribers", http.path());
		assertEquals("123", query.get("after"));
		assertFalse(query.containsKey("cursor"));
	}
}
