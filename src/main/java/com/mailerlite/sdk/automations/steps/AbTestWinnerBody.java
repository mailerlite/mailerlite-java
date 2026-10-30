/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.steps;

import com.google.gson.annotations.SerializedName;

/**
 * The Class AbTestWinnerBody.
 */
public class AbTestWinnerBody {

	/** The ab test winner. */
	@SerializedName("ab_test_winner")
	public String abTestWinner;
}
