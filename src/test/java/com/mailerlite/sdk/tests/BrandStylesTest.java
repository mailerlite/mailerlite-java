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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.brandstyles.BrandStyle;
import com.mailerlite.sdk.brandstyles.BrandStylesData;
import com.mailerlite.sdk.brandstyles.SingleBrandStyles;
import com.mailerlite.sdk.support.Fixtures;
import com.mailerlite.sdk.support.RecordingHttpClient;

/**
 * Tests the brand styles endpoint with the recording HTTP client.
 */
public class BrandStylesTest {

	/** The fake HTTP client. */
	private RecordingHttpClient client;

	/** The MailerLite object. */
	private MailerLite ml;

	/**
	 * Sets the fake HTTP client.
	 */
	@BeforeEach
	public void setupEach()
	{
		client = RecordingHttpClient.install();

		ml = new MailerLite();
		ml.setToken("test-token");
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
	 * get sends GET /brand-styles and decodes the brand, the default palette and the presets.
	 *
	 * @throws Exception if the request fails
	 */
	@Test
	public void getDecodesThePalettes() throws Exception
	{
		client.respond(200, Fixtures.read("brand_styles.json"));

		SingleBrandStyles response = ml.brandStyles().get();

		EmailsTest.assertRequest(client, "GET", "/brand-styles", null, null);

		BrandStylesData data = response.brandStyles;
		assertEquals("account", data.brand.identifier);
		assertEquals("Brand styles", data.brand.name);
		assertEquals("#2cb191", data.brand.primaryColor);

		BrandStyle defaultStyle = data.defaultStyle;
		assertEquals("default-brand", defaultStyle.identifier);
		assertEquals("Default", defaultStyle.name);
		assertEquals("Inter, sans-serif", defaultStyle.fontFamily);
		assertEquals("#09c269", defaultStyle.primaryColor);
		assertEquals("#000000", defaultStyle.secondaryColor);
		assertEquals("#000000", defaultStyle.headingColor);
		assertEquals("#000000", defaultStyle.textColor);
		assertEquals("#e5e7eb", defaultStyle.borderColor);
		assertEquals("#ffffff", defaultStyle.backgroundColor);

		assertEquals(2, data.presets.length);
		assertEquals("preset-business-casual", data.presets[0].identifier);
		assertEquals("preset-retro", data.presets[1].identifier);
		assertEquals("Courier New, monospace", data.presets[1].fontFamily);
	}

	/**
	 * brand is null when the account has no brand styles.
	 *
	 * @throws Exception if the request fails
	 */
	@Test
	public void getWithoutBrand() throws Exception
	{
		client.respond(200, "{\"data\":{\"brand\":null,\"default\":{\"identifier\":\"default-brand\",\"name\":\"Default\"},\"presets\":[]}}");

		SingleBrandStyles response = ml.brandStyles().get();

		assertNull(response.brandStyles.brand);
		assertEquals("default-brand", response.brandStyles.defaultStyle.identifier);
		assertEquals(0, response.brandStyles.presets.length);
	}
}
