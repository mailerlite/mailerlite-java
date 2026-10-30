/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.emails;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.MailerLiteResponse;

/**
 * The result of a validation.
 *
 * An invalid input is not an error: the status is 200, valid is false, and errors has the messages for each field.
 */
public class EmailValidation extends MailerLiteResponse {

	/** True if the input is valid. */
	@SerializedName("valid")
	public boolean valid;

	/** The error messages for each field path. It is empty if the input is valid. */
	@SerializedName("errors")
	public HashMap<String, String[]> errors;

	/** The version of the rules that the validation used. */
	@SerializedName("schema")
	public EmailSchema schema;

	/**
	 * The unsafe values that an update would remove, or null if there are none. Only the validation of settings has it.
	 */
	@SerializedName("rejected")
	public EmailRejected rejected;
}
