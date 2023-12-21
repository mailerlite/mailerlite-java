/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.emails;


import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.ParseDate;

/**
 * The Class Email.
 */
public class Email extends EmailBase {

	/** The id. */
	@SerializedName("id")
	public String id;
	
	/** The account id. */
	@SerializedName("account_id")
	public String accountId;
	
	/** The emailable id. */
	@SerializedName("emailable_id")
	public String emailableId;
	
	/** The emailable type. */
	@SerializedName("emailable_type")
	public String emailableType;
	
	/** The type. */
	@SerializedName("type")
	public String type;
	
	/** The name. */
	@SerializedName("name")
	public String name;

	/** The plain text. */
	@SerializedName("plain_text")
	public String plainText;
	
	/** The screenshot url. */
	@SerializedName("screenshot_url")
	public String screenshotUrl;
	
	/** The preview url. */
	@SerializedName("preview_url")
	public String previewUrl;
	
	
	/** The created at str. */
	@SerializedName("created_at")
	public String createdAtStr;
	
	/** The created at. */
	@ParseDate("createdAtStr")
	public Date createdAt;
	
	
	/** The updated at str. */
	@SerializedName("updated_at")
	public String updatedAtStr;
	
	/** The updated at. */
	@ParseDate("updatedAtStr")
	public Date updatedAt;
	
	/** The is designed. */
	@SerializedName("is_designed")
	public boolean isDesigned;
	
	/** The language id. */
	@SerializedName("language_id")
	public String languageId;
	
	/** The is winner. */
	@SerializedName("is_winner")
	public boolean isWinner;
	
	/** The stats. */
	@SerializedName("stats")
	public EmailStats stats;
	
	/** The send after. */
	@SerializedName("send_after")
	public DelayValue sendAfter;
	
	/** The track opens. */
	@SerializedName("track_opens")
	public boolean trackOpens;
}
