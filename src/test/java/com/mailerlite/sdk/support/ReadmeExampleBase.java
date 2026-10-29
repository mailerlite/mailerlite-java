/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.support;

import com.mailerlite.sdk.MailerLite;

/**
 * The base class of the README examples in ReadmeExamplesTest. It gives getMailerLite(), as the
 * Usage section of the README shows.
 */
public class ReadmeExampleBase {

	/**
	 * Gets a MailerLite object with a test token.
	 *
	 * @return the MailerLite object
	 */
	public MailerLite getMailerLite()
	{
		MailerLite ml = new MailerLite();
		ml.setToken("test-token");

		return ml;
	}
}
