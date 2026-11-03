/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.templates;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.PaginatedResponse;

/**
 * The Class AutomationTemplateList.
 */
public class AutomationTemplateList extends PaginatedResponse {

	/** The templates. */
	@SerializedName("data")
	public AutomationTemplate[] templates;
}
