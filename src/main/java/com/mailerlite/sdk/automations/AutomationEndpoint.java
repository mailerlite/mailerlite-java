/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations;

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;

/**
 * Builds the API client and the endpoints under one automation.
 */
public class AutomationEndpoint {

	/** The api object reference. */
	private MailerLite apiObjectReference;

	/** The automation id. */
	private String automationId;

	/**
	 * Instantiates a new automation endpoint.
	 *
	 * @param apiRef the api ref
	 * @param forAutomationId the automation id
	 */
	public AutomationEndpoint(MailerLite apiRef, String forAutomationId)
	{
		apiObjectReference = apiRef;
		automationId = forAutomationId;
	}

	/**
	 * Creates an API client with the token set.
	 *
	 * @return the mailer lite api
	 */
	public MailerLiteApi api()
	{
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());

		return api;
	}

	/**
	 * Path under the automation, for example path("/steps") gives /automations/{id}/steps.
	 *
	 * @param suffix the suffix
	 * @return the path
	 */
	public String path(String suffix)
	{
		return "/automations/".concat(automationId).concat(suffix);
	}
}
