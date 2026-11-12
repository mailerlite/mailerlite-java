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

public class MediaFilesTest {

	private RecordingHttpClient client;

	private MailerLite ml;

	@BeforeEach
	public void setupEach()
	{
		client = RecordingHttpClient.install();
		client.respond(200, Fixtures.read("files.json"));

		ml = new MailerLite();
		ml.setToken("test-token");
	}

	@AfterEach
	public void afterEach()
	{
		RecordingHttpClient.uninstall();
	}

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

	@Test
	public void nameFilterWithSpaces() throws Exception
	{
		ml.files().retriever().nameFilter("summer sale banner").get();

		EmailsTest.assertRequest(client, "GET", "/files?filter[name]=summer+sale+banner", null, null);
	}

	@Test
	public void emptyAndNullFilterValues() throws Exception
	{
		ml.files().retriever().nameFilter("").typeFilter(null).get();

		EmailsTest.assertRequest(client, "GET", "/files?filter[name]=", null, null);
	}

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
				"/files?filter[type]=image&filter[name]=banner&sort=-size&page=2&limit=25", null, null);
	}
}
