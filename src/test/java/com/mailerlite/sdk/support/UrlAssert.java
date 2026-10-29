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

/**
 * Compares URLs. The path must be the same. The query parameters must be the same raw "key=value" pairs, with the
 * same number of each pair, but in any sequence. The API gives no meaning to the sequence, and ApiRequest keeps the
 * parameters in a HashMap.
 */
public final class UrlAssert {

	/**
	 * Do not make instances of this class.
	 */
	private UrlAssert()
	{
	}

	/**
	 * Tells if two URLs are the same, in any sequence of the query parameters.
	 *
	 * @param expected the expected URL
	 * @param actual the actual URL
	 * @return true if they are the same
	 */
	public static boolean sameUrl(String expected, String actual)
	{
		return normalize(expected).equals(normalize(actual));
	}

	/**
	 * Asserts that two URLs are the same, in any sequence of the query parameters.
	 *
	 * @param expected the expected URL
	 * @param actual the actual URL
	 */
	public static void assertUrlEquals(String expected, String actual)
	{
		if (!sameUrl(expected, actual)) {
			assertEquals(expected, actual, "the URLs are different (the sequence of the query parameters is ignored)");
		}
	}

	/**
	 * Makes a form of the URL that has the path and the sorted list of the raw query pairs.
	 *
	 * @param url the URL
	 * @return the normalized form
	 */
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
