/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mailerlite.sdk.support.UrlAssert;

/**
 * Tests the URL comparison that ignores the sequence of the query parameters.
 */
public class UrlAssertTest {

	/**
	 * The sequence of the query parameters does not matter.
	 */
	@Test
	public void ignoresTheSequenceOfTheParameters()
	{
		assertTrue(UrlAssert.sameUrl("/files?a=1&b=2", "/files?b=2&a=1"));
		assertTrue(UrlAssert.sameUrl("/files", "/files"));
		UrlAssert.assertUrlEquals("/files?filter[name]=a+b&page=2", "/files?page=2&filter[name]=a+b");
	}

	/**
	 * A parameter that is sent two times is not the same as a parameter that is sent one time.
	 */
	@Test
	public void keepsDuplicateParameters()
	{
		assertFalse(UrlAssert.sameUrl("/files?a=1", "/files?a=1&a=1"));
	}

	/**
	 * The values and the encoding must be the same.
	 */
	@Test
	public void comparesTheRawValues()
	{
		assertFalse(UrlAssert.sameUrl("/files?a=1", "/files?a=2"));
		assertFalse(UrlAssert.sameUrl("/files?a=a+b", "/files?a=a%20b"));
	}

	/**
	 * The paths must be the same, and a URL without a query is not the same as a URL with a query.
	 */
	@Test
	public void comparesThePathExactly()
	{
		assertFalse(UrlAssert.sameUrl("/files?a=1", "/emails?a=1"));
		assertFalse(UrlAssert.sameUrl("/files", "/files?a=1"));
		assertThrows(AssertionError.class, () -> UrlAssert.assertUrlEquals("/files?a=1&b=2", "/files?a=1&b=3"));
	}
}
