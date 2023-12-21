/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.susbcribers;

import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.ApiResource;
import com.mailerlite.sdk.util.ParseDate;

/**
 * The Class SubscriberImport.
 */
public class SubscriberImport extends ApiResource {

	/** The id. */
	@SerializedName("id")
	public String id;
	
	/** The total. */
	@SerializedName("total")
	public int total;
	
	/** The processed. */
	@SerializedName("processed")
	public int processed;
	
	/** The imported. */
	@SerializedName("imported")
	public int imported;
	
	/** The updated. */
	@SerializedName("updated")
	public int updated;
	
	/** The errored. */
	@SerializedName("errored")
	public int errored;
	
	/** The percent. */
	@SerializedName("percent")
	public float percent;
	
	/** The done. */
	@SerializedName("done")
	public boolean done;
	
	/** The file path. */
	@SerializedName("file_path")
	public String filePath;
	
	/** The invalid. */
	@SerializedName("invalid")
	public SubscriberImportStatusEntry[] invalid;
	
	/** The invalid count. */
	@SerializedName("invalid_count")
	public int invalidCount;
	
	/** The mistyped. */
	@SerializedName("mistyped")
	public SubscriberImportStatusEntry[] mistyped;
	
	/** The mistyped count. */
	@SerializedName("mistyped_count")
	public int mistypedCount;
	
	/** The changed. */
	@SerializedName("changed")
	public SubscriberImportStatusEntry[] changed;
	
	/** The changed count. */
	@SerializedName("changed_count")
	public int changedCount;
	
	/** The unchanged. */
	@SerializedName("unchanged")
	public SubscriberImportStatusEntry[] unchanged;
	
	/** The unchanged count. */
	@SerializedName("unchanged_count")
	public int unchangedCount;
	
	/** The unsubscribed. */
	@SerializedName("unsubscribed")
	public SubscriberImportStatusEntry[] unsubscribed;
	
	/** The unsubscribed count. */
	@SerializedName("unsubscribed_count")
	public int unsubscribedCount;
	
	/** The role based. */
	@SerializedName("role_based")
	public SubscriberImportStatusEntry[] roleBased;
	
	/** The role based count. */
	@SerializedName("role_based_count")
	public int roleBasedCount;
	
	/** The banned import emails count. */
	@SerializedName("banned_import_emails_count")
	public int bannedImportEmailsCount;
	
	/** The match route. */
	@SerializedName("match_route")
	public String matchRoute;
	
	/** The source label. */
	@SerializedName("source_label")
	public String sourceLabel;
	
	/** The updated at str. */
	@SerializedName("updated_at")
	public String updatedAtStr;
	
	/** The updated at. */
	@ParseDate("updatedAtStr")
	public Date updatedAt;
	
	/** The undone at str. */
	@SerializedName("undone_at")
	public String undoneAtStr;
	
	/** The undone at. */
	@ParseDate("undoneAtStr")
	public Date undoneAt;
	
	/** The stopped at str. */
	@SerializedName("stopped_at")
	public String stoppedAtStr;
	
	/** The stopped at. */
	@ParseDate("stoppedAtStr")
	public Date stoppedAt;

	/** The undo started at str. */
	@SerializedName("undo_started_at")
	public String undoStartedAtStr;
	
	/** The undo started at. */
	@ParseDate("undoStartedAtStr")
	public Date undoStartedAt;
	
	/** The finished at str. */
	@SerializedName("finished_at")
	public String finishedAtStr;
	
	/** The finished at. */
	@ParseDate("finishedAtStr")
	public Date finishedAt;
}
