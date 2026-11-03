/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.files;

/**
 * The sort values of the file list. The default is {@link #CREATED_AT_DESC}: the newest files first.
 */
public final class MediaFileSorts {

	/** The oldest files first. */
	public static final String CREATED_AT = "created_at";

	/** The newest files first. */
	public static final String CREATED_AT_DESC = "-created_at";

	/** By name, from A to Z. */
	public static final String NAME = "name";

	/** By name, from Z to A. */
	public static final String NAME_DESC = "-name";

	/** The smallest files first. */
	public static final String SIZE = "size";

	/** The largest files first. */
	public static final String SIZE_DESC = "-size";

	private MediaFileSorts()
	{
	}
}
