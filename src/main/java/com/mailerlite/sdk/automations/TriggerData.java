/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations;

import com.google.gson.annotations.SerializedName;

/**
 * The Class TriggerData.
 */
public class TriggerData {

	/** The track ecommerce. */
	@SerializedName("track_ecommerce")
	public Boolean trackEcommerce;
	
	/** The repeatable. */
	@SerializedName("repeatable")
	public Boolean repeatable;
	
	/** The valid. */
	@SerializedName("valid")
	public Boolean valid;
}
