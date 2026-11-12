/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.emails;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.util.CursorPaginatedRequest;

/**
 * Lists the versions of an email. Use page() and limit() to select a page, and then call get().
 */
public class EmailVersionsRetriever extends CursorPaginatedRequest<EmailVersionsRetriever> {

	private MailerLite apiObjectReference;

	private String endpoint;

	EmailVersionsRetriever(MailerLite apiReference, String endpoint)
	{
		this.apiObjectReference = apiReference;
		this.endpoint = endpoint;
	}

	/**
	 * Gets a page of versions, newest first.
	 *
	 * @return the versions
	 * @throws MailerLiteException if the API returns an error or the request fails
	 */
	public EmailVersionsList get() throws MailerLiteException
	{
		String query = this.getQueryParameters();

		if (query.equals("?")) {
			query = "";
		}

		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());

		return api.sendRequest("GET", endpoint.concat(query), null, null, EmailVersionsList.class);
	}

	/**
	 * Gets this instance.
	 *
	 * @return this retriever
	 */
	@Override
	protected EmailVersionsRetriever getInstance()
	{
		return this;
	}
}
