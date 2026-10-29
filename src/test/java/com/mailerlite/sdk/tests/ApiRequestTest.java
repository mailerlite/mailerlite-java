/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.mailerlite.sdk.util.ApiRequest;

/**
 * Tests the query string of ApiRequest.
 */
public class ApiRequestTest {

	/**
	 * The SDK does not store a null value.
	 */
	@Test
	public void skipsNullValues()
	{
		ApiRequest request = new ApiRequest();
		request.addQueryParameter("filter[type]", null);
		request.addQueryParameter("page", "2");

		assertEquals("?page=2", request.getQueryParameters());
	}

	/**
	 * A null value removes a value that the caller set before.
	 */
	@Test
	public void nullValueRemovesTheEarlierValue()
	{
		ApiRequest request = new ApiRequest();
		request.addQueryParameter("filter[type]", "image");
		request.addQueryParameter("filter[type]", null);

		assertEquals("?", request.getQueryParameters());
	}

	/**
	 * The SDK encodes a space as a plus sign and keeps the brackets of the key.
	 */
	@Test
	public void encodesSpacesInValues()
	{
		ApiRequest request = new ApiRequest();
		request.addQueryParameter("filter[name]", "summer sale banner");

		assertEquals("?filter[name]=summer+sale+banner", request.getQueryParameters());
	}

	/**
	 * The SDK encodes the characters that have a meaning in a query string, and characters that are not ASCII.
	 */
	@Test
	public void encodesReservedAndNonAsciiCharacters()
	{
		ApiRequest request = new ApiRequest();
		request.addQueryParameter("filter[name]", "a&b=c#d/é");

		assertEquals("?filter[name]=a%26b%3Dc%23d%2F%C3%A9", request.getQueryParameters());
	}

	/**
	 * The SDK sends an empty value as "key=".
	 */
	@Test
	public void sendsEmptyValue()
	{
		ApiRequest request = new ApiRequest();
		request.addQueryParameter("filter[name]", "");

		assertEquals("?filter[name]=", request.getQueryParameters());
	}

	/**
	 * A value with only safe characters does not change, so the recorded tapes still match.
	 */
	@Test
	public void keepsSafeValues()
	{
		ApiRequest request = new ApiRequest();
		request.addQueryParameter("filter[status]", "active");

		assertEquals("?filter[status]=active", request.getQueryParameters());
	}

	/**
	 * The SDK encodes each value, so a value that the caller encoded is encoded two times. Do not encode values.
	 */
	@Test
	public void encodesAnEncodedValueAgain()
	{
		ApiRequest request = new ApiRequest();
		request.addQueryParameter("filter[name]", "summer%20sale");

		assertEquals("?filter[name]=summer%2520sale", request.getQueryParameters());
	}

	/**
	 * A plus sign is sent as "%2B", so the API reads a literal plus sign, not a space.
	 */
	@Test
	public void sendsPlusAsLiteralPlus()
	{
		ApiRequest request = new ApiRequest();
		request.addQueryParameter("filter[name]", "a+b");

		assertEquals("?filter[name]=a%2Bb", request.getQueryParameters());
	}

}
