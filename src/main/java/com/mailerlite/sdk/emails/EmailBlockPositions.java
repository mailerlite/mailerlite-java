/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.emails;

/**
 * The values of {@link EmailBlockPosition#position}.
 */
public final class EmailBlockPositions {

	/** At the start of the level. The API does not use a block ID. */
	public static final String START = "start";

	/** At the end of the level. The API does not use a block ID. */
	public static final String END = "end";

	/** Before the block with the block ID. */
	public static final String BEFORE = "before";

	/** After the block with the block ID. */
	public static final String AFTER = "after";

	private EmailBlockPositions()
	{
	}
}
