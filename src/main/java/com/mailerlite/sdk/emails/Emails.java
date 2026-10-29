/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.emails;

import java.util.Map;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.MailerLiteResponse;
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
 */
public class Emails {

	/** The Gson object for the request bodies. */
	private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

	/** The api object reference. */
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
		String endpoint = emailPath(emailId).concat("/blocks");
		BlockBody body = blockBody(block, RequestChecks.etag(etag));

		return send("POST", endpoint, toJson(body), SingleEmailBlock.class);
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
		String endpoint = blockPath(emailId, blockId);

		UpdateBlockBody body = new UpdateBlockBody();
		body.etag = RequestChecks.etag(etag);
		body.editorContent = Objects.requireNonNull(editorContent, "editorContent");

		return send("PATCH", endpoint, toJson(body), SingleEmailBlock.class);
	}

	/**
	 * Checks the email ID and makes the path of the email.
	 *
	 * @param emailId the email ID
	 * @return the path
	 */
	private static String emailPath(String emailId)
	{
		return "/emails/".concat(RequestChecks.pathId(emailId, "emailId"));
	}

	/**
	 * Checks the email ID and the block ID, and makes the path of the block.
	 *
	 * @param emailId the email ID
	 * @param blockId the block ID
	 * @return the path
	 */
	private static String blockPath(String emailId, String blockId)
	{
		return emailPath(emailId).concat("/blocks/").concat(RequestChecks.pathId(blockId, "blockId"));
	}

	/**
	 * Checks a block input and makes the request body.
	 *
	 * @param block the block input
	 * @param etag the etag, or null for no etag
	 * @return the request body
	 */
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
	 * Checks a placement and makes a copy to send.
	 * <p>
	 * The API uses the block ID only for "before" and "after". So for these positions the block ID must be valid.
	 * For "start" and "end", the copy has no block ID. For another position, the block ID is checked if it is set.
	 *
	 * @param placement the placement of the caller
	 * @return the copy to send
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

	/**
	 * Converts a request body to JSON.
	 *
	 * @param body the body
	 * @return the JSON
	 */
	private static String toJson(Object body)
	{
		return GSON.toJson(body);
	}

	/**
	 * Sends a request.
	 *
	 * @param <T> the response type
	 * @param method the HTTP method
	 * @param endpoint the endpoint
	 * @param body the JSON body, or null
	 * @param responseClass the response class
	 * @return the response
	 * @throws MailerLiteException if the API returns an error or the request fails
	 */
	private <T extends MailerLiteResponse> T send(String method, String endpoint, String body, Class<T> responseClass)
			throws MailerLiteException
	{
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());

		return api.sendRequest(method, endpoint, body, null, responseClass);
	}

	/**
	 * The request body of a block insert or a block validation.
	 */
	private static class BlockBody {

		/** The block type. */
		@SerializedName("type")
		String type;

		/** The editor document. */
		@SerializedName("editor_content")
		@JsonAdapter(EditorContentAdapter.class)
		Map<String, Object> editorContent;

		/** The placement, or null. */
		@SerializedName("placement")
		PlacementBody placement;

		/** The etag, or null. */
		@SerializedName("etag")
		String etag;
	}

	/**
	 * The request body of a block update.
	 */
	private static class UpdateBlockBody {

		/** The editor document. */
		@SerializedName("editor_content")
		@JsonAdapter(EditorContentAdapter.class)
		Map<String, Object> editorContent;

		/** The etag. */
		@SerializedName("etag")
		String etag;
	}

	/**
	 * The placement in a request body.
	 */
	private static class PlacementBody {

		/** The position. */
		@SerializedName("position")
		final String position;

		/** The block ID, or null. */
		@SerializedName("block_id")
		final String blockId;

		/**
		 * Instantiates a new placement body.
		 *
		 * @param position the position
		 * @param blockId the block ID, or null
		 */
		PlacementBody(String position, String blockId)
		{
			this.position = position;
			this.blockId = blockId;
		}
	}
}
