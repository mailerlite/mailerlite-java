package com.mailerlite.sdk.susbcribers;

import java.time.LocalDateTime;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.ApiResource;
import com.mailerlite.sdk.util.ParseDate;

public class SubscriberImport extends ApiResource {

	@SerializedName("id")
	public String id;
	
	@SerializedName("total")
	public int total;
	
	@SerializedName("processed")
	public int processed;
	
	@SerializedName("imported")
	public int imported;
	
	@SerializedName("updated")
	public int updated;
	
	@SerializedName("errored")
	public int errored;
	
	@SerializedName("percent")
	public float percent;
	
	@SerializedName("done")
	public boolean done;
	
	@SerializedName("file_path")
	public String filePath;
	
	@SerializedName("invalid")
	public SubscriberImportStatusEntry[] invalid;
	
	@SerializedName("invalid_count")
	public int invalidCount;
	
	@SerializedName("mistyped")
	public SubscriberImportStatusEntry[] mistyped;
	
	@SerializedName("mistyped_count")
	public int mistypedCount;
	
	@SerializedName("changed")
	public SubscriberImportStatusEntry[] changed;
	
	@SerializedName("changed_count")
	public int changedCount;
	
	@SerializedName("unchanged")
	public SubscriberImportStatusEntry[] unchanged;
	
	@SerializedName("unchanged_count")
	public int unchangedCount;
	
	@SerializedName("unsubscribed")
	public SubscriberImportStatusEntry[] unsubscribed;
	
	@SerializedName("unsubscribed_count")
	public int unsubscribedCount;
	
	@SerializedName("role_based")
	public SubscriberImportStatusEntry[] roleBased;
	
	@SerializedName("role_based_count")
	public int roleBasedCount;
	
	@SerializedName("banned_import_emails_count")
	public int bannedImportEmailsCount;
	
	@SerializedName("match_route")
	public String matchRoute;
	
	@SerializedName("source_label")
	public String sourceLabel;
	
	@SerializedName("updated_at")
	public String updatedAtStr;
	
	@ParseDate("updatedAtStr")
	public LocalDateTime updatedAt;
	
	@SerializedName("undone_at")
	public String undoneAtStr;
	
	@ParseDate("undoneAtStr")
	public LocalDateTime undoneAt;
	
	@SerializedName("stopped_at")
	public String stoppedAtStr;
	
	@ParseDate("stoppedAtStr")
	public LocalDateTime stoppedAt;

	@SerializedName("undo_started_at")
	public String undoStartedAtStr;
	
	@ParseDate("undoStartedAtStr")
	public LocalDateTime undoStartedAt;
	
	@SerializedName("finished_at")
	public String finishedAtStr;
	
	@ParseDate("finishedAtStr")
	public LocalDateTime finishedAt;
}
