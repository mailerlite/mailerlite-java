/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.emails;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.ApiResource;

/**
 * The Class EmailBase.
 */
public class EmailBase extends ApiResource {
	
	/** The from. */
	@SerializedName("from")
	public String from;
	
	/** The from name. */
	@SerializedName("from_name")
	public String fromName;
	
	/** The content. */
	@SerializedName("content")
	public String content;
	
	/** The subject. */
	@SerializedName("subject")
	public String subject;
	
	/** The reply-to address. If it is null, the SDK does not send it. */
	@SerializedName("reply_to")
	public String replyTo;
	
	/**
	 * The editor of the email: the only accepted value is {@code simple}, which selects the simple editor instead of
	 * the drag-and-drop builder. It has to be the same across every email in the payload; a mixed array is rejected.
	 * Setting it prohibits {@code emails.*.content}.
	 * <p>
	 * Use {@link EmailEditors#SIMPLE}. If it is null, the SDK does not send it. Only
	 * {@code CampaignBuilder.create()} accepts it. If it is set, {@code CampaignBuilder.update()} gets a 422 error.
	 */
	@SerializedName("editor")
	public String editor;
	
	/**
	 * The template ID: the template the new email starts from. Allowed only when {@code editor} is {@code simple},
	 * accepts only a template from the shared gallery, and also has to be the same across every email.
	 * <p>
	 * If it is null, the SDK does not send it. Only {@code CampaignBuilder.create()} accepts it. If it is set,
	 * {@code CampaignBuilder.update()} gets a 422 error.
	 */
	@SerializedName("template_id")
	public String templateId;
}
