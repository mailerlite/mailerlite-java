/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.campaigns;

import java.util.Date;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.emails.Email;
import com.mailerlite.sdk.util.ApiResource;
import com.mailerlite.sdk.util.ParseDate;

public class Campaign extends ApiResource {

	@SerializedName("id")
	public String id;
	
	@SerializedName("account_id")
	public int accountId;
	
	@SerializedName("name")
	public String name;
	
	@SerializedName("type")
	public String type;
	
	@SerializedName("status")
	public String status;
	
	@SerializedName("missing_data")
	public String[] missingData;
	
	/**
	 * Settings will be an empty array if there are no settings
	 * or an object if there are.
	 */
	@SerializedName("settings")
	public JsonElement settings;

	@SerializedName("delivery_schedule")
	public String deliverySchedule;
	
	@SerializedName("language_id")
	public String languageId;
	
	
	@SerializedName("created_at")
	public String createdAtStr;
	
	@ParseDate("createdAtStr")
	public Date createdAt;
	
	
	@SerializedName("updated_at")
	public String updatedAtStr;
	
	@ParseDate("updatedAtStr")
	public Date updatedAt;
	
	
	@SerializedName("scheduled_for")
	public String scheduledForStr;
	
	@ParseDate("scheduledForStr")
	public Date scheduledFor;
	
	
	@SerializedName("queued_at")
	public String queuedAtStr;
	
	@ParseDate("queuedAtStr")
	public Date queuedAt;
	
	
	@SerializedName("started_at")
	public String startedAtStr;
	
	@ParseDate("startedAtStr")
	public Date startedAt;
	
	
	@SerializedName("finished_at")
	public String finishedAtStr;
	
	@ParseDate("finishedAtStr")
	public Date finishedAt;
	
	
	@SerializedName("stopped_at")
	public String stoppedAtStr;
	
	@ParseDate("stoppedAtStr")
	public Date stoppedAt;
	
	
	@SerializedName("default_email_id")
	public String defaultEmailId;
	
	@SerializedName("emails")
	public Email[] emails;
	
	@SerializedName("used_in_automations")
	public Boolean usedInAutomations;
	
	@SerializedName("type_for_humans")
	public String typeForHumans;
	
	@SerializedName("stats")
	public CampaignStats stats;
	
	@SerializedName("is_stopped")
	public Boolean isStopped;
	
	@SerializedName("has_winner")
	public Boolean hasWinner;
	
	@SerializedName("winner_version_for_humans")
	public String winnerVersionForHumans;
	
	@SerializedName("winner_sending_time_for_humans")
	public String winnerSendingTimeForHumans;
	
	@SerializedName("winner_selected_manually_at")
	public String winnerSelectedManuallyAtStr;
	
	@ParseDate("winnerSelectedManuallyAtStr")
	public Date winnerSelectedManuallyAt;
	
	@SerializedName("uses_ecommerce")
	public Boolean usesEcommerce;
	
	@SerializedName("uses_survey")
	public Boolean usesSurvey;
	
	@SerializedName("can_be_scheduled")
	public Boolean canBeScheduled;
	
	@SerializedName("warnings")
	public String[] warnings;
	
	@SerializedName("initial_created_at")
	public String initialCreatedAtStr;
	
	@ParseDate("initialCreatedAtStr")
	public Date initialCreatedAt;
}
