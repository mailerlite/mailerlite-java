/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.files;

import com.mailerlite.sdk.MailerLite;

/**
 * The files endpoint: the files of the media library of the account.
 */
public class MediaFiles {

	private MailerLite apiObjectReference;

	/**
	 * Instantiates a new files service.
	 *
	 * @param apiReference the api reference
	 */
	public MediaFiles(MailerLite apiReference)
	{
		apiObjectReference = apiReference;
	}

	/**
	 * Lists the files.
	 *
	 * @return the retriever
	 */
	public MediaFileRetriever retriever()
	{
		return new MediaFileRetriever(apiObjectReference);
	}
}
