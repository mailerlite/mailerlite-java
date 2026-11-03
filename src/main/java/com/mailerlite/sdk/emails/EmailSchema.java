/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.emails;

import com.google.gson.annotations.SerializedName;

/**
 * The version of the rules that a validation used.
 */
public class EmailSchema {

	/** The version of the rules, for example "2.73.2". */
	@SerializedName("version")
	public String version;

	/** The hash of the rules. */
	@SerializedName("hash")
	public String hash;
}
