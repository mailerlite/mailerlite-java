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

public class ApiRequestTest {

	@Test
	public void skipsNullValues()
	{
		ApiRequest request = new ApiRequest();
		request.addQueryParameter("filter[type]", null);
		request.addQueryParameter("page", "2");

		assertEquals("?page=2", request.getQueryParameters());
	}

	@Test
	public void nullValueRemovesTheEarlierValue()
	{
		ApiRequest request = new ApiRequest();
		request.addQueryParameter("filter[type]", "image");
		request.addQueryParameter("filter[type]", null);

		assertEquals("?", request.getQueryParameters());
	}

	@Test
	public void encodesSpacesInValues()
	{
		ApiRequest request = new ApiRequest();
		request.addQueryParameter("filter[name]", "summer sale banner");

		assertEquals("?filter[name]=summer+sale+banner", request.getQueryParameters());
	}

	@Test
	public void encodesReservedAndNonAsciiCharacters()
	{
		ApiRequest request = new ApiRequest();
		request.addQueryParameter("filter[name]", "a&b=c#d/é");

		assertEquals("?filter[name]=a%26b%3Dc%23d%2F%C3%A9", request.getQueryParameters());
	}

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

	@Test
	public void encodesAnEncodedValueAgain()
	{
		ApiRequest request = new ApiRequest();
		request.addQueryParameter("filter[name]", "summer%20sale");

		assertEquals("?filter[name]=summer%2520sale", request.getQueryParameters());
	}

	@Test
	public void sendsPlusAsLiteralPlus()
	{
		ApiRequest request = new ApiRequest();
		request.addQueryParameter("filter[name]", "a+b");

		assertEquals("?filter[name]=a%2Bb", request.getQueryParameters());
	}

}
