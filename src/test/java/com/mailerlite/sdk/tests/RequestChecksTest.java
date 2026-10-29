/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import com.google.gson.Gson;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.emails.EditorContentAdapter;
import com.mailerlite.sdk.exceptions.EmptyEtagException;
import com.mailerlite.sdk.exceptions.EmptyIdException;
import com.mailerlite.sdk.exceptions.InvalidIdException;
import com.mailerlite.sdk.exceptions.InvalidIdempotencyKeyException;
import com.mailerlite.sdk.util.RequestChecks;

public class RequestChecksTest {

	public static final List<String> INVALID_IDS = Collections.unmodifiableList(Arrays.asList(
			"123/html", "123%2Fhtml", "123\n", "\u00b2", "\u0661\u0662\u0663", "12a", " 123", "-1", "1.5"));

	public static final List<String> INVALID_KEYS = Collections.unmodifiableList(Arrays.asList(
			"", repeat('k', 256), "key\n", "key\t", "cl\u00e9", "key\u007f", " key", "key "));

	public static String repeat(char c, int count)
	{
		char[] chars = new char[count];
		Arrays.fill(chars, c);

		return new String(chars);
	}

	@Test
	public void pathIdAcceptsAsciiDigits()
	{
		assertEquals("123", RequestChecks.pathId("123", "emailId"));
		assertEquals("0", RequestChecks.pathId("0", "emailId"));
	}

	@Test
	public void pathIdRejectsEmptyIds()
	{
		EmptyIdException empty = assertThrows(EmptyIdException.class, () -> RequestChecks.pathId("", "emailId"));
		assertTrue(empty.getMessage().contains("emailId"), empty.getMessage());

		assertThrows(EmptyIdException.class, () -> RequestChecks.pathId(null, "blockId"));
		assertTrue(empty instanceof IllegalArgumentException);
	}

	@TestFactory
	public List<DynamicTest> pathIdRejectsInvalidIds()
	{
		List<DynamicTest> tests = new ArrayList<DynamicTest>();

		for (String id : INVALID_IDS) {
			tests.add(dynamicTest("rejects " + id.replace("\n", "\\n"), () -> {
				InvalidIdException e = assertThrows(InvalidIdException.class, () -> RequestChecks.pathId(id, "versionId"));
				assertTrue(e.getMessage().contains("versionId"), e.getMessage());
				assertTrue(e instanceof IllegalArgumentException);
			}));
		}

		return tests;
	}

	@Test
	public void etagRejectsEmptyValues()
	{
		assertEquals("42", RequestChecks.etag("42"));
		assertThrows(EmptyEtagException.class, () -> RequestChecks.etag(""));
		EmptyEtagException e = assertThrows(EmptyEtagException.class, () -> RequestChecks.etag(null));
		assertTrue(e instanceof IllegalArgumentException);
	}

	@Test
	public void idempotencyKeyAcceptsPrintableAscii()
	{
		String longest = repeat('k', 255);

		assertEquals(longest, RequestChecks.idempotencyKey(longest));
		assertEquals("a", RequestChecks.idempotencyKey("a"));
		assertEquals("retry 1 ~!@#$%^&*()", RequestChecks.idempotencyKey("retry 1 ~!@#$%^&*()"));
	}

	@TestFactory
	public List<DynamicTest> idempotencyKeyRejectsInvalidKeys()
	{
		List<String> keys = new ArrayList<String>(INVALID_KEYS);
		keys.add(null);

		List<DynamicTest> tests = new ArrayList<DynamicTest>();

		for (String key : keys) {
			String name = key == null ? "null" : key.length() > 20 ? "length " + key.length() : key;
			tests.add(dynamicTest("rejects " + name.replace("\n", "\\n").replace("\t", "\\t"), () -> {
				InvalidIdempotencyKeyException e = assertThrows(InvalidIdempotencyKeyException.class,
						() -> RequestChecks.idempotencyKey(key));
				assertTrue(e instanceof IllegalArgumentException);
			}));
		}

		return tests;
	}

	public static class Holder {

		@SerializedName("editor_content")
		@JsonAdapter(EditorContentAdapter.class)
		public Map<String, Object> editorContent;

		@SerializedName("other")
		public String other;
	}

	@Test
	public void editorContentKeepsWholeNumbers()
	{
		String json = "{\"editor_content\":{\"attrs\":{\"linkId\":7,\"size\":24,\"ratio\":1.5},"
				+ "\"content\":[{\"text\":\"Hi\",\"level\":2}]}}";

		Holder holder = new Gson().fromJson(json, Holder.class);

		@SuppressWarnings("unchecked")
		Map<String, Object> attrs = (Map<String, Object>) holder.editorContent.get("attrs");
		assertEquals(Long.valueOf(7), attrs.get("linkId"));
		assertEquals(Long.valueOf(24), attrs.get("size"));
		assertEquals(Double.valueOf(1.5), attrs.get("ratio"));
		assertEquals(json, new Gson().toJson(holder));
	}

	@Test
	public void editorContentKeepsNullValues()
	{
		Map<String, Object> attrs = new LinkedHashMap<String, Object>();
		attrs.put("href", null);
		attrs.put("textAlign", "left");

		Map<String, Object> content = new LinkedHashMap<String, Object>();
		content.put("attrs", attrs);
		content.put("title", null);

		Holder holder = new Holder();
		holder.editorContent = content;

		String json = new Gson().toJson(holder);
		assertEquals("{\"editor_content\":{\"attrs\":{\"href\":null,\"textAlign\":\"left\"},\"title\":null}}", json);

		Holder read = new Gson().fromJson(json, Holder.class);
		assertTrue(read.editorContent.containsKey("title"));
		assertNull(read.editorContent.get("title"));
	}
}
