/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.files;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.util.CursorPaginatedRequest;

/**
 * Lists the files of the media library. Set the filters, the sort, the page and the limit, and then call get().
 * <p>
 * The SDK encodes each value, so a space is sent as "+". A null value is not sent. An empty value is sent as
 * "name=", and the API then ignores it.
 */
public class MediaFileRetriever extends CursorPaginatedRequest<MediaFileRetriever> {

	/** The api object reference. */
	private MailerLite apiObjectReference;

	/**
	 * Instantiates a new retriever. Use {@link MediaFiles#retriever()}.
	 *
	 * @param apiReference the api reference
	 */
	public MediaFileRetriever(MailerLite apiReference)
	{
		apiObjectReference = apiReference;
	}

	/**
	 * Filters the files on a part of the name.
	 *
	 * @param name a part of the file name
	 * @return this retriever
	 */
	public MediaFileRetriever nameFilter(String name)
	{
		return filter("name", name);
	}

	/**
	 * Filters the files on the type.
	 *
	 * @param type the file type, for example {@link MediaFileTypes#IMAGE}
	 * @return this retriever
	 */
	public MediaFileRetriever typeFilter(String type)
	{
		return filter("type", type);
	}

	/**
	 * Adds a filter. The SDK sends it as "filter[name]=value".
	 *
	 * @param name the filter name, for example "name" or "type"
	 * @param value the value
	 * @return this retriever
	 */
	public MediaFileRetriever filter(String name, String value)
	{
		this.addQueryParameter("filter[".concat(name).concat("]"), value);

		return this;
	}

	/**
	 * Sorts the files.
	 *
	 * @param sort one of the values in {@link MediaFileSorts}
	 * @return this retriever
	 */
	public MediaFileRetriever sort(String sort)
	{
		this.addQueryParameter("sort", sort);

		return this;
	}

	/**
	 * Gets a page of files.
	 *
	 * @return the files
	 * @throws MailerLiteException if the API returns an error or the request fails
	 */
	public MediaFilesList get() throws MailerLiteException
	{
		String query = this.getQueryParameters();

		if (query.equals("?")) {
			query = "";
		}

		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());

		return api.sendRequest("GET", "/files".concat(query), null, null, MediaFilesList.class);
	}

	/**
	 * Gets this instance.
	 *
	 * @return this retriever
	 */
	@Override
	protected MediaFileRetriever getInstance()
	{
		return this;
	}
}
