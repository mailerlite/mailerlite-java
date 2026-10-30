/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.fields.FieldList;

/**
 * The Class FieldsRequestTest.
 */
public class FieldsRequestTest extends MockTestBase {

	/**
	 * Test list sends page and parses is_default.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestListSendsPage() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":[{\"id\":\"1\",\"name\":\"Name\",\"key\":\"name\",\"type\":\"text\",\"is_default\":true}],\"links\":{},\"meta\":{}}");
		
		FieldList list = this.getMailerLite().fields().retriever()
			.page(2)
			.limit(10)
			.filter("keyword", "nam")
			.filter("type", "text")
			.sort("name")
			.get();
		
		Map<String, String> query = http.query();
		
		assertEquals("GET", http.method());
		assertEquals("/fields", http.path());
		assertEquals("2", query.get("page"));
		assertEquals("10", query.get("limit"));
		assertEquals("nam", query.get("filter[keyword]"));
		assertEquals("text", query.get("filter[type]"));
		assertEquals("name", query.get("sort"));
		assertTrue(list.fields[0].isDefault);
	}
}
