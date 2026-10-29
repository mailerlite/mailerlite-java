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
 * A footer text as an editor document. In an update, an empty editorContent map unsets the text.
 * The email then uses the text of your account.
 */
public class EmailEditorText {

	/** The editor document. The keys are camelCase. */
	@SerializedName("editor_content")
	@JsonAdapter(EditorContentAdapter.class)
	public Map<String, Object> editorContent;
}
