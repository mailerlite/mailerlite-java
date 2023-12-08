/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.emails;

import java.time.LocalDateTime;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.ParseDate;

public class Email extends EmailBase {

	@SerializedName("id")
	public String id;
	
	@SerializedName("account_id")
	public String accountId;
	
	@SerializedName("emailable_id")
	public String emailableId;
	
	@SerializedName("emailable_type")
	public String emailableType;
	
	@SerializedName("type")
	public String type;
	
	@SerializedName("name")
	public String name;

	@SerializedName("plain_text")
	public String plainText;
	
	@SerializedName("screenshot_url")
	public String screenshotUrl;
	
	@SerializedName("preview_url")
	public String previewUrl;
	
	
	@SerializedName("created_at")
	public String createdAtStr;
	
	@ParseDate("createdAtStr")
	public LocalDateTime createdAt;
	
	
	@SerializedName("updated_at")
	public String updatedAtStr;
	
	@ParseDate("updatedAtStr")
	public LocalDateTime updatedAt;
	
	@SerializedName("is_designed")
	public boolean isDesigned;
	
	@SerializedName("language_id")
	public String languageId;
	
	@SerializedName("is_winner")
	public boolean isWinner;
	
	@SerializedName("stats")
	public EmailStats stats;
	
	@SerializedName("send_after")
	public DelayValue sendAfter;
	
	@SerializedName("track_opens")
	public boolean trackOpens;
}
