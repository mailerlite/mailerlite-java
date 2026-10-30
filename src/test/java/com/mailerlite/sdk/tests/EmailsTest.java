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
import com.mailerlite.sdk.emails.EmailLayoutSettings;
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
import com.mailerlite.sdk.exceptions.EmptyIdException;
import com.mailerlite.sdk.exceptions.InvalidIdException;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.support.Fixtures;
import com.mailerlite.sdk.support.RecordingHttpClient;
import com.mailerlite.sdk.support.RecordingHttpClient.RecordedRequest;
import com.mailerlite.sdk.support.UrlAssert;

public class EmailsTest {

	static final String BASE = "https://connect.mailerlite.com/api";

	private RecordingHttpClient client;

	private Emails emails;

	@BeforeEach
	public void setupEach()
	{
		client = RecordingHttpClient.install();

		MailerLite ml = new MailerLite();
		ml.setToken("test-token");
		emails = ml.emails();
	}

	@AfterEach
	public void afterEach()
	{
		RecordingHttpClient.uninstall();
	}

	static RecordedRequest assertRequest(RecordingHttpClient client, String method, String path, String body,
			String idempotencyKey)
	{
		assertEquals(1, client.requests().size(), "number of requests");

		RecordedRequest request = client.lastRequest();
		assertEquals(method, request.method);
		UrlAssert.assertUrlEquals(BASE + path, request.uri.toString());

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

	@SuppressWarnings("unchecked")
	static Map<String, Object> map(Map<String, Object> map, String key)
	{
		return (Map<String, Object>) map.get(key);
	}

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

	@Test
	public void getRejectsInvalidIds()
	{
		assertRejectsIds(client, id -> () -> emails.get(id));
	}

	@Test
	public void getHtmlDecodesTheHtml() throws Exception
	{
		client.respond(200, Fixtures.read("email_html.json"));

		SingleEmailHtml response = emails.getHtml("123");

		assertRequest(client, "GET", "/emails/123/html", null, null);
		assertEquals("<!doctype html><html><body><p>Hello</p></body></html>", response.preview.html);
		assertEquals("42", response.etag);
	}

	@Test
	public void getHtmlServiceUnavailable()
	{
		client.respond(503, "<html><body>Service Unavailable</body></html>");

		MailerLiteException e = assertThrows(MailerLiteException.class, () -> emails.getHtml("123"));

		assertEquals(503, e.code);
		assertEquals("<html><body>Service Unavailable</body></html>", e.responseBody);
	}

	@Test
	public void getHtmlRejectsInvalidIds()
	{
		assertRejectsIds(client, id -> () -> emails.getHtml(id));
	}


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

	@Test
	public void getBlockRejectsInvalidIds()
	{
		assertRejectsIds(client, id -> () -> emails.getBlock(id, "57"));
		assertRejectsIds(client, id -> () -> emails.getBlock("123", id));
	}


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
	 * The API checks the etag. The SDK sends an empty etag as it is, and sends no etag key for a null etag.
	 */
	static void assertSendsEtags(RecordingHttpClient client, String response, Function<String, Executable> call,
			Function<String, String> expectedBody) throws Throwable
	{
		client.requests().clear();
		client.respond(200, response).respond(200, response);

		call.apply("").execute();
		assertEquals(expectedBody.apply(",\"etag\":\"\""), client.lastRequest().bodyString(), "empty etag");

		call.apply(null).execute();
		assertEquals(expectedBody.apply(""), client.lastRequest().bodyString(), "null etag");
		assertEquals(2, client.requests().size(), "number of requests");
	}

	/**
	 * The API checks the placement. The SDK sends the placement exactly as the caller gives it.
	 */
	static void assertSendsThePlacement(RecordingHttpClient client, String response,
			Function<EmailBlockPosition, Executable> call, Function<String, String> expectedBody) throws Throwable
	{
		Object[][] sent = {
			{EmailBlockPositions.END, null, "{\"position\":\"end\"}"},
			{EmailBlockPositions.END, "", "{\"position\":\"end\",\"block_id\":\"\"}"},
			{EmailBlockPositions.START, "5", "{\"position\":\"start\",\"block_id\":\"5\"}"},
			{EmailBlockPositions.START, "1/2", "{\"position\":\"start\",\"block_id\":\"1/2\"}"},
			{EmailBlockPositions.AFTER, "57", "{\"position\":\"after\",\"block_id\":\"57\"}"},
			{EmailBlockPositions.AFTER, null, "{\"position\":\"after\"}"},
			{EmailBlockPositions.AFTER, "", "{\"position\":\"after\",\"block_id\":\"\"}"},
			{EmailBlockPositions.AFTER, "\u00b2", "{\"position\":\"after\",\"block_id\":\"\u00b2\"}"},
			{EmailBlockPositions.BEFORE, "1", "{\"position\":\"before\",\"block_id\":\"1\"}"},
			{EmailBlockPositions.BEFORE, null, "{\"position\":\"before\"}"},
			{EmailBlockPositions.BEFORE, "1/2", "{\"position\":\"before\",\"block_id\":\"1/2\"}"},
			{"middle", "5", "{\"position\":\"middle\",\"block_id\":\"5\"}"},
			{"middle", null, "{\"position\":\"middle\"}"},
			{"", "5", "{\"position\":\"\",\"block_id\":\"5\"}"},
			{null, "5", "{\"block_id\":\"5\"}"},
			{null, null, "{}"},
		};

		for (Object[] item : sent) {

			client.requests().clear();
			client.respond(200, response);

			EmailBlockPosition placement = new EmailBlockPosition((String) item[0], (String) item[1]);
			call.apply(placement).execute();

			assertEquals(1, client.requests().size(), item[0] + " " + item[1]);
			assertEquals(expectedBody.apply((String) item[2]), client.lastRequest().bodyString(), item[0] + " " + item[1]);
			assertEquals(item[0], placement.position, "the SDK must not change the placement of the caller");
			assertEquals(item[1], placement.blockId, "the SDK must not change the placement of the caller");
		}
	}

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

	@Test
	public void createBlockSendsThePlacementAsGiven() throws Throwable
	{
		assertSendsThePlacement(client, Fixtures.read("email_block_created.json"), placement -> () -> {
			EmailBlockInput block = new EmailBlockInput(EmailBlockTypes.DIVIDER, new LinkedHashMap<String, Object>());
			block.placement = placement;
			emails.createBlock("123", "42", block);
		}, json -> "{\"type\":\"divider\",\"editor_content\":{},\"placement\":" + json + ",\"etag\":\"42\"}");
	}

	@Test
	public void createBlockRejectsInvalidInput()
	{
		EmailBlockInput divider = new EmailBlockInput(EmailBlockTypes.DIVIDER, new LinkedHashMap<String, Object>());

		assertRejectsIds(client, id -> () -> emails.createBlock(id, "42", divider));

		NullPointerException noBlock = assertThrows(NullPointerException.class, () -> emails.createBlock("123", "42", null));
		assertTrue(noBlock.getMessage().contains("block"), noBlock.getMessage());

		assertTrue(client.requests().isEmpty(), "the SDK must send no request");
	}

	@Test
	public void createBlockSendsTheEtagAsGiven() throws Throwable
	{
		EmailBlockInput divider = new EmailBlockInput(EmailBlockTypes.DIVIDER, new LinkedHashMap<String, Object>());

		assertSendsEtags(client, Fixtures.read("email_block_created.json"),
				etag -> () -> emails.createBlock("123", etag, divider),
				etag -> "{\"type\":\"divider\",\"editor_content\":{}" + etag + "}");
	}

	@Test
	public void createBlockSendsTheBlockValuesAsGiven() throws Exception
	{
		client.respond(422, "{\"message\":\"The given data was invalid.\","
				+ "\"errors\":{\"type\":[\"The type field is required.\"]}}")
				.respond(201, Fixtures.read("email_block_created.json"))
				.respond(201, Fixtures.read("email_block_created.json"));

		MailerLiteException e = assertThrows(MailerLiteException.class,
				() -> emails.createBlock("123", "42", new EmailBlockInput("", new LinkedHashMap<String, Object>())));

		assertEquals("{\"type\":\"\",\"editor_content\":{},\"etag\":\"42\"}", client.lastRequest().bodyString());
		assertEquals(422, e.code);
		assertArrayEquals(new String[] {"The type field is required."}, e.errors.get("type"));

		emails.createBlock("123", "42", new EmailBlockInput(null, new LinkedHashMap<String, Object>()));
		assertEquals("{\"editor_content\":{},\"etag\":\"42\"}", client.lastRequest().bodyString());

		emails.createBlock("123", "42", new EmailBlockInput(EmailBlockTypes.DIVIDER, null));
		assertEquals("{\"type\":\"divider\",\"etag\":\"42\"}", client.lastRequest().bodyString());
		assertEquals(3, client.requests().size());
	}


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

	@Test
	public void updateBlockRejectsInvalidInput()
	{
		Map<String, Object> content = paragraph("left", "Hi");

		assertRejectsIds(client, id -> () -> emails.updateBlock(id, "57", "43", content));
		assertRejectsIds(client, id -> () -> emails.updateBlock("123", id, "43", content));
	}

	@Test
	public void updateBlockSendsTheValuesAsGiven() throws Throwable
	{
		assertSendsEtags(client, Fixtures.read("email_block_updated.json"),
				etag -> () -> emails.updateBlock("123", "57", etag, new LinkedHashMap<String, Object>()),
				etag -> "{\"editor_content\":{}" + etag + "}");

		client.requests().clear();
		client.respond(200, Fixtures.read("email_block_updated.json"));

		emails.updateBlock("123", "57", "43", null);

		assertRequest(client, "PATCH", "/emails/123/blocks/57", "{\"etag\":\"43\"}", null);
	}


	@Test
	public void deleteBlockSendsTheEtagInTheBody() throws Exception
	{
		client.respond(200, Fixtures.read("email_etag.json"));

		EmailEtagResponse response = emails.deleteBlock("123", "57", "44");

		assertRequest(client, "DELETE", "/emails/123/blocks/57", "{\"etag\":\"44\"}", null);
		assertEquals(200, response.responseStatusCode);
		assertEquals("45", response.etag);
	}

	@Test
	public void deleteBlockRejectsInvalidInput()
	{
		assertRejectsIds(client, id -> () -> emails.deleteBlock(id, "57", "44"));
		assertRejectsIds(client, id -> () -> emails.deleteBlock("123", id, "44"));
	}

	@Test
	public void deleteBlockSendsTheEtagAsGiven() throws Throwable
	{
		assertSendsEtags(client, Fixtures.read("email_etag.json"),
				etag -> () -> emails.deleteBlock("123", "57", etag),
				etag -> "{" + (etag.isEmpty() ? "" : etag.substring(1)) + "}");
	}


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

	@Test
	public void moveBlockSendsThePlacementAsGiven() throws Throwable
	{
		assertSendsThePlacement(client, Fixtures.read("email_block_moved.json"),
				placement -> () -> emails.moveBlock("123", "57", "45", placement),
				json -> "{\"placement\":" + json + ",\"etag\":\"45\"}");
	}

	@Test
	public void moveBlockRejectsInvalidInput()
	{
		EmailBlockPosition start = new EmailBlockPosition(EmailBlockPositions.START);

		assertRejectsIds(client, id -> () -> emails.moveBlock(id, "57", "45", start));
		assertRejectsIds(client, id -> () -> emails.moveBlock("123", id, "45", start));
	}

	@Test
	public void moveBlockSendsTheValuesAsGiven() throws Throwable
	{
		EmailBlockPosition start = new EmailBlockPosition(EmailBlockPositions.START);

		assertSendsEtags(client, Fixtures.read("email_block_moved.json"),
				etag -> () -> emails.moveBlock("123", "57", etag, start),
				etag -> "{\"placement\":{\"position\":\"start\"}" + etag + "}");

		client.requests().clear();
		client.respond(200, Fixtures.read("email_block_moved.json"));

		emails.moveBlock("123", "57", "45", null);

		assertRequest(client, "POST", "/emails/123/blocks/57/move", "{\"etag\":\"45\"}", null);
	}


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

	@Test
	public void validateBlockSendsThePlacementAsGiven() throws Throwable
	{
		assertSendsThePlacement(client, Fixtures.read("email_block_validation_valid.json"), placement -> () -> {
			EmailBlockInput block = new EmailBlockInput(EmailBlockTypes.DIVIDER, new LinkedHashMap<String, Object>());
			block.placement = placement;
			emails.validateBlock(block);
		}, json -> "{\"type\":\"divider\",\"editor_content\":{},\"placement\":" + json + "}");
	}

	@Test
	public void validateBlockSendsAnInvalidBlock() throws Exception
	{
		String invalid = Fixtures.read("email_block_validation_invalid.json");
		client.respond(200, invalid).respond(200, invalid).respond(200, invalid);

		EmailValidation response = emails.validateBlock(new EmailBlockInput("", new LinkedHashMap<String, Object>()));
		assertEquals("{\"type\":\"\",\"editor_content\":{}}", client.lastRequest().bodyString());
		assertFalse(response.valid);

		emails.validateBlock(new EmailBlockInput(EmailBlockTypes.DIVIDER, null));
		assertEquals("{\"type\":\"divider\"}", client.lastRequest().bodyString());

		emails.validateBlock(null);
		assertEquals("{}", client.lastRequest().bodyString());
		assertEquals(3, client.requests().size());
	}


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

	@Test
	public void updateSettingsRejectsInvalidInput()
	{
		EmailSettings settings = brandAndParagraphSettings();

		assertRejectsIds(client, id -> () -> emails.updateSettings(id, "46", settings));

		NullPointerException e = assertThrows(NullPointerException.class, () -> emails.updateSettings("123", "46", null));
		assertTrue(e.getMessage().contains("settings"), e.getMessage());
		assertTrue(client.requests().isEmpty(), "the SDK must send no request");
	}


	@Test
	public void updateSettingsSendsTheEtagAsGiven() throws Throwable
	{
		EmailSettings settings = new EmailSettings();
		settings.layout = new EmailLayoutSettings();
		settings.layout.alignment = "left";

		assertSendsEtags(client, Fixtures.read("email_settings.json"),
				etag -> () -> emails.updateSettings("123", etag, settings),
				etag -> "{\"layout\":{\"alignment\":\"left\"}" + etag + "}");
	}


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

	@Test
	public void validateSettingsRejectsInvalidInput()
	{
		assertRejectsIds(client, id -> () -> emails.validateSettings(id, brandAndParagraphSettings()));
	}

	@Test
	public void validateSettingsWithNullSettingsSendsAnEmptyBody() throws Exception
	{
		client.respond(200, Fixtures.read("email_settings_validation.json"));

		emails.validateSettings("123", null);

		assertRequest(client, "POST", "/emails/123/settings/validate", "{}", null);
	}


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

	@Test
	public void createTemplateRejectsInvalidInput()
	{
		assertRejectsIds(client, id -> () -> emails.createTemplate(id, "Newsletter"));
	}

	@Test
	public void createTemplateSendsTheNameAsGiven() throws Exception
	{
		client.respond(422, "{\"message\":\"The given data was invalid.\","
				+ "\"errors\":{\"name\":[\"The name field is required.\"]}}")
				.respond(422, "{\"message\":\"The given data was invalid.\"}");

		MailerLiteException e = assertThrows(MailerLiteException.class, () -> emails.createTemplate("123", ""));

		assertRequest(client, "POST", "/emails/123/templates", "{\"name\":\"\"}", null);
		assertEquals(422, e.code);
		assertArrayEquals(new String[] {"The name field is required."}, e.errors.get("name"));

		assertThrows(MailerLiteException.class, () -> emails.createTemplate("123", null));
		assertEquals("{}", client.lastRequest().bodyString());
		assertEquals(2, client.requests().size());
	}


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

	@Test
	public void versionsWithPageAndLimit() throws Exception
	{
		client.respond(200, Fixtures.read("email_versions.json"));

		emails.versions("123").page(2).limit(10).get();

		assertRequest(client, "GET", "/emails/123/versions?page=2&limit=10", null, null);
	}

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

	@Test
	public void versionsRejectsInvalidIds()
	{
		assertRejectsIds(client, id -> () -> emails.versions(id).get());
	}


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

	@Test
	public void createVersionUnchanged() throws Exception
	{
		client.respond(200, Fixtures.read("email_version.json"));

		SingleEmailVersion response = emails.createVersion("123");

		assertFalse(response.isCreated());
		assertEquals(200, response.responseStatusCode);
		assertEquals("903", response.version.id);
	}

	@Test
	public void createVersionRejectsInvalidIds()
	{
		assertRejectsIds(client, id -> () -> emails.createVersion(id));
	}


	@Test
	public void restoreVersionSendsTheEtag() throws Exception
	{
		client.respond(200, Fixtures.read("email.json"));

		SingleEmailDocument response = emails.restoreVersion("123", "901", "47");

		assertRequest(client, "POST", "/emails/123/versions/901/restore", "{\"etag\":\"47\"}", null);
		assertEquals("42", response.etag);
		assertEquals("123", response.email.id);
		assertEquals(2, response.email.blocks.length);
	}

	@Test
	public void restoreVersionRejectsInvalidInput()
	{
		assertRejectsIds(client, id -> () -> emails.restoreVersion(id, "901", "47"));
		assertRejectsIds(client, id -> () -> emails.restoreVersion("123", id, "47"));
	}

	@Test
	public void restoreVersionSendsTheEtagAsGiven() throws Throwable
	{
		assertSendsEtags(client, Fixtures.read("email.json"),
				etag -> () -> emails.restoreVersion("123", "901", etag),
				etag -> "{" + (etag.isEmpty() ? "" : etag.substring(1)) + "}");
	}

}
