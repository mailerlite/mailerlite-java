/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.emails;

import java.util.Map;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * A content block of an email.
 */
public class EmailBlock {

	/** The ID of the block. */
	@SerializedName("id")
	public String id;

	/** The block type. See EmailBlockTypes. The API can add new types, so the SDK accepts any value. */
	@SerializedName("type")
	public String type;

	/**
	 * The editor document of the block.
	 * The keys are camelCase, for example "textAlign" and "linkId". Whole numbers are Long values.
	 */
	@SerializedName("editor_content")
	@JsonAdapter(EditorContentAdapter.class)
	public Map<String, Object> editorContent;

	/** The blocks in this block, or null if the block has none. */
	@SerializedName("children")
	public EmailBlock[] children;

	/**
	 * The position of the block. Only the responses for one block have it. In the blocks of an email, it is null.
	 */
	@SerializedName("placement")
	public EmailBlockPlacement placement;
}
