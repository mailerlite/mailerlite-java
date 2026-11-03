/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.groups;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/**
 * The Class GroupImportBody.
 */
public class GroupImportBody {

	/** The resubscribe. */
	@SerializedName("resubscribe")
	public Boolean resubscribe;
	
	/** The trigger automations. */
	@SerializedName("trigger_automations")
	public Boolean triggerAutomations;
	
	/** The subscribers. */
	@SerializedName("subscribers")
	public ArrayList<GroupImportSubscriber> subscribers = new ArrayList<GroupImportSubscriber>();
}
