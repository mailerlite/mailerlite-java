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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.susbcribers.ForgottenSubscriber;
import com.mailerlite.sdk.susbcribers.SingleSubscriber;
import com.mailerlite.sdk.susbcribers.SingleSubscriberImport;
import com.mailerlite.sdk.susbcribers.SubscriberActivityLogList;
import com.mailerlite.sdk.susbcribers.SubscriberImportList;

/**
 * The Class SubscribersRequestTest.
 */
public class SubscribersRequestTest extends MockTestBase {

	/**
	 * Test list sends the documented query params.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestListSendsDocumentedParams() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":[],\"links\":{},\"meta\":{\"next_cursor\":\"abc\"}}");
		
		this.getMailerLite().subscribers().retriever()
			.filter("status", "active")
			.limit(10)
			.page(2)
			.cursor("abc")
			.sort("-created_at")
			.include("groups")
			.startDate("2024-01-01")
			.endDate("2024-02-01")
			.withFields(true)
			.withGroups(false)
			.get();
		
		Map<String, String> query = http.query();
		
		assertEquals("GET", http.method());
		assertEquals("/subscribers", http.path());
		assertEquals("active", query.get("filter[status]"));
		assertEquals("10", query.get("limit"));
		assertEquals("2", query.get("page"));
		assertEquals("abc", query.get("cursor"));
		assertEquals("-created_at", query.get("sort"));
		assertEquals("groups", query.get("include"));
		assertEquals("2024-01-01", query.get("start_date"));
		assertEquals("2024-02-01", query.get("end_date"));
		assertEquals("1", query.get("with_fields"));
		assertEquals("0", query.get("with_groups"));
	}
	
	/**
	 * Test create sends the new body params.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestCreateSendsResubscribeDoubleOptinAndNotes() throws MailerLiteException
	{
		http.respondWith(201, "{\"data\":{\"id\":\"1\",\"email\":\"a@example.com\"}}");
		
		this.getMailerLite().subscribers().builder()
			.email("a@example.com")
			.resubscribe(true)
			.doubleOptin(false)
			.addNote("first note")
			.create();
		
		JsonObject body = http.body();
		
		assertEquals("POST", http.method());
		assertEquals("/subscribers", http.path());
		assertEquals("a@example.com", body.get("email").getAsString());
		assertTrue(body.get("resubscribe").getAsBoolean());
		assertFalse(body.get("double_optin").getAsBoolean());
		assertEquals("first note", body.getAsJsonArray("notes").get(0).getAsString());
	}
	
	/**
	 * Test create and update bodies are byte for byte what the builder always sent.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestBuilderBodyIsUnchanged() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":{\"id\":\"1\",\"email\":\"a@example.com\"}}");
		
		this.getMailerLite().subscribers().builder().email("a@example.com").addField("city", "Vilnius").addGroup("5").status("active").create();
		assertEquals("{\"email\":\"a@example.com\",\"fields\":{\"city\":\"Vilnius\"},\"groups\":[\"5\"],\"status\":\"active\"}", http.rawBody());
		
		this.getMailerLite().subscribers().builder().email("a@example.com").status("unsubscribed").update("1");
		assertEquals("PUT", http.method());
		assertEquals("{\"email\":\"a@example.com\",\"status\":\"unsubscribed\"}", http.rawBody());
	}
	
	/**
	 * Test get single parses the new response fields.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestGetSingleParsesNewFields() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":{\"id\":\"1\",\"email\":\"a@example.com\",\"deleted_at\":\"2024-01-02 03:04:05\",\"forget_at\":\"2024-02-02 03:04:05\","
				+ "\"groups\":[{\"id\":\"5\",\"name\":\"Group\",\"created_at\":\"2024-03-02 03:04:05\"}],"
				+ "\"location\":{\"id\":\"9\",\"title\":\"Vilnius\",\"country_name\":\"Lithuania\",\"continent_name\":\"Europe\",\"state_name\":null,\"time_zone\":\"Europe/Vilnius\"},"
				+ "\"timezone\":{\"id\":\"3\",\"name\":\"Europe/Vilnius\",\"name_for_humans\":\"Vilnius\",\"offset_name\":\"+02:00\",\"offset\":120}}}");
		
		SingleSubscriber subscriber = this.getMailerLite().subscribers().retriever().getSingle("1");
		
		assertEquals("GET", http.method());
		assertEquals("/subscribers/1", http.path());
		assertNotNull(subscriber.subscriber.deletedAt);
		assertNotNull(subscriber.subscriber.forgetAt);
		assertEquals("5", subscriber.subscriber.groups[0].id);
		assertNotNull(subscriber.subscriber.groups[0].createdAt);
		assertEquals("Lithuania", subscriber.subscriber.location.countryName);
		assertEquals(120, (int) subscriber.subscriber.timezone.offset);
	}
	
	/**
	 * Test forget.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestForget() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":{\"id\":\"1\",\"email\":\"a@example.com\"},\"message\":\"Subscriber data will be completely deleted and forgotten within 30 days.\"}");
		
		ForgottenSubscriber response = this.getMailerLite().subscribers().forget("1");
		
		assertEquals("POST", http.method());
		assertEquals("/subscribers/1/forget", http.path());
		assertEquals("1", response.subscriber.id);
		assertTrue(response.message.contains("30 days"));
	}
	
	/**
	 * Test activity log.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestActivityLog() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":["
				+ "{\"id\":\"1\",\"log_name\":\"added_to_group\",\"subject_id\":\"10\",\"subject_type\":\"subscribers\",\"properties\":{\"group_id\":\"1\"},\"created_at\":\"2024-05-01 12:43:29\",\"updated_at\":\"2024-05-01 12:43:29\"},"
				+ "{\"id\":\"2\",\"log_name\":\"activated\",\"subject_id\":\"10\",\"subject_type\":\"subscribers\",\"properties\":[],\"created_at\":\"2024-05-01 12:43:29\",\"updated_at\":\"2024-05-01 12:43:29\"}]}");
		
		SubscriberActivityLogList list = this.getMailerLite().subscribers().activityLog("10")
			.logName("added_to_group")
			.limit(5)
			.page(1)
			.get();
		
		Map<String, String> query = http.query();
		
		assertEquals("GET", http.method());
		assertEquals("/subscribers/10/activity-log", http.path());
		assertEquals("added_to_group", query.get("filter[log_name]"));
		assertEquals("5", query.get("limit"));
		assertEquals("1", query.get("page"));
		assertEquals(2, list.entries.length);
		assertEquals("1", list.entries[0].properties.getAsJsonObject().get("group_id").getAsString());
		assertTrue(list.entries[1].properties.isJsonArray());
		assertNotNull(list.entries[0].createdAt);
	}
	
	/**
	 * Test the import list parses its ISO 8601 dates.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestImportListParsesIsoDates() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":[{\"id\":\"7\",\"updated_at\":\"2023-11-02T13:43:10.000000Z\"}],\"links\":{},\"meta\":{}}");
		
		SubscriberImportList list = this.getMailerLite().subscribers().imports().get();
		
		assertEquals("GET", http.method());
		assertEquals("/subscribers/import", http.path());
		assertEquals(Instant.parse("2023-11-02T13:43:10Z"), list.imports[0].updatedAt.toInstant());
	}
	
	/**
	 * Test get import parses the new response fields.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestGetImportParsesNewFields() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":{\"id\":\"7\",\"account_id\":\"3\",\"updated_at\":\"2023-11-02T13:43:10.000000Z\",\"suspicious_format\":[{\"id\":\"1\",\"email\":\"x@example\"}],\"suspicious_format_count\":1,"
				+ "\"duplicate\":[],\"duplicate_count\":2,\"subscriber_limit\":[],\"subscriber_limit_count\":3,\"is_stopped_due_to_free_account_limit_exceeded\":false}}");
		
		SingleSubscriberImport response = this.getMailerLite().subscribers().imports().getSingle("7");
		
		assertEquals("GET", http.method());
		assertEquals("/subscribers/import/7", http.path());
		assertEquals("3", response.subscriberImport.accountId);
		assertEquals("x@example", response.subscriberImport.suspiciousFormat[0].email);
		assertEquals(1, response.subscriberImport.suspiciousFormatCount);
		assertEquals(2, response.subscriberImport.duplicateCount);
		assertEquals(3, response.subscriberImport.subscriberLimitCount);
		assertFalse(response.subscriberImport.isStoppedDueToFreeAccountLimitExceeded);
		assertEquals(Instant.parse("2023-11-02T13:43:10Z"), response.subscriberImport.updatedAt.toInstant());
	}
}
