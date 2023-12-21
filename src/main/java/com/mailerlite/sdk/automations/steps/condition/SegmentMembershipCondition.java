/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.steps.condition;

import com.google.gson.annotations.SerializedName;

/**
 * The Class SegmentMembershipCondition.
 */
public class SegmentMembershipCondition extends ConditionBase {

	/** The segment id. */
	@SerializedName("segment_id")
	public String segmentId;
	
	/** The segment. */
	@SerializedName("segment")
	public WorkflowConditionIdName segment;
}
