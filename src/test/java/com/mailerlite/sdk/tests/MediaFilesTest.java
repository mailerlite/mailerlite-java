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
import com.mailerlite.sdk.files.MediaFile;
import com.mailerlite.sdk.files.MediaFileSorts;
import com.mailerlite.sdk.files.MediaFileTypes;
import com.mailerlite.sdk.files.MediaFilesList;
import com.mailerlite.sdk.support.Fixtures;
import com.mailerlite.sdk.support.RecordingHttpClient;

/**
 * Tests the files endpoint with the recording HTTP client.
 */
public class MediaFilesTest {

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
		client.respond(200, Fixtures.read("files.json"));

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
	 * get() without options sends GET /files without a query string, and decodes the files.
	 *
	 * @throws Exception if the request fails
	 */
	@Test
	public void listWithoutOptions() throws Exception
	{
		MediaFilesList response = ml.files().retriever().get();

		EmailsTest.assertRequest(client, "GET", "/files", null, null);
		assertEquals(2, response.files.length);

		MediaFile image = response.files[0];
		assertEquals("301", image.id);
		assertEquals("image", image.type);
		assertEquals("summer sale banner.png", image.name);
		assertEquals("https://storage.example.com/files/summer-sale-banner.png", image.url);
		assertEquals(48213L, image.size);
		assertEquals(Integer.valueOf(1200), image.width);
		assertEquals(Integer.valueOf(600), image.height);
		assertEquals("2026-10-20 09:30:00", image.createdAt);

		MediaFile pdf = response.files[1];
		assertEquals("pdf", pdf.type);
		assertNull(pdf.width);
		assertNull(pdf.height);

		assertNull(response.links.next);
		assertEquals(2, response.meta.total);
	}

	/**
	 * A name filter with spaces keeps the key filter[name] and sends the spaces as "+".
	 *
	 * @throws Exception if the request fails
	 */
	@Test
	public void nameFilterWithSpaces() throws Exception
	{
		ml.files().retriever().nameFilter("summer sale banner").get();

		EmailsTest.assertRequest(client, "GET", "/files?filter[name]=summer+sale+banner", null, null);
	}

	/**
	 * An empty filter value is sent as "key=", and a null value is not sent.
	 *
	 * @throws Exception if the request fails
	 */
	@Test
	public void emptyAndNullFilterValues() throws Exception
	{
		ml.files().retriever().nameFilter("").typeFilter(null).get();

		EmailsTest.assertRequest(client, "GET", "/files?filter[name]=", null, null);
	}

	/**
	 * The type filter, the sort, the page and the limit are sent. The filter accepts types that the SDK does not know.
	 *
	 * @throws Exception if the request fails
	 */
	@Test
	public void allOptions() throws Exception
	{
		ml.files().retriever()
				.typeFilter(MediaFileTypes.IMAGE)
				.filter("name", "banner")
				.sort(MediaFileSorts.SIZE_DESC)
				.page(2)
				.limit(25)
				.get();

		EmailsTest.assertRequest(client, "GET",
				"/files?filter[type]=image&limit=25&filter[name]=banner&sort=-size&page=2", null, null);
	}
}
