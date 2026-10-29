/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.emails;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
}
