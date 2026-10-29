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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.emails.EmailBlock;
import com.mailerlite.sdk.emails.EmailBlockInput;
import com.mailerlite.sdk.emails.EmailBlockPosition;
import com.mailerlite.sdk.emails.EmailBlockPositions;
import com.mailerlite.sdk.emails.EmailBlockTypes;
import com.mailerlite.sdk.emails.EmailDocument;
import com.mailerlite.sdk.emails.EmailSettings;
import com.mailerlite.sdk.emails.EmailSocialLink;
import com.mailerlite.sdk.emails.Emails;
import com.mailerlite.sdk.emails.SingleEmailBlock;
import com.mailerlite.sdk.emails.SingleEmailDocument;
import com.mailerlite.sdk.emails.SingleEmailHtml;
import com.mailerlite.sdk.exceptions.EmptyEtagException;
import com.mailerlite.sdk.exceptions.EmptyIdException;
import com.mailerlite.sdk.exceptions.InvalidIdException;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.support.Fixtures;
import com.mailerlite.sdk.support.RecordingHttpClient;
import com.mailerlite.sdk.support.RecordingHttpClient.RecordedRequest;

/**
 * Tests the email endpoints with the recording HTTP client.
 */
public class EmailsTest {

	/** The base URL of the API. */
	static final String BASE = "https://connect.mailerlite.com/api";

	/** The fake HTTP client. */
	private RecordingHttpClient client;

	/** The email service. */
	private Emails emails;

	/**
	 * Sets the fake HTTP client.
	 */
	@BeforeEach
	public void setupEach()
	{
		client = RecordingHttpClient.install();

		MailerLite ml = new MailerLite();
		ml.setToken("test-token");
		emails = ml.emails();
	}

	/**
	 * Removes the fake HTTP client.
	 */
	@AfterEach
	public void afterEach()
	{
		RecordingHttpClient.uninstall();
	}

	/**
	 * Asserts the one request that the client got: the method, the full URL, all the headers and the raw body.
	 *
	 * @param client the fake HTTP client
	 * @param method the HTTP method
	 * @param path the path and the query string, after the base URL
	 * @param body the exact raw body, or null for a request without a body publisher
	 * @param idempotencyKey the expected Idempotency-Key header, or null for no header
	 * @return the request
	 */
	static RecordedRequest assertRequest(RecordingHttpClient client, String method, String path, String body,
			String idempotencyKey)
	{
		assertEquals(1, client.requests().size(), "number of requests");

		RecordedRequest request = client.lastRequest();
		assertEquals(method, request.method);
		assertEquals(BASE + path, request.uri.toString());

		Set<String> expectedHeaders = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
		expectedHeaders.addAll(Arrays.asList("Content-Type", "Accept", "Authorization"));

		if (idempotencyKey != null) {
			expectedHeaders.add("Idempotency-Key");
		}

		Set<String> actualHeaders = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
		actualHeaders.addAll(request.headers.map().keySet());
		assertEquals(expectedHeaders, actualHeaders);

		assertEquals("application/json", request.header("Content-Type"));
		assertEquals("application/json", request.header("Accept"));
		assertEquals("Bearer test-token", request.header("Authorization"));
		assertEquals(idempotencyKey, request.header("Idempotency-Key"));

		if (body == null) {
			assertFalse(request.hasBodyPublisher, "the request must have no body");
		} else {
			assertEquals(body, request.bodyString());
		}

		return request;
	}

	/**
	 * Asserts that each empty and each invalid ID throws the correct exception, and that no request is sent.
	 *
	 * @param client the fake HTTP client
	 * @param call makes the call for an ID
	 */
	static void assertRejectsIds(RecordingHttpClient client, Function<String, Executable> call)
	{
		for (String id : Arrays.asList("", null)) {
			assertThrows(EmptyIdException.class, call.apply(id), "ID " + id);
		}

		for (String id : RequestChecksTest.INVALID_IDS) {
			assertThrows(InvalidIdException.class, call.apply(id), "ID " + id);
		}

		assertTrue(client.requests().isEmpty(), "the SDK must send no request");
	}

	/**
	 * Gets a map value as a map.
	 *
	 * @param map the map
	 * @param key the key
	 * @return the value
	 */
	@SuppressWarnings("unchecked")
	static Map<String, Object> map(Map<String, Object> map, String key)
	{
		return (Map<String, Object>) map.get(key);
	}

	/**
	 * get sends GET /emails/{email} and decodes the email, the settings and the blocks.
	 *
	 * @throws Exception if the request fails
	 */
	@Test
	public void getDecodesTheEmail() throws Exception
	{
		client.respond(200, Fixtures.read("email.json"));

		SingleEmailDocument response = emails.get("123");

		assertRequest(client, "GET", "/emails/123", null, null);
		assertEquals(200, response.responseStatusCode);
		assertEquals("42", response.etag);

		EmailDocument email = response.email;
		assertEquals("123", email.id);
		assertEquals("builder_inline_email", email.type);
		assertEquals("campaigns", email.emailableType);
		assertEquals("456", email.emailableId);

		EmailSettings settings = email.settings;
		assertEquals("account", settings.brand.mode);
		assertNull(settings.brand.style);
		assertEquals("Inter, sans-serif", settings.brand.fontFamily);
		assertEquals("#ffffff", settings.brand.backgroundColor);
		assertEquals("#000000", settings.brand.textColor);
		assertEquals("#e5e7eb", settings.brand.borderColor);
		assertEquals("default", settings.layout.widthMode);
		assertEquals("center", settings.layout.alignment);
		assertEquals("#2cb191", settings.typography.link.color);
		assertEquals("Inter, sans-serif", settings.typography.heading.fontFamily);
		assertEquals("700", settings.typography.heading.fontWeight);
		assertEquals("150%", settings.typography.heading.lineHeight);
		assertEquals("0", settings.typography.heading.letterSpacing);
		assertEquals("32", settings.typography.heading.h1.size);
		assertEquals("24", settings.typography.heading.h2.size);
		assertEquals("20", settings.typography.heading.h3.size);
		assertEquals("#000000", settings.typography.heading.h3.color);
		assertEquals("400", settings.typography.paragraph.fontWeight);
		assertEquals("16", settings.typography.paragraph.p1.size);
		assertEquals("Inter, sans-serif", settings.button.fontFamily);
		assertEquals("600", settings.button.fontWeight);
		assertEquals("0", settings.button.letterSpacing);
		assertEquals("#2cb191", settings.button.backgroundColor);
		assertEquals("#ffffff", settings.button.textColor);
		assertEquals("4", settings.button.borderRadius);
		assertEquals(Boolean.FALSE, settings.footer.showPreferences);
		assertEquals("Unsubscribe", settings.footer.unsubscribeText);
		assertEquals("Update your preferences", settings.footer.preferencesText);

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> companyName = (List<Map<String, Object>>) settings.footer.companyName.editorContent.get("content");
		assertEquals("Acme", companyName.get(0).get("text"));

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> address = (List<Map<String, Object>>) settings.footer.address.editorContent.get("content");
		assertEquals("1 Main Street", address.get(0).get("text"));

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> infoText = (List<Map<String, Object>>) settings.footer.infoText.editorContent.get("content");
		assertEquals("You signed up on our website.", infoText.get(0).get("text"));

		assertEquals(1, settings.footer.socialLinks.size());
		EmailSocialLink link = settings.footer.socialLinks.get(0);
		assertEquals("https://example.com", link.url);
		assertEquals("website", link.type);
		assertEquals("Website", link.label);
		assertEquals(Long.valueOf(7), link.linkId);

		assertEquals(2, email.blocks.length);
		EmailBlock paragraph = email.blocks[0];
		assertEquals("1", paragraph.id);
		assertEquals("paragraph", paragraph.type);
		assertEquals("left", map(paragraph.editorContent, "attrs").get("textAlign"));
		assertNull(paragraph.children);
		assertNull(paragraph.placement);

		EmailBlock list = email.blocks[1];
		assertEquals("2", list.id);
		assertEquals("bulleted_list", list.type);
		assertEquals(Collections.emptyMap(), list.editorContent);
		assertEquals(1, list.children.length);
		assertEquals("3", list.children[0].id);
		assertEquals("paragraph", list.children[0].type);
	}

	/**
	 * get checks the email ID and sends no request for an invalid ID.
	 */
	@Test
	public void getRejectsInvalidIds()
	{
		assertRejectsIds(client, id -> () -> emails.get(id));
	}

	/**
	 * getHtml sends GET /emails/{email}/html and decodes the HTML.
	 *
	 * @throws Exception if the request fails
	 */
	@Test
	public void getHtmlDecodesTheHtml() throws Exception
	{
		client.respond(200, Fixtures.read("email_html.json"));

		SingleEmailHtml response = emails.getHtml("123");

		assertRequest(client, "GET", "/emails/123/html", null, null);
		assertEquals("<!doctype html><html><body><p>Hello</p></body></html>", response.preview.html);
		assertEquals("42", response.etag);
	}

	/**
	 * getHtml throws a MailerLiteException with the status for a temporary 503 with a body that is not JSON.
	 */
	@Test
	public void getHtmlServiceUnavailable()
	{
		client.respond(503, "<html><body>Service Unavailable</body></html>");

		MailerLiteException e = assertThrows(MailerLiteException.class, () -> emails.getHtml("123"));

		assertEquals(503, e.code);
		assertEquals("<html><body>Service Unavailable</body></html>", e.responseBody);
	}

	/**
	 * getHtml checks the email ID and sends no request for an invalid ID.
	 */
	@Test
	public void getHtmlRejectsInvalidIds()
	{
		assertRejectsIds(client, id -> () -> emails.getHtml(id));
	}


	/**
	 * getBlock sends GET /emails/{email}/blocks/{block} and decodes the block and its placement.
	 *
	 * @throws Exception if the request fails
	 */
	@Test
	public void getBlockDecodesTheBlock() throws Exception
	{
		client.respond(200, Fixtures.read("email_block.json"));

		SingleEmailBlock response = emails.getBlock("123", "57");

		assertRequest(client, "GET", "/emails/123/blocks/57", null, null);
		assertEquals("43", response.etag);
		assertEquals("57", response.block.id);
		assertEquals("paragraph", response.block.type);
		assertEquals("left", map(response.block.editorContent, "attrs").get("textAlign"));
		assertEquals("56", response.block.placement.after);
		assertNull(response.block.placement.before);
		assertNull(response.block.children);
	}

	/**
	 * getBlock checks both IDs and sends no request for an invalid ID.
	 */
	@Test
	public void getBlockRejectsInvalidIds()
	{
		assertRejectsIds(client, id -> () -> emails.getBlock(id, "57"));
		assertRejectsIds(client, id -> () -> emails.getBlock("123", id));
	}


	/**
	 * Makes the editor content of a paragraph: {"attrs":{"textAlign":align},"content":[{"type":"text","text":text}]}.
	 *
	 * @param align the text alignment
	 * @param text the text
	 * @return the editor content
	 */
	static Map<String, Object> paragraph(String align, String text)
	{
		Map<String, Object> attrs = new LinkedHashMap<String, Object>();
		attrs.put("textAlign", align);

		Map<String, Object> node = new LinkedHashMap<String, Object>();
		node.put("type", "text");
		node.put("text", text);

		Map<String, Object> content = new LinkedHashMap<String, Object>();
		content.put("attrs", attrs);
		content.put("content", Collections.singletonList(node));

		return content;
	}

	/**
	 * Asserts that each write rejects an empty or null etag, and sends no request.
	 *
	 * @param client the fake HTTP client
	 * @param call makes the call for an etag
	 */
	static void assertRejectsEtags(RecordingHttpClient client, Function<String, Executable> call)
	{
		assertThrows(EmptyEtagException.class, call.apply(""));
		assertThrows(EmptyEtagException.class, call.apply(null));
		assertTrue(client.requests().isEmpty(), "the SDK must send no request");
	}

	/**
	 * Asserts the placement rules of a method that sends a placement.
	 * <p>
	 * The SDK checks blockId only for "before" and "after". For "start" and "end", it does not check blockId and
	 * does not send it. For another position, it checks blockId if it is set, and sends it.
	 * The SDK does not change the object of the caller.
	 *
	 * @param client the fake HTTP client
	 * @param response the response body to queue for each request
	 * @param call makes the call for a placement
	 * @param expectedBody makes the expected raw body from the JSON of the placement
	 * @throws Throwable if a call fails
	 */
	static void assertPlacementRules(RecordingHttpClient client, String response, Function<EmailBlockPosition, Executable> call,
			Function<String, String> expectedBody) throws Throwable
	{
		Object[][] sent = {
			{EmailBlockPositions.END, "", "{\"position\":\"end\"}"},
			{EmailBlockPositions.START, "1/2", "{\"position\":\"start\"}"},
			{EmailBlockPositions.AFTER, "57", "{\"position\":\"after\",\"block_id\":\"57\"}"},
			{EmailBlockPositions.BEFORE, "1", "{\"position\":\"before\",\"block_id\":\"1\"}"},
			{"middle", "5", "{\"position\":\"middle\",\"block_id\":\"5\"}"},
			{"middle", null, "{\"position\":\"middle\"}"},
		};

		for (Object[] item : sent) {

			client.requests().clear();
			client.respond(200, response);

			EmailBlockPosition placement = new EmailBlockPosition((String) item[0], (String) item[1]);
			call.apply(placement).execute();

			assertEquals(expectedBody.apply((String) item[2]), client.lastRequest().bodyString(), item[0] + " " + item[1]);
			assertEquals(item[1], placement.blockId, "the SDK must not change the placement of the caller");
		}

		client.requests().clear();

		Object[][] rejected = {
			{EmailBlockPositions.AFTER, null, EmptyIdException.class},
			{EmailBlockPositions.AFTER, "", EmptyIdException.class},
			{EmailBlockPositions.BEFORE, null, EmptyIdException.class},
			{EmailBlockPositions.BEFORE, "1/2", InvalidIdException.class},
			{EmailBlockPositions.AFTER, "\u00b2", InvalidIdException.class},
			{"middle", "1/2", InvalidIdException.class},
			{"", "5", IllegalArgumentException.class},
			{null, "5", IllegalArgumentException.class},
		};

		for (Object[] item : rejected) {

			@SuppressWarnings("unchecked")
			Class<? extends Throwable> type = (Class<? extends Throwable>) item[2];
			assertThrows(type, call.apply(new EmailBlockPosition((String) item[0], (String) item[1])), item[0] + " " + item[1]);
		}

		assertTrue(client.requests().isEmpty(), "the SDK must send no request");
	}

	/**
	 * createBlock sends POST /emails/{email}/blocks with the block, the placement and the etag.
	 *
	 * @throws Exception if the request fails
	 */
	@Test
	public void createBlockSendsTheBlock() throws Exception
	{
		client.respond(201, Fixtures.read("email_block_created.json"));

		EmailBlockInput block = new EmailBlockInput(EmailBlockTypes.PARAGRAPH, paragraph("center", "Hi"));
		block.placement = new EmailBlockPosition(EmailBlockPositions.AFTER, "57");

		SingleEmailBlock response = emails.createBlock("123", "42", block);

		assertRequest(client, "POST", "/emails/123/blocks",
				"{\"type\":\"paragraph\",\"editor_content\":{\"attrs\":{\"textAlign\":\"center\"},"
				+ "\"content\":[{\"type\":\"text\",\"text\":\"Hi\"}]},"
				+ "\"placement\":{\"position\":\"after\",\"block_id\":\"57\"},\"etag\":\"42\"}", null);
		assertEquals(201, response.responseStatusCode);
		assertEquals("43", response.etag);
		assertEquals("58", response.block.id);
		assertEquals("center", map(response.block.editorContent, "attrs").get("textAlign"));
		assertEquals("57", response.block.placement.after);
		assertNull(response.block.placement.before);
	}

	/**
	 * createBlock sends an empty editor content and no placement when the caller sets none.
	 * A null value inside the editor content is sent as null.
	 *
	 * @throws Exception if the request fails
	 */
	@Test
	public void createBlockWithoutPlacement() throws Exception
	{
		client.respond(201, Fixtures.read("email_block_created.json")).respond(201, Fixtures.read("email_block_created.json"));

		emails.createBlock("123", "42", new EmailBlockInput(EmailBlockTypes.DIVIDER, new LinkedHashMap<String, Object>()));

		assertEquals("{\"type\":\"divider\",\"editor_content\":{},\"etag\":\"42\"}", client.lastRequest().bodyString());

		Map<String, Object> attrs = new LinkedHashMap<String, Object>();
		attrs.put("url", null);
		Map<String, Object> content = new LinkedHashMap<String, Object>();
		content.put("attrs", attrs);

		emails.createBlock("123", "42", new EmailBlockInput(EmailBlockTypes.BUTTON, content));

		assertEquals("{\"type\":\"button\",\"editor_content\":{\"attrs\":{\"url\":null}},\"etag\":\"42\"}",
				client.lastRequest().bodyString());
	}

	/**
	 * createBlock checks the placement: blockId only for "before" and "after".
	 *
	 * @throws Throwable if a call fails
	 */
	@Test
	public void createBlockPlacementRules() throws Throwable
	{
		assertPlacementRules(client, Fixtures.read("email_block_created.json"), placement -> () -> {
			EmailBlockInput block = new EmailBlockInput(EmailBlockTypes.DIVIDER, new LinkedHashMap<String, Object>());
			block.placement = placement;
			emails.createBlock("123", "42", block);
		}, json -> "{\"type\":\"divider\",\"editor_content\":{},\"placement\":" + json + ",\"etag\":\"42\"}");
	}

	/**
	 * createBlock checks the IDs, the etag and the required values, and sends no request when a check fails.
	 */
	@Test
	public void createBlockRejectsInvalidInput()
	{
		EmailBlockInput divider = new EmailBlockInput(EmailBlockTypes.DIVIDER, new LinkedHashMap<String, Object>());

		assertRejectsIds(client, id -> () -> emails.createBlock(id, "42", divider));
		assertRejectsEtags(client, etag -> () -> emails.createBlock("123", etag, divider));

		NullPointerException noBlock = assertThrows(NullPointerException.class, () -> emails.createBlock("123", "42", null));
		assertTrue(noBlock.getMessage().contains("block"), noBlock.getMessage());

		assertThrows(IllegalArgumentException.class,
				() -> emails.createBlock("123", "42", new EmailBlockInput("", new LinkedHashMap<String, Object>())));
		assertThrows(IllegalArgumentException.class,
				() -> emails.createBlock("123", "42", new EmailBlockInput(null, new LinkedHashMap<String, Object>())));

		NullPointerException noContent = assertThrows(NullPointerException.class,
				() -> emails.createBlock("123", "42", new EmailBlockInput(EmailBlockTypes.DIVIDER, null)));
		assertTrue(noContent.getMessage().contains("editorContent"), noContent.getMessage());

		assertTrue(client.requests().isEmpty(), "the SDK must send no request");
	}

}
