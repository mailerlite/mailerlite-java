/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.support;

import com.mailerlite.sdk.MailerLite;

public class ReadmeExampleBase {

	public MailerLite getMailerLite()
	{
		MailerLite ml = new MailerLite();
		ml.setToken("test-token");

		return ml;
	}
}
