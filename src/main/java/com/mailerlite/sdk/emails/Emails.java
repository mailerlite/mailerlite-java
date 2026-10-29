/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.emails;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.MailerLiteResponse;
import com.mailerlite.sdk.RequestOptions;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.util.RequestChecks;

/**
 * The endpoints for emails built with the simple editor.
 * <p>
 * Each write must send the etag from the last response for the email, and each write returns a new etag.
 * The etag covers the whole email, so send the writes to one email one after another. If the email changed
 * after you read it, the write throws a {@link MailerLiteException} with
 * {@link MailerLiteException#isVersionConflict()} set to true and the current etag in
 * {@link MailerLiteException#etag}.
 * <p>
 * The methods check the IDs and the etag before they send a request. An empty or null ID throws
 * {@link com.mailerlite.sdk.exceptions.EmptyIdException}. An ID that contains a character other than the
 * digits 0 to 9 throws {@link com.mailerlite.sdk.exceptions.InvalidIdException}.
 * <p>
 * The writes, createTemplate and createVersion have an overload with {@link RequestOptions} as the last argument.
 * Use it to send an idempotency key, so that you can safely send a write again after a network error.
 */
public class Emails {

	private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

	/** Writes the trees that GSON makes, with the null values of the editor content maps. */
	private static final Gson TREE_GSON = new GsonBuilder().disableHtmlEscaping().serializeNulls().create();

	private MailerLite apiObjectReference;

	/**
	 * Instantiates a new emails service.
	 *
	 * @param apiReference the api reference
	 */
	public Emails(MailerLite apiReference)
	{
		apiObjectReference = apiReference;
	}

	/**
	 * Gets an email with its design settings and its blocks.
	 *
	 * @param emailId the email ID
	 * @return the email and its etag
	 * @throws MailerLiteException if the API returns an error or the request fails
	 */
	public SingleEmailDocument get(String emailId) throws MailerLiteException
	{
		return send("GET", emailPath(emailId), null, SingleEmailDocument.class);
	}

	/**
	 * Gets the HTML of an email, as a preview. The HTML is not ready to send.
	 * <p>
	 * The API can return a temporary 503 error with a body that is not JSON. If it does, try again later.
	 *
	 * @param emailId the email ID
	 * @return the HTML and the etag of the email
	 * @throws MailerLiteException if the API returns an error or the request fails
	 */
	public SingleEmailHtml getHtml(String emailId) throws MailerLiteException
	{
		return send("GET", emailPath(emailId).concat("/html"), null, SingleEmailHtml.class);
	}

	/**
	 * Gets one block of an email, with its placement.
	 *
	 * @param emailId the email ID
	 * @param blockId the block ID
	 * @return the block and the etag of the email
	 * @throws MailerLiteException if the API returns an error or the request fails
	 */
	public SingleEmailBlock getBlock(String emailId, String blockId) throws MailerLiteException
	{
		return send("GET", blockPath(emailId, blockId), null, SingleEmailBlock.class);
	}

	/**
	 * Inserts a block into an email.
	 * <p>
	 * Without a placement, the API puts the block at the end of the email. The response has the new block with
	 * its placement and the new etag.
	 *
	 * @param emailId the email ID
	 * @param etag the etag from the last response for the email
	 * @param block the block to insert
	 * @return the new block and the new etag
	 * @throws MailerLiteException if the API returns an error or the request fails
	 * @throws NullPointerException if the block or its editor content is null
	 * @throws IllegalArgumentException if the block type or the placement position is empty
	 */
	public SingleEmailBlock createBlock(String emailId, String etag, EmailBlockInput block) throws MailerLiteException
	{
		return createBlock(emailId, etag, block, null);
	}

	/**
	 * Inserts a block into an email.
	 * <p>
	 * Without a placement, the API puts the block at the end of the email. The response has the new block with
	 * its placement and the new etag.
	 *
	 * @param emailId the email ID
	 * @param etag the etag from the last response for the email
	 * @param block the block to insert
	 * @param options the request options, for example an idempotency key, or null for no options
	 * @return the new block and the new etag
	 * @throws MailerLiteException if the API returns an error or the request fails
	 * @throws NullPointerException if the block or its editor content is null
	 * @throws IllegalArgumentException if the block type or the placement position is empty
	 */
	public SingleEmailBlock createBlock(String emailId, String etag, EmailBlockInput block, RequestOptions options)
			throws MailerLiteException
	{
		String endpoint = emailPath(emailId).concat("/blocks");
		BlockBody body = blockBody(block, RequestChecks.etag(etag));

		return send("POST", endpoint, toJson(body), options, SingleEmailBlock.class);
	}

	/**
	 * Replaces the content of a block. The type and the position of the block do not change.
	 * <p>
	 * The new editor content replaces the whole content of the block. The API removes the keys that you do not
	 * send. Read the block, change it, and send the full editor content.
	 *
	 * @param emailId the email ID
	 * @param blockId the block ID
	 * @param etag the etag from the last response for the email
	 * @param editorContent the new editor document of the block. The keys are camelCase.
	 * @return the block and the new etag
	 * @throws MailerLiteException if the API returns an error or the request fails
	 * @throws NullPointerException if the editor content is null
	 */
	public SingleEmailBlock updateBlock(String emailId, String blockId, String etag, Map<String, Object> editorContent)
			throws MailerLiteException
	{
		return updateBlock(emailId, blockId, etag, editorContent, null);
	}

	/**
	 * Replaces the content of a block. The type and the position of the block do not change.
	 * <p>
	 * The new editor content replaces the whole content of the block. The API removes the keys that you do not
	 * send. Read the block, change it, and send the full editor content.
	 *
	 * @param emailId the email ID
	 * @param blockId the block ID
	 * @param etag the etag from the last response for the email
	 * @param editorContent the new editor document of the block. The keys are camelCase.
	 * @param options the request options, for example an idempotency key, or null for no options
	 * @return the block and the new etag
	 * @throws MailerLiteException if the API returns an error or the request fails
	 * @throws NullPointerException if the editor content is null
	 */
	public SingleEmailBlock updateBlock(String emailId, String blockId, String etag, Map<String, Object> editorContent,
			RequestOptions options)
			throws MailerLiteException
	{
		String endpoint = blockPath(emailId, blockId);

		UpdateBlockBody body = new UpdateBlockBody();
		body.etag = RequestChecks.etag(etag);
		body.editorContent = Objects.requireNonNull(editorContent, "editorContent");

		return send("PATCH", endpoint, toJson(body), options, SingleEmailBlock.class);
	}

	/**
	 * Deletes a block of an email. The API also deletes the blocks in it.
	 *
	 * @param emailId the email ID
	 * @param blockId the block ID
	 * @param etag the etag from the last response for the email
	 * @return the new etag
	 * @throws MailerLiteException if the API returns an error or the request fails
	 */
	public EmailEtagResponse deleteBlock(String emailId, String blockId, String etag) throws MailerLiteException
	{
		return deleteBlock(emailId, blockId, etag, null);
	}

	/**
	 * Deletes a block of an email. The API also deletes the blocks in it.
	 *
	 * @param emailId the email ID
	 * @param blockId the block ID
	 * @param etag the etag from the last response for the email
	 * @param options the request options, for example an idempotency key, or null for no options
	 * @return the new etag
	 * @throws MailerLiteException if the API returns an error or the request fails
	 */
	public EmailEtagResponse deleteBlock(String emailId, String blockId, String etag, RequestOptions options)
			throws MailerLiteException
	{
		String endpoint = blockPath(emailId, blockId);

		return send("DELETE", endpoint, etagBody(etag), options, EmailEtagResponse.class);
	}

	/**
	 * Moves a block to a new position at the same level. The nesting of the block does not change.
	 * <p>
	 * The positions {@link EmailBlockPositions#BEFORE} and {@link EmailBlockPositions#AFTER} need a block ID.
	 *
	 * @param emailId the email ID
	 * @param blockId the ID of the block to move
	 * @param etag the etag from the last response for the email
	 * @param placement the new position
	 * @return the block with its new placement, and the new etag
	 * @throws MailerLiteException if the API returns an error or the request fails
	 * @throws NullPointerException if the placement is null
	 * @throws IllegalArgumentException if the placement position is empty
	 */
	public SingleEmailBlock moveBlock(String emailId, String blockId, String etag, EmailBlockPosition placement)
			throws MailerLiteException
	{
		return moveBlock(emailId, blockId, etag, placement, null);
	}

	/**
	 * Moves a block to a new position at the same level. The nesting of the block does not change.
	 * <p>
	 * The positions {@link EmailBlockPositions#BEFORE} and {@link EmailBlockPositions#AFTER} need a block ID.
	 *
	 * @param emailId the email ID
	 * @param blockId the ID of the block to move
	 * @param etag the etag from the last response for the email
	 * @param placement the new position
	 * @param options the request options, for example an idempotency key, or null for no options
	 * @return the block with its new placement, and the new etag
	 * @throws MailerLiteException if the API returns an error or the request fails
	 * @throws NullPointerException if the placement is null
	 * @throws IllegalArgumentException if the placement position is empty
	 */
	public SingleEmailBlock moveBlock(String emailId, String blockId, String etag, EmailBlockPosition placement,
			RequestOptions options)
			throws MailerLiteException
	{
		String endpoint = blockPath(emailId, blockId).concat("/move");

		MoveBlockBody body = new MoveBlockBody();
		body.etag = RequestChecks.etag(etag);
		body.placement = placementBody(Objects.requireNonNull(placement, "placement"));

		return send("POST", endpoint, toJson(body), options, SingleEmailBlock.class);
	}

	/**
	 * Checks a block with the rules of an insert, and saves nothing. It needs no email and no etag.
	 * <p>
	 * An invalid block is not an error: the response has valid set to false and the errors for each field.
	 *
	 * @param block the block to check
	 * @return the result of the validation
	 * @throws MailerLiteException if the API returns an error or the request fails
	 * @throws NullPointerException if the block or its editor content is null
	 * @throws IllegalArgumentException if the block type or the placement position is empty
	 */
	public EmailValidation validateBlock(EmailBlockInput block) throws MailerLiteException
	{
		return send("POST", "/emails/blocks/validate", toJson(blockBody(block, null)), EmailValidation.class);
	}

	/**
	 * Changes the design settings of an email.
	 * <p>
	 * Set only the fields that you want to change. A null field is not sent and does not change. To unset a brand
	 * color, send an empty string. To unset a footer text, send an empty editor document. The email then uses the
	 * value of your account. For the other values, the API rejects an empty string with a 422 error. The API stores
	 * each value that you send, also a value that the email inherits from your account. So do not send back all the
	 * settings that you read.
	 *
	 * @param emailId the email ID
	 * @param etag the etag from the last response for the email
	 * @param settings the settings to change
	 * @return the settings after the update, the new etag, and the unsafe values that the API removed
	 * @throws MailerLiteException if the API returns an error or the request fails
	 * @throws NullPointerException if the settings are null
	 */
	public SingleEmailSettings updateSettings(String emailId, String etag, EmailSettings settings)
			throws MailerLiteException
	{
		return updateSettings(emailId, etag, settings, null);
	}

	/**
	 * Changes the design settings of an email.
	 * <p>
	 * Set only the fields that you want to change. A null field is not sent and does not change. To unset a brand
	 * color, send an empty string. To unset a footer text, send an empty editor document. The email then uses the
	 * value of your account. For the other values, the API rejects an empty string with a 422 error. The API stores
	 * each value that you send, also a value that the email inherits from your account. So do not send back all the
	 * settings that you read.
	 *
	 * @param emailId the email ID
	 * @param etag the etag from the last response for the email
	 * @param settings the settings to change
	 * @param options the request options, for example an idempotency key, or null for no options
	 * @return the settings after the update, the new etag, and the unsafe values that the API removed
	 * @throws MailerLiteException if the API returns an error or the request fails
	 * @throws NullPointerException if the settings are null
	 */
	public SingleEmailSettings updateSettings(String emailId, String etag, EmailSettings settings, RequestOptions options)
			throws MailerLiteException
	{
		String endpoint = emailPath(emailId).concat("/settings");
		String checkedEtag = RequestChecks.etag(etag);

		JsonObject body = settingsBody(settings);
		body.addProperty("etag", checkedEtag);

		return send("PATCH", endpoint, TREE_GSON.toJson(body), options, SingleEmailSettings.class);
	}

	/**
	 * Checks a change to the design settings with the rules of an update, and saves nothing. It needs no etag.
	 * <p>
	 * Invalid settings are not an error: the response has valid set to false and the errors for each field. If the
	 * update would remove unsafe values, the response has their paths in rejected.
	 *
	 * @param emailId the email ID
	 * @param settings the settings to check
	 * @return the result of the validation
	 * @throws MailerLiteException if the API returns an error or the request fails
	 * @throws NullPointerException if the settings are null
	 */
	public EmailValidation validateSettings(String emailId, EmailSettings settings) throws MailerLiteException
	{
		String endpoint = emailPath(emailId).concat("/settings/validate");

		return send("POST", endpoint, TREE_GSON.toJson(settingsBody(settings)), EmailValidation.class);
	}

	/**
	 * Copies an email to a new template. The API cannot delete templates.
	 *
	 * @param emailId the email ID
	 * @param name the name of the new template
	 * @return the new template
	 * @throws MailerLiteException if the API returns an error or the request fails
	 * @throws IllegalArgumentException if the name is null or empty
	 */
	public SingleEmailTemplate createTemplate(String emailId, String name) throws MailerLiteException
	{
		return createTemplate(emailId, name, null);
	}

	/**
	 * Copies an email to a new template. The API cannot delete templates.
	 *
	 * @param emailId the email ID
	 * @param name the name of the new template
	 * @param options the request options, for example an idempotency key, or null for no options
	 * @return the new template
	 * @throws MailerLiteException if the API returns an error or the request fails
	 * @throws IllegalArgumentException if the name is null or empty
	 */
	public SingleEmailTemplate createTemplate(String emailId, String name, RequestOptions options)
			throws MailerLiteException
	{
		String endpoint = emailPath(emailId).concat("/templates");

		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("name must not be empty");
		}

		TemplateBody body = new TemplateBody();
		body.name = name;

		return send("POST", endpoint, toJson(body), options, SingleEmailTemplate.class);
	}

	/**
	 * Lists the versions of an email, newest first. Call get() on the result, after page() and limit() if you
	 * want them.
	 *
	 * @param emailId the email ID
	 * @return the retriever of the versions
	 */
	public EmailVersionsRetriever versions(String emailId)
	{
		return new EmailVersionsRetriever(apiObjectReference, emailPath(emailId).concat("/versions"));
	}

	/**
	 * Saves the email as a new version.
	 * <p>
	 * If nothing changed after the last version, the API saves no new version and returns the last version.
	 * {@link SingleEmailVersion#isCreated()} tells the two cases apart.
	 *
	 * @param emailId the email ID
	 * @return the new version, or the last version
	 * @throws MailerLiteException if the API returns an error or the request fails
	 */
	public SingleEmailVersion createVersion(String emailId) throws MailerLiteException
	{
		return createVersion(emailId, null);
	}

	/**
	 * Saves the email as a new version.
	 * <p>
	 * If nothing changed after the last version, the API saves no new version and returns the last version.
	 * {@link SingleEmailVersion#isCreated()} tells the two cases apart.
	 *
	 * @param emailId the email ID
	 * @param options the request options, for example an idempotency key, or null for no options
	 * @return the new version, or the last version
	 * @throws MailerLiteException if the API returns an error or the request fails
	 */
	public SingleEmailVersion createVersion(String emailId, RequestOptions options) throws MailerLiteException
	{
		return send("POST", emailPath(emailId).concat("/versions"), null, options, SingleEmailVersion.class);
	}

	/**
	 * Puts an email back to a version, and returns the full email with the new etag.
	 * <p>
	 * Before the restore, the API saves the current state as a version, so you can undo the restore.
	 *
	 * @param emailId the email ID
	 * @param versionId the version ID
	 * @param etag the etag from the last response for the email
	 * @return the email and the new etag
	 * @throws MailerLiteException if the API returns an error or the request fails
	 */
	public SingleEmailDocument restoreVersion(String emailId, String versionId, String etag) throws MailerLiteException
	{
		return restoreVersion(emailId, versionId, etag, null);
	}

	/**
	 * Puts an email back to a version, and returns the full email with the new etag.
	 * <p>
	 * Before the restore, the API saves the current state as a version, so you can undo the restore.
	 *
	 * @param emailId the email ID
	 * @param versionId the version ID
	 * @param etag the etag from the last response for the email
	 * @param options the request options, for example an idempotency key, or null for no options
	 * @return the email and the new etag
	 * @throws MailerLiteException if the API returns an error or the request fails
	 */
	public SingleEmailDocument restoreVersion(String emailId, String versionId, String etag, RequestOptions options)
			throws MailerLiteException
	{
		String endpoint = emailPath(emailId).concat("/versions/")
				.concat(RequestChecks.pathId(versionId, "versionId")).concat("/restore");

		return send("POST", endpoint, etagBody(etag), options, SingleEmailDocument.class);
	}

	private static String emailPath(String emailId)
	{
		return "/emails/".concat(RequestChecks.pathId(emailId, "emailId"));
	}

	private static String blockPath(String emailId, String blockId)
	{
		return emailPath(emailId).concat("/blocks/").concat(RequestChecks.pathId(blockId, "blockId"));
	}

	private static BlockBody blockBody(EmailBlockInput block, String etag)
	{
		Objects.requireNonNull(block, "block");

		if (block.type == null || block.type.isEmpty()) {
			throw new IllegalArgumentException("block.type must not be empty");
		}

		BlockBody body = new BlockBody();
		body.type = block.type;
		body.editorContent = Objects.requireNonNull(block.editorContent, "block.editorContent");
		body.placement = block.placement == null ? null : placementBody(block.placement);
		body.etag = etag;

		return body;
	}

	/**
	 * The API uses the block ID only for "before" and "after". So the copy has no block ID for "start" and "end".
	 */
	private static PlacementBody placementBody(EmailBlockPosition placement)
	{
		String position = placement.position;

		if (position == null || position.isEmpty()) {
			throw new IllegalArgumentException("placement.position must not be empty");
		}

		if (EmailBlockPositions.START.equals(position) || EmailBlockPositions.END.equals(position)) {
			return new PlacementBody(position, null);
		}

		if (EmailBlockPositions.BEFORE.equals(position) || EmailBlockPositions.AFTER.equals(position)
				|| placement.blockId != null) {
			RequestChecks.pathId(placement.blockId, "placement.blockId");
		}

		return new PlacementBody(position, placement.blockId);
	}

	private static String etagBody(String etag)
	{
		EtagBody body = new EtagBody();
		body.etag = RequestChecks.etag(etag);

		return toJson(body);
	}

	private static JsonObject settingsBody(EmailSettings settings)
	{
		return GSON.toJsonTree(Objects.requireNonNull(settings, "settings")).getAsJsonObject();
	}

	private static String toJson(Object body)
	{
		return GSON.toJson(body);
	}

	private <T extends MailerLiteResponse> T send(String method, String endpoint, String body, Class<T> responseClass)
			throws MailerLiteException
	{
		return send(method, endpoint, body, null, responseClass);
	}

	private <T extends MailerLiteResponse> T send(String method, String endpoint, String body, RequestOptions options,
			Class<T> responseClass) throws MailerLiteException
	{
		Map<String, String> headers = null;

		if (options != null && options.getIdempotencyKey() != null) {
			headers = Collections.singletonMap("Idempotency-Key", options.getIdempotencyKey());
		}

		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());

		return api.sendRequest(method, endpoint, body, headers, responseClass);
	}

	private static class BlockBody {

		@SerializedName("type")
		String type;

		@SerializedName("editor_content")
		@JsonAdapter(EditorContentAdapter.class)
		Map<String, Object> editorContent;

		@SerializedName("placement")
		PlacementBody placement;

		@SerializedName("etag")
		String etag;
	}

	private static class UpdateBlockBody {

		@SerializedName("editor_content")
		@JsonAdapter(EditorContentAdapter.class)
		Map<String, Object> editorContent;

		@SerializedName("etag")
		String etag;
	}

	private static class EtagBody {

		@SerializedName("etag")
		String etag;
	}

	private static class MoveBlockBody {

		@SerializedName("placement")
		PlacementBody placement;

		@SerializedName("etag")
		String etag;
	}

	private static class TemplateBody {

		@SerializedName("name")
		String name;
	}

	private static class PlacementBody {

		@SerializedName("position")
		final String position;

		@SerializedName("block_id")
		final String blockId;

		PlacementBody(String position, String blockId)
		{
			this.position = position;
			this.blockId = blockId;
		}
	}
}
