/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * The Class AutomationActivityActionBody.
 */
public class AutomationActivityActionBody {

	/** The destination groups. */
	@SerializedName("destination_groups")
	public List<String> destinationGroups;
	
	/** The filter. */
	@SerializedName("filter")
	public AutomationActivityFilter filter;
}
