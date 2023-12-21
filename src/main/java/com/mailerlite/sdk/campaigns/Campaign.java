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

/**
 * The Class Campaign.
 */
public class Campaign extends ApiResource {

	/** The id. */
	@SerializedName("id")
	public String id;
	
	/** The account id. */
	@SerializedName("account_id")
	public int accountId;
	
	/** The name. */
	@SerializedName("name")
	public String name;
	
	/** The type. */
	@SerializedName("type")
	public String type;
	
	/** The status. */
	@SerializedName("status")
	public String status;
	
	/** The missing data. */
	@SerializedName("missing_data")
	public String[] missingData;
	
	/**
	 * Settings will be an empty array if there are no settings
	 * or an object if there are.
	 */
	@SerializedName("settings")
	public JsonElement settings;

	/** The delivery schedule. */
	@SerializedName("delivery_schedule")
	public String deliverySchedule;
	
	/** The language id. */
	@SerializedName("language_id")
	public String languageId;
	
	
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
	
	
	/** The scheduled for str. */
	@SerializedName("scheduled_for")
	public String scheduledForStr;
	
	/** The scheduled for. */
	@ParseDate("scheduledForStr")
	public Date scheduledFor;
	
	
	/** The queued at str. */
	@SerializedName("queued_at")
	public String queuedAtStr;
	
	/** The queued at. */
	@ParseDate("queuedAtStr")
	public Date queuedAt;
	
	
	/** The started at str. */
	@SerializedName("started_at")
	public String startedAtStr;
	
	/** The started at. */
	@ParseDate("startedAtStr")
	public Date startedAt;
	
	
	/** The finished at str. */
	@SerializedName("finished_at")
	public String finishedAtStr;
	
	/** The finished at. */
	@ParseDate("finishedAtStr")
	public Date finishedAt;
	
	
	/** The stopped at str. */
	@SerializedName("stopped_at")
	public String stoppedAtStr;
	
	/** The stopped at. */
	@ParseDate("stoppedAtStr")
	public Date stoppedAt;
	
	
	/** The default email id. */
	@SerializedName("default_email_id")
	public String defaultEmailId;
	
	/** The emails. */
	@SerializedName("emails")
	public Email[] emails;
	
	/** The used in automations. */
	@SerializedName("used_in_automations")
	public Boolean usedInAutomations;
	
	/** The type for humans. */
	@SerializedName("type_for_humans")
	public String typeForHumans;
	
	/** The stats. */
	@SerializedName("stats")
	public CampaignStats stats;
	
	/** The is stopped. */
	@SerializedName("is_stopped")
	public Boolean isStopped;
	
	/** The has winner. */
	@SerializedName("has_winner")
	public Boolean hasWinner;
	
	/** The winner version for humans. */
	@SerializedName("winner_version_for_humans")
	public String winnerVersionForHumans;
	
	/** The winner sending time for humans. */
	@SerializedName("winner_sending_time_for_humans")
	public String winnerSendingTimeForHumans;
	
	/** The winner selected manually at str. */
	@SerializedName("winner_selected_manually_at")
	public String winnerSelectedManuallyAtStr;
	
	/** The winner selected manually at. */
	@ParseDate("winnerSelectedManuallyAtStr")
	public Date winnerSelectedManuallyAt;
	
	/** The uses ecommerce. */
	@SerializedName("uses_ecommerce")
	public Boolean usesEcommerce;
	
	/** The uses survey. */
	@SerializedName("uses_survey")
	public Boolean usesSurvey;
	
	/** The can be scheduled. */
	@SerializedName("can_be_scheduled")
	public Boolean canBeScheduled;
	
	/** The warnings. */
	@SerializedName("warnings")
	public String[] warnings;
	
	/** The initial created at str. */
	@SerializedName("initial_created_at")
	public String initialCreatedAtStr;
	
	/** The initial created at. */
	@ParseDate("initialCreatedAtStr")
	public Date initialCreatedAt;
}
