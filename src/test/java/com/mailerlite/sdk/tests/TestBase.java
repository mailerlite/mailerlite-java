package com.mailerlite.sdk.tests;

import com.mailerlite.sdk.MailerLite;

public class TestBase {

	public MailerLite getMailerLite()
	{
		MailerLite ml = new MailerLite();
		ml.setToken(TestHelper.validToken);
		
		return ml;
	}
	
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
