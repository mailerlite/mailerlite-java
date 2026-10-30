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
 * A block to insert into an email or to validate.
 */
public class EmailBlockInput {

	/** The block type: one of the values in {@link EmailBlockTypes}, except TITLE. The API needs it. */
	@SerializedName("type")
	public String type;

	/**
	 * The editor document of the block. The API needs it, but it can be an empty map, for example for a divider.
	 * The keys are camelCase, for example "textAlign". The SDK sends the map as it is, also its null values.
	 */
	@SerializedName("editor_content")
	@JsonAdapter(EditorContentAdapter.class)
	public Map<String, Object> editorContent;

	/** The position of the new block. If it is null, the API puts the block at the end of the email. */
	@SerializedName("placement")
	public EmailBlockPosition placement;

	/**
	 * Instantiates an empty block input.
	 */
	public EmailBlockInput()
	{
	}

	/**
	 * Instantiates a block input with a type and an editor document.
	 *
	 * @param type the block type
	 * @param editorContent the editor document
	 */
	public EmailBlockInput(String type, Map<String, Object> editorContent)
	{
		this.type = type;
		this.editorContent = editorContent;
	}
}
