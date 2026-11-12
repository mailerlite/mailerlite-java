/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.support;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class UrlAssert {

	private UrlAssert()
	{
	}

	public static boolean sameUrl(String expected, String actual)
	{
		return normalize(expected).equals(normalize(actual));
	}

	public static void assertUrlEquals(String expected, String actual)
	{
		if (!sameUrl(expected, actual)) {
			assertEquals(expected, actual, "the URLs are different (the sequence of the query parameters is ignored)");
		}
	}

	/** Sorts the query pairs, because ApiRequest keeps the parameters in a HashMap with no fixed sequence. */
	private static String normalize(String url)
	{
		int mark = url.indexOf('?');

		if (mark < 0) {
			return url;
		}

		List<String> pairs = new ArrayList<String>(Arrays.asList(url.substring(mark + 1).split("&", -1)));
		Collections.sort(pairs);

		return url.substring(0, mark + 1) + pairs;
	}
}
