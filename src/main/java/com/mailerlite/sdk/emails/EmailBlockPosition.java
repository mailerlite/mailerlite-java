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
 * Where to put a block: at the start or the end of its level, or before or after a block at the same level.
 */
public class EmailBlockPosition {

	/** The position: one of the values in {@link EmailBlockPositions}. Required. */
	@SerializedName("position")
	public String position;

	/**
	 * The ID of the block at the same level to put the block before or after.
	 * <p>
	 * It is required for {@link EmailBlockPositions#BEFORE} and {@link EmailBlockPositions#AFTER}, and it must
	 * contain only the digits 0 to 9. For {@link EmailBlockPositions#START} and {@link EmailBlockPositions#END},
	 * the API does not use it, so the SDK does not check it and does not send it.
	 */
	@SerializedName("block_id")
	public String blockId;

	/**
	 * Instantiates an empty position.
	 */
	public EmailBlockPosition()
	{
	}

	/**
	 * Instantiates a position without a block, for example {@link EmailBlockPositions#START}.
	 *
	 * @param position the position
	 */
	public EmailBlockPosition(String position)
	{
		this.position = position;
	}

	/**
	 * Instantiates a position before or after a block.
	 *
	 * @param position the position, for example {@link EmailBlockPositions#AFTER}
	 * @param blockId the ID of the block at the same level
	 */
	public EmailBlockPosition(String position, String blockId)
	{
		this.position = position;
		this.blockId = blockId;
	}
}
