/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
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
import com.mailerlite.sdk.emails.EmailEtagResponse;
import com.mailerlite.sdk.emails.EmailBrandModes;
import com.mailerlite.sdk.emails.EmailBrandSettings;
import com.mailerlite.sdk.emails.EmailEditorText;
import com.mailerlite.sdk.emails.EmailFooterSettings;
import com.mailerlite.sdk.emails.EmailParagraphStyle;
import com.mailerlite.sdk.emails.EmailSettings;
import com.mailerlite.sdk.emails.EmailTextStyle;
import com.mailerlite.sdk.emails.EmailTypographySettings;
import com.mailerlite.sdk.emails.SingleEmailSettings;
import com.mailerlite.sdk.emails.SingleEmailTemplate;
import com.mailerlite.sdk.emails.SingleEmailVersion;
import com.mailerlite.sdk.emails.EmailSocialLink;
import com.mailerlite.sdk.emails.EmailValidation;
import com.mailerlite.sdk.emails.EmailVersionsList;
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


	/**
	 * updateBlock sends PATCH /emails/{email}/blocks/{block} with the editor content and the etag.
	 *
	 * @throws Exception if the request fails
	 */
	@Test
	public void updateBlockSendsTheEditorContent() throws Exception
	{
		client.respond(200, Fixtures.read("email_block_updated.json"));

		SingleEmailBlock response = emails.updateBlock("123", "57", "43", paragraph("center", "Hello again"));

		assertRequest(client, "PATCH", "/emails/123/blocks/57",
				"{\"editor_content\":{\"attrs\":{\"textAlign\":\"center\"},"
				+ "\"content\":[{\"type\":\"text\",\"text\":\"Hello again\"}]},\"etag\":\"43\"}", null);
		assertEquals("44", response.etag);
		assertEquals("57", response.block.id);
		assertEquals("56", response.block.placement.after);
		assertEquals("58", response.block.placement.before);
	}

	/**
	 * A block that the SDK read is sent back with the same numbers and the same null values.
	 *
	 * @throws Exception if the request fails
	 */
	@Test
	public void updateBlockSendsBackTheEditorContentExactly() throws Exception
	{
		String editorContent = "{\"attrs\":{\"url\":\"https://example.com\",\"linkId\":7,\"size\":24,\"ratio\":1.5,\"title\":null}}";
		client.respond(200, "{\"data\":{\"id\":\"57\",\"type\":\"button\",\"editor_content\":" + editorContent + "},\"etag\":\"43\"}")
				.respond(200, Fixtures.read("email_block_updated.json"));

		SingleEmailBlock read = emails.getBlock("123", "57");
		assertEquals(Long.valueOf(7), map(read.block.editorContent, "attrs").get("linkId"));

		emails.updateBlock("123", "57", read.etag, read.block.editorContent);

		assertEquals("{\"editor_content\":" + editorContent + ",\"etag\":\"43\"}", client.lastRequest().bodyString());
	}

	/**
	 * updateBlock checks the IDs, the etag and the editor content, and sends no request when a check fails.
	 */
	@Test
	public void updateBlockRejectsInvalidInput()
	{
		Map<String, Object> content = paragraph("left", "Hi");

		assertRejectsIds(client, id -> () -> emails.updateBlock(id, "57", "43", content));
		assertRejectsIds(client, id -> () -> emails.updateBlock("123", id, "43", content));
		assertRejectsEtags(client, etag -> () -> emails.updateBlock("123", "57", etag, content));

		NullPointerException e = assertThrows(NullPointerException.class, () -> emails.updateBlock("123", "57", "43", null));
		assertTrue(e.getMessage().contains("editorContent"), e.getMessage());
		assertTrue(client.requests().isEmpty(), "the SDK must send no request");
	}


	/**
	 * deleteBlock sends DELETE /emails/{email}/blocks/{block} with the etag in the body, and returns the new etag.
	 *
	 * @throws Exception if the request fails
	 */
	@Test
	public void deleteBlockSendsTheEtagInTheBody() throws Exception
	{
		client.respond(200, Fixtures.read("email_etag.json"));

		EmailEtagResponse response = emails.deleteBlock("123", "57", "44");

		assertRequest(client, "DELETE", "/emails/123/blocks/57", "{\"etag\":\"44\"}", null);
		assertEquals(200, response.responseStatusCode);
		assertEquals("45", response.etag);
	}

	/**
	 * deleteBlock checks the IDs and the etag, and sends no request when a check fails.
	 */
	@Test
	public void deleteBlockRejectsInvalidInput()
	{
		assertRejectsIds(client, id -> () -> emails.deleteBlock(id, "57", "44"));
		assertRejectsIds(client, id -> () -> emails.deleteBlock("123", id, "44"));
		assertRejectsEtags(client, etag -> () -> emails.deleteBlock("123", "57", etag));
	}


	/**
	 * moveBlock sends POST /emails/{email}/blocks/{block}/move with the placement and the etag.
	 *
	 * @throws Exception if the request fails
	 */
	@Test
	public void moveBlockSendsThePlacement() throws Exception
	{
		client.respond(200, Fixtures.read("email_block_moved.json"));

		SingleEmailBlock response = emails.moveBlock("123", "57", "45", new EmailBlockPosition(EmailBlockPositions.BEFORE, "1"));

		assertRequest(client, "POST", "/emails/123/blocks/57/move",
				"{\"placement\":{\"position\":\"before\",\"block_id\":\"1\"},\"etag\":\"45\"}", null);
		assertEquals("46", response.etag);
		assertEquals("57", response.block.id);
		assertNull(response.block.placement.after);
		assertEquals("1", response.block.placement.before);
	}

	/**
	 * moveBlock checks the placement: blockId only for "before" and "after".
	 *
	 * @throws Throwable if a call fails
	 */
	@Test
	public void moveBlockPlacementRules() throws Throwable
	{
		assertPlacementRules(client, Fixtures.read("email_block_moved.json"),
				placement -> () -> emails.moveBlock("123", "57", "45", placement),
				json -> "{\"placement\":" + json + ",\"etag\":\"45\"}");
	}

	/**
	 * moveBlock checks the IDs, the etag and the placement, and sends no request when a check fails.
	 */
	@Test
	public void moveBlockRejectsInvalidInput()
	{
		EmailBlockPosition start = new EmailBlockPosition(EmailBlockPositions.START);

		assertRejectsIds(client, id -> () -> emails.moveBlock(id, "57", "45", start));
		assertRejectsIds(client, id -> () -> emails.moveBlock("123", id, "45", start));
		assertRejectsEtags(client, etag -> () -> emails.moveBlock("123", "57", etag, start));

		NullPointerException e = assertThrows(NullPointerException.class, () -> emails.moveBlock("123", "57", "45", null));
		assertTrue(e.getMessage().contains("placement"), e.getMessage());
		assertTrue(client.requests().isEmpty(), "the SDK must send no request");
	}


	/**
	 * validateBlock sends POST /emails/blocks/validate with the block and no etag, and decodes a valid result.
	 *
	 * @throws Exception if the request fails
	 */
	@Test
	public void validateBlockValid() throws Exception
	{
		client.respond(200, Fixtures.read("email_block_validation_valid.json"));

		EmailBlockInput block = new EmailBlockInput(EmailBlockTypes.PARAGRAPH, paragraph("left", "Hi"));
		block.placement = new EmailBlockPosition(EmailBlockPositions.END);

		EmailValidation response = emails.validateBlock(block);

		assertRequest(client, "POST", "/emails/blocks/validate",
				"{\"type\":\"paragraph\",\"editor_content\":{\"attrs\":{\"textAlign\":\"left\"},"
				+ "\"content\":[{\"type\":\"text\",\"text\":\"Hi\"}]},\"placement\":{\"position\":\"end\"}}", null);
		assertTrue(response.valid);
		assertTrue(response.errors.isEmpty());
		assertEquals("2.73.2", response.schema.version);
		assertEquals("0f3a9c", response.schema.hash);
		assertNull(response.rejected);
	}

	/**
	 * validateBlock returns an invalid result as a success, with the errors for each field.
	 *
	 * @throws Exception if the request fails
	 */
	@Test
	public void validateBlockInvalid() throws Exception
	{
		client.respond(200, Fixtures.read("email_block_validation_invalid.json"));

		EmailValidation response = emails.validateBlock(new EmailBlockInput("table", new LinkedHashMap<String, Object>()));

		assertEquals("{\"type\":\"table\",\"editor_content\":{}}", client.lastRequest().bodyString());
		assertEquals(200, response.responseStatusCode);
		assertFalse(response.valid);
		assertArrayEquals(new String[] {"The selected type is invalid."}, response.errors.get("type"));
	}

	/**
	 * validateBlock checks the placement: blockId only for "before" and "after".
	 *
	 * @throws Throwable if a call fails
	 */
	@Test
	public void validateBlockPlacementRules() throws Throwable
	{
		assertPlacementRules(client, Fixtures.read("email_block_validation_valid.json"), placement -> () -> {
			EmailBlockInput block = new EmailBlockInput(EmailBlockTypes.DIVIDER, new LinkedHashMap<String, Object>());
			block.placement = placement;
			emails.validateBlock(block);
		}, json -> "{\"type\":\"divider\",\"editor_content\":{},\"placement\":" + json + "}");
	}

	/**
	 * validateBlock checks the required values, and sends no request when a check fails.
	 */
	@Test
	public void validateBlockRejectsInvalidInput()
	{
		assertThrows(NullPointerException.class, () -> emails.validateBlock(null));
		assertThrows(IllegalArgumentException.class,
				() -> emails.validateBlock(new EmailBlockInput("", new LinkedHashMap<String, Object>())));
		assertThrows(NullPointerException.class, () -> emails.validateBlock(new EmailBlockInput(EmailBlockTypes.DIVIDER, null)));
		assertTrue(client.requests().isEmpty(), "the SDK must send no request");
	}


	/**
	 * Makes settings that change the brand mode, the background color and the paragraph size.
	 *
	 * @return the settings
	 */
	static EmailSettings brandAndParagraphSettings()
	{
		EmailSettings settings = new EmailSettings();
		settings.brand = new EmailBrandSettings();
		settings.brand.mode = EmailBrandModes.CUSTOM;
		settings.brand.backgroundColor = "#f4f4f4";
		settings.typography = new EmailTypographySettings();
		settings.typography.paragraph = new EmailParagraphStyle();
		settings.typography.paragraph.p1 = new EmailTextStyle();
		settings.typography.paragraph.p1.size = "18";

		return settings;
	}

	/**
	 * updateSettings sends PATCH /emails/{email}/settings with only the set fields, and the etag at the top level.
	 *
	 * @throws Exception if the request fails
	 */
	@Test
	public void updateSettingsSendsOnlyTheSetFields() throws Exception
	{
		client.respond(200, Fixtures.read("email_settings.json"));

		SingleEmailSettings response = emails.updateSettings("123", "46", brandAndParagraphSettings());

		assertRequest(client, "PATCH", "/emails/123/settings",
				"{\"brand\":{\"mode\":\"custom\",\"background_color\":\"#f4f4f4\"},"
				+ "\"typography\":{\"paragraph\":{\"p1\":{\"size\":\"18\"}}},\"etag\":\"46\"}", null);
		assertEquals("47", response.etag);
		assertEquals("custom", response.settings.brand.mode);
		assertEquals("#f4f4f4", response.settings.brand.backgroundColor);
		assertEquals("16", response.settings.typography.paragraph.p1.size);
		assertEquals(Long.valueOf(7), response.settings.footer.socialLinks.get(0).linkId);
		assertEquals("Some attributes were rejected.", response.rejected.message);
		assertArrayEquals(new String[] {"footer.social_links[0].url"}, response.rejected.keys);
	}

	/**
	 * An empty string unsets a value, an empty list removes all the social links, an empty editor content unsets a
	 * footer text, and a null inside an editor content is kept.
	 *
	 * @throws Exception if the request fails
	 */
	@Test
	public void updateSettingsSendsEmptyValues() throws Exception
	{
		client.respond(200, Fixtures.read("email_settings.json"));

		Map<String, Object> companyName = new LinkedHashMap<String, Object>();
		companyName.put("content", Collections.emptyList());
		companyName.put("attrs", null);

		EmailSettings settings = new EmailSettings();
		settings.brand = new EmailBrandSettings();
		settings.brand.backgroundColor = "";
		settings.footer = new EmailFooterSettings();
		settings.footer.showPreferences = false;
		settings.footer.infoText = new EmailEditorText();
		settings.footer.infoText.editorContent = new LinkedHashMap<String, Object>();
		settings.footer.companyName = new EmailEditorText();
		settings.footer.companyName.editorContent = companyName;
		settings.footer.socialLinks = new ArrayList<EmailSocialLink>();

		emails.updateSettings("123", "46", settings);

		assertEquals("{\"brand\":{\"background_color\":\"\"},\"footer\":{\"show_preferences\":false,"
				+ "\"company_name\":{\"editor_content\":{\"content\":[],\"attrs\":null}},"
				+ "\"info_text\":{\"editor_content\":{}},\"social_links\":[]},\"etag\":\"46\"}",
				client.lastRequest().bodyString());
	}

	/**
	 * A social link keeps its link ID as a whole number.
	 *
	 * @throws Exception if the request fails
	 */
	@Test
	public void updateSettingsKeepsTheLinkId() throws Exception
	{
		client.respond(200, Fixtures.read("email.json")).respond(200, Fixtures.read("email_settings.json"));

		SingleEmailDocument email = emails.get("123");

		EmailSettings settings = new EmailSettings();
		settings.footer = new EmailFooterSettings();
		settings.footer.socialLinks = email.email.settings.footer.socialLinks;

		emails.updateSettings("123", email.etag, settings);

		assertEquals("{\"footer\":{\"social_links\":[{\"url\":\"https://example.com\",\"type\":\"website\","
				+ "\"label\":\"Website\",\"link_id\":7}]},\"etag\":\"42\"}", client.lastRequest().bodyString());
	}

	/**
	 * updateSettings checks the ID, the etag and the settings, and sends no request when a check fails.
	 */
	@Test
	public void updateSettingsRejectsInvalidInput()
	{
		EmailSettings settings = brandAndParagraphSettings();

		assertRejectsIds(client, id -> () -> emails.updateSettings(id, "46", settings));
		assertRejectsEtags(client, etag -> () -> emails.updateSettings("123", etag, settings));

		NullPointerException e = assertThrows(NullPointerException.class, () -> emails.updateSettings("123", "46", null));
		assertTrue(e.getMessage().contains("settings"), e.getMessage());
		assertTrue(client.requests().isEmpty(), "the SDK must send no request");
	}


	/**
	 * validateSettings sends POST /emails/{email}/settings/validate with the settings and no etag.
	 *
	 * @throws Exception if the request fails
	 */
	@Test
	public void validateSettingsSendsTheSettingsWithoutEtag() throws Exception
	{
		client.respond(200, Fixtures.read("email_settings_validation.json"));

		EmailValidation response = emails.validateSettings("123", brandAndParagraphSettings());

		assertRequest(client, "POST", "/emails/123/settings/validate",
				"{\"brand\":{\"mode\":\"custom\",\"background_color\":\"#f4f4f4\"},"
				+ "\"typography\":{\"paragraph\":{\"p1\":{\"size\":\"18\"}}}}", null);
		assertFalse(response.valid);
		assertArrayEquals(new String[] {"The layout.width field cannot be changed."}, response.errors.get("layout.width"));
		assertEquals("2.73.2", response.schema.version);
		assertEquals("Some attributes were rejected.", response.rejected.message);
		assertArrayEquals(new String[] {"footer.social_links[0].url"}, response.rejected.keys);
	}

	/**
	 * validateSettings checks the ID and the settings, and sends no request when a check fails.
	 */
	@Test
	public void validateSettingsRejectsInvalidInput()
	{
		assertRejectsIds(client, id -> () -> emails.validateSettings(id, brandAndParagraphSettings()));

		assertThrows(NullPointerException.class, () -> emails.validateSettings("123", null));
		assertTrue(client.requests().isEmpty(), "the SDK must send no request");
	}


	/**
	 * createTemplate sends POST /emails/{email}/templates with the name, and decodes the template.
	 *
	 * @throws Exception if the request fails
	 */
	@Test
	public void createTemplateSendsTheName() throws Exception
	{
		client.respond(201, Fixtures.read("email_template.json"));

		SingleEmailTemplate response = emails.createTemplate("123", "Newsletter");

		assertRequest(client, "POST", "/emails/123/templates", "{\"name\":\"Newsletter\"}", null);
		assertEquals(201, response.responseStatusCode);
		assertEquals("789", response.template.id);
		assertEquals("Newsletter", response.template.name);
		assertEquals("email", response.template.type);
		assertEquals("2026-10-28 12:00:00", response.template.createdAt);
	}

	/**
	 * createTemplate checks the ID and the name, and sends no request when a check fails.
	 */
	@Test
	public void createTemplateRejectsInvalidInput()
	{
		assertRejectsIds(client, id -> () -> emails.createTemplate(id, "Newsletter"));

		assertThrows(IllegalArgumentException.class, () -> emails.createTemplate("123", ""));
		assertThrows(IllegalArgumentException.class, () -> emails.createTemplate("123", null));
		assertTrue(client.requests().isEmpty(), "the SDK must send no request");
	}


	/**
	 * versions().get() sends GET /emails/{email}/versions without a query string, and decodes the list.
	 *
	 * @throws Exception if the request fails
	 */
	@Test
	public void versionsWithoutOptions() throws Exception
	{
		client.respond(200, Fixtures.read("email_versions.json"));

		EmailVersionsList response = emails.versions("123").get();

		assertRequest(client, "GET", "/emails/123/versions", null, null);
		assertEquals(2, response.versions.length);
		assertEquals("902", response.versions[0].id);
		assertEquals("2026-10-28 12:05:00", response.versions[0].createdAt);
		assertTrue(response.versions[0].current);
		assertEquals("901", response.versions[1].id);
		assertFalse(response.versions[1].current);
		assertNull(response.links.previous);
		assertNull(response.links.next);
		assertEquals(1, response.meta.from);
		assertEquals(2, response.meta.to);
		assertEquals(2, response.meta.total);
	}

	/**
	 * versions() sends the page and the limit.
	 *
	 * @throws Exception if the request fails
	 */
	@Test
	public void versionsWithPageAndLimit() throws Exception
	{
		client.respond(200, Fixtures.read("email_versions.json"));

		emails.versions("123").page(2).limit(10).get();

		assertRequest(client, "GET", "/emails/123/versions?limit=10&page=2", null, null);
	}

	/**
	 * An empty page has null for meta.from and meta.to. The SDK reads them as 0.
	 *
	 * @throws Exception if the request fails
	 */
	@Test
	public void versionsEmptyPageReadsNullFromAndToAsZero() throws Exception
	{
		client.respond(200, "{\"data\":[],\"links\":{\"first\":null,\"last\":null,\"prev\":null,\"next\":null},"
				+ "\"meta\":{\"current_page\":1,\"from\":null,\"last_page\":1,\"per_page\":25,\"to\":null,\"total\":0}}");

		EmailVersionsList response = emails.versions("123").get();

		assertEquals(0, response.versions.length);
		assertEquals(0, response.meta.from);
		assertEquals(0, response.meta.to);
		assertEquals(0, response.meta.total);
	}

	/**
	 * versions() checks the ID, and sends no request for an invalid ID.
	 */
	@Test
	public void versionsRejectsInvalidIds()
	{
		assertRejectsIds(client, id -> () -> emails.versions(id).get());
	}


	/**
	 * createVersion sends POST /emails/{email}/versions with an empty body, not the JSON null.
	 * Status 201 means a new version.
	 *
	 * @throws Exception if the request fails
	 */
	@Test
	public void createVersionNew() throws Exception
	{
		client.respond(201, Fixtures.read("email_version.json"));

		SingleEmailVersion response = emails.createVersion("123");

		RecordedRequest request = assertRequest(client, "POST", "/emails/123/versions", "", null);
		assertTrue(request.hasBodyPublisher);
		assertEquals(0, request.contentLength);
		assertEquals(0, request.body.length);
		assertTrue(response.isCreated());
		assertEquals(201, response.responseStatusCode);
		assertEquals("903", response.version.id);
		assertEquals("2026-10-28 12:10:00", response.version.createdAt);
		assertTrue(response.version.current);
	}

	/**
	 * Status 200 means that nothing changed, and the response has the last version.
	 *
	 * @throws Exception if the request fails
	 */
	@Test
	public void createVersionUnchanged() throws Exception
	{
		client.respond(200, Fixtures.read("email_version.json"));

		SingleEmailVersion response = emails.createVersion("123");

		assertFalse(response.isCreated());
		assertEquals(200, response.responseStatusCode);
		assertEquals("903", response.version.id);
	}

	/**
	 * createVersion checks the ID, and sends no request for an invalid ID.
	 */
	@Test
	public void createVersionRejectsInvalidIds()
	{
		assertRejectsIds(client, id -> () -> emails.createVersion(id));
	}

}
