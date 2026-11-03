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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.groups.GroupImportBuilder;
import com.mailerlite.sdk.groups.GroupSubscriberImport;
import com.mailerlite.sdk.groups.GroupSubscribersList;
import com.mailerlite.sdk.groups.GroupsList;

/**
 * The Class GroupsRequestTest.
 */
public class GroupsRequestTest extends MockTestBase {

	/**
	 * Test list sends page.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestListSendsPage() throws MailerLiteException, ParseException
	{
		http.respondWith(200, "{\"data\":[{\"id\":\"1\",\"name\":\"G\",\"created_at\":\"2024-05-01 12:43:29\"}],\"links\":{},\"meta\":{}}");
		
		GroupsList list = this.getMailerLite().groups().retriever().page(3).limit(25).nameFilter("news").sort("-name").get();
		
		Map<String, String> query = http.query();
		
		assertEquals("GET", http.method());
		assertEquals("/groups", http.path());
		assertEquals("3", query.get("page"));
		assertEquals("25", query.get("limit"));
		assertEquals("news", query.get("filter[name]"));
		assertEquals("-name", query.get("sort"));
		assertEquals(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2024-05-01 12:43:29"), list.groups[0].createdAt);
	}
	
	/**
	 * Test group subscribers sends include and cursor, and parses the cursor meta.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestGroupSubscribersSendsIncludeAndCursor() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":[],\"links\":{},\"meta\":{\"path\":\"x\",\"per_page\":25,\"next_cursor\":\"next\",\"prev_cursor\":null}}");
		
		GroupSubscribersList list = this.getMailerLite().groups().subscribers("5")
			.statusFilter("active")
			.limit(25)
			.cursor("abc")
			.include("groups")
			.get();
		
		Map<String, String> query = http.query();
		
		assertEquals("GET", http.method());
		assertEquals("/groups/5/subscribers", http.path());
		assertEquals("active", query.get("filter[status]"));
		assertEquals("25", query.get("limit"));
		assertEquals("abc", query.get("cursor"));
		assertEquals("groups", query.get("include"));
		assertEquals("next", list.meta.nextCursor);
	}
	
	/**
	 * Test import subscribers.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestImportSubscribers() throws MailerLiteException
	{
		http.respondWith(200, "{\"import_progress_url\":\"https://connect.mailerlite.com/api/subscribers/import/9\",\"data\":{\"id\":\"9\",\"total\":2}}");
		
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("city", "Vilnius");
		
		GroupSubscriberImport response = this.getMailerLite().groups().importBuilder("5")
			.addSubscriber("a@example.com")
			.addSubscriber("b@example.com", "Bee", fields)
			.resubscribe(true)
			.triggerAutomations(false)
			.start();
		
		JsonObject body = http.body();
		JsonObject second = body.getAsJsonArray("subscribers").get(1).getAsJsonObject();
		
		assertEquals("POST", http.method());
		assertEquals("/groups/5/import-subscribers", http.path());
		assertTrue(body.get("resubscribe").getAsBoolean());
		assertFalse(body.get("trigger_automations").getAsBoolean());
		assertEquals("a@example.com", body.getAsJsonArray("subscribers").get(0).getAsJsonObject().get("email").getAsString());
		assertEquals("b@example.com", second.get("email").getAsString());
		assertEquals("Bee", second.get("name").getAsString());
		assertEquals("Vilnius", second.getAsJsonObject("fields").get("city").getAsString());
		assertEquals("https://connect.mailerlite.com/api/subscribers/import/9", response.importProgressUrl);
		assertEquals("9", response.subscriberImport.id);
	}
	
	/**
	 * Test clear empties the import body.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestImportBuilderClear() throws MailerLiteException
	{
		http.respondWith(200, "{\"import_progress_url\":\"https://connect.mailerlite.com/api/subscribers/import/9\"}");
		
		GroupImportBuilder builder = this.getMailerLite().groups().importBuilder("5").addSubscriber("a@example.com").resubscribe(true);
		builder.clear();
		builder.addSubscriber("b@example.com").start();
		
		JsonObject body = http.body();
		
		assertEquals(1, body.getAsJsonArray("subscribers").size());
		assertEquals("b@example.com", body.getAsJsonArray("subscribers").get(0).getAsJsonObject().get("email").getAsString());
		assertFalse(body.has("resubscribe"));
	}
}
