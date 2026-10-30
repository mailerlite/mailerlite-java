/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.emails;

/**
 * The block types of the simple editor.
 * <p>
 * The API can add new types. The SDK accepts any type string when it reads a block, and it sends any type string
 * that you give.
 */
public final class EmailBlockTypes {

	/** A quotation. */
	public static final String BLOCKQUOTE = "blockquote";

	/** A list with bullets. */
	public static final String BULLETED_LIST = "bulleted_list";

	/** A button. */
	public static final String BUTTON = "button";

	/** A code block. */
	public static final String CODE = "code";

	/** A divider line. */
	public static final String DIVIDER = "divider";

	/** A heading. */
	public static final String HEADING = "heading";

	/** An image. */
	public static final String IMAGE = "image";

	/** A list with numbers. */
	public static final String NUMBERED_LIST = "numbered_list";

	/** A paragraph. */
	public static final String PARAGRAPH = "paragraph";

	/** A row of social icons. */
	public static final String SOCIAL_ICON = "social_icon";

	/** The title of the email. It is read-only: you cannot insert it. */
	public static final String TITLE = "title";

	/** A video. */
	public static final String VIDEO = "video";

	private EmailBlockTypes()
	{
	}
}
