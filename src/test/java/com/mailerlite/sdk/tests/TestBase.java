/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.tests;

import com.mailerlite.sdk.MailerLite;

/**
 * The Class TestBase.
 */
public class TestBase {

	/**
	 * Gets the mailer lite.
	 *
	 * @return the mailer lite
	 */
	public MailerLite getMailerLite()
	{
		MailerLite ml = new MailerLite();
		ml.setToken(TestHelper.validToken);
		
		return ml;
	}
	
	/**
	 * Gets the mailer lite.
	 *
	 * @param invalidToken the invalid token
	 * @return the mailer lite
	 */
	public MailerLite getMailerLite(Boolean invalidToken)
	{
		if (!invalidToken) {
			
			return getMailerLite();
		}
		
		MailerLite ml = new MailerLite();
		ml.setToken("invalid token");
		
		return ml;
	}
}
