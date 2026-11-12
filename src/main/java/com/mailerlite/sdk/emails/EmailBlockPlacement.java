/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.emails;

import com.google.gson.annotations.SerializedName;

/**
 * The position of a block among the blocks at the same level.
 */
public class EmailBlockPlacement {

	/** The ID of the block before this block, or null if this block is the first block. */
	@SerializedName("after")
	public String after;

	/** The ID of the block after this block, or null if this block is the last block. */
	@SerializedName("before")
	public String before;
}
