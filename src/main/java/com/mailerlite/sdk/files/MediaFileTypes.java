/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.files;

/**
 * The file types of the media library, for the type filter. The API can add new types, so the filter accepts any
 * string.
 */
public final class MediaFileTypes {

	/** An image. */
	public static final String IMAGE = "image";

	/** A PDF document. */
	public static final String PDF = "pdf";

	/** An EPUB book. */
	public static final String EPUB = "epub";

	/**
	 * Do not make instances of this class.
	 */
	private MediaFileTypes()
	{
	}
}
