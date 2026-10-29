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

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteResponse;
import com.mailerlite.sdk.RequestOptions;
import com.mailerlite.sdk.emails.EmailBlockInput;
import com.mailerlite.sdk.emails.EmailBlockPosition;
import com.mailerlite.sdk.emails.EmailBlockPositions;
import com.mailerlite.sdk.emails.EmailBlockTypes;
import com.mailerlite.sdk.emails.Emails;
import com.mailerlite.sdk.emails.SingleEmailBlock;
import com.mailerlite.sdk.exceptions.InvalidIdempotencyKeyException;
import com.mailerlite.sdk.support.Fixtures;
import com.mailerlite.sdk.support.RecordingHttpClient;

public class IdempotencyTest {

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

	private Map<String, Object[]> writeMethods()
	{
		EmailBlockInput divider = new EmailBlockInput(EmailBlockTypes.DIVIDER, new LinkedHashMap<String, Object>());

		Map<String, Object[]> methods = new LinkedHashMap<String, Object[]>();
		methods.put("createBlock", new Object[] {"email_block_created.json",
				(Function<RequestOptions, Executable>) o -> () -> emails.createBlock("123", "42", divider, o)});
		methods.put("updateBlock", new Object[] {"email_block_updated.json",
				(Function<RequestOptions, Executable>) o -> () -> emails.updateBlock("123", "57", "43", new HashMap<String, Object>(), o)});
		methods.put("deleteBlock", new Object[] {"email_etag.json",
				(Function<RequestOptions, Executable>) o -> () -> emails.deleteBlock("123", "57", "44", o)});
		methods.put("moveBlock", new Object[] {"email_block_moved.json",
				(Function<RequestOptions, Executable>) o -> () -> emails.moveBlock("123", "57", "45",
						new EmailBlockPosition(EmailBlockPositions.START), o)});
		methods.put("updateSettings", new Object[] {"email_settings.json",
				(Function<RequestOptions, Executable>) o -> () -> emails.updateSettings("123", "46",
						EmailsTest.brandAndParagraphSettings(), o)});
		methods.put("createTemplate", new Object[] {"email_template.json",
				(Function<RequestOptions, Executable>) o -> () -> emails.createTemplate("123", "Newsletter", o)});
		methods.put("createVersion", new Object[] {"email_version.json",
				(Function<RequestOptions, Executable>) o -> () -> emails.createVersion("123", o)});
		methods.put("restoreVersion", new Object[] {"email.json",
				(Function<RequestOptions, Executable>) o -> () -> emails.restoreVersion("123", "901", "47", o)});

		return methods;
	}

	@Test
	@SuppressWarnings("unchecked")
	public void writeMethodsSendTheKeyOnlyWhenSet() throws Throwable
	{
		for (Map.Entry<String, Object[]> method : writeMethods().entrySet()) {

			String name = method.getKey();
			String fixture = (String) method.getValue()[0];
			Function<RequestOptions, Executable> call = (Function<RequestOptions, Executable>) method.getValue()[1];

			client.requests().clear();
			client.respond(200, Fixtures.read(fixture)).respond(200, Fixtures.read(fixture)).respond(200, Fixtures.read(fixture));

			call.apply(RequestOptions.idempotencyKey("key-" + name)).execute();
			call.apply(null).execute();
			call.apply(RequestOptions.idempotencyKey(null)).execute();

			List<RecordingHttpClient.RecordedRequest> requests = client.requests();
			assertEquals("key-" + name, requests.get(0).header("Idempotency-Key"), name);
			assertNull(requests.get(1).header("Idempotency-Key"), name);
			assertNull(requests.get(2).header("Idempotency-Key"), name);
			assertEquals(requests.get(0).bodyString(), requests.get(1).bodyString(), name);
		}
	}

	@Test
	public void validateMethodsSendNoKey() throws Exception
	{
		client.respond(200, Fixtures.read("email_block_validation_valid.json"))
				.respond(200, Fixtures.read("email_settings_validation.json"));

		emails.validateBlock(new EmailBlockInput(EmailBlockTypes.DIVIDER, new LinkedHashMap<String, Object>()));
		emails.validateSettings("123", EmailsTest.brandAndParagraphSettings());

		assertNull(client.requests().get(0).header("Idempotency-Key"));
		assertNull(client.requests().get(1).header("Idempotency-Key"));
	}

	@Test
	public void invalidKeysThrowAtOnce()
	{
		for (String key : RequestChecksTest.INVALID_KEYS) {
			assertThrows(InvalidIdempotencyKeyException.class, () -> RequestOptions.idempotencyKey(key),
					"key of length " + key.length());
		}

		String longest = RequestChecksTest.repeat('k', 255);
		assertEquals(longest, RequestOptions.idempotencyKey(longest).getIdempotencyKey());
		assertNull(RequestOptions.idempotencyKey(null).getIdempotencyKey());
		assertTrue(client.requests().isEmpty());
	}

	@Test
	public void replayFlag() throws Exception
	{
		String[][] cases = {
			{"Idempotent-Replayed", "true", "true"},
			{"Idempotent-Replayed", "True", "true"},
			{"idempotent-replayed", " TRUE ", "true"},
			{"Idempotent-Replayed", "false", "false"},
		};

		for (String[] item : cases) {

			client.respond(201, Fixtures.read("email_block_created.json"), Collections.singletonMap(item[0], item[1]));

			SingleEmailBlock response = emails.createBlock("123", "42",
					new EmailBlockInput(EmailBlockTypes.DIVIDER, new LinkedHashMap<String, Object>()));

			assertEquals(Boolean.parseBoolean(item[2]), response.isIdempotentReplay(), item[0] + ": " + item[1]);
		}

		client.respond(201, Fixtures.read("email_block_created.json"));
		assertFalse(emails.createBlock("123", "42",
				new EmailBlockInput(EmailBlockTypes.DIVIDER, new LinkedHashMap<String, Object>())).isIdempotentReplay());
	}

	@Test
	public void replayFlagWithPlainHeaderMap()
	{
		MailerLiteResponse response = new MailerLiteResponse();
		assertFalse(response.isIdempotentReplay());

		Map<String, List<String>> headers = new HashMap<String, List<String>>();
		headers.put("idempotent-replayed", Collections.singletonList("true"));
		response.headers = headers;
		assertTrue(response.isIdempotentReplay());
	}
}
