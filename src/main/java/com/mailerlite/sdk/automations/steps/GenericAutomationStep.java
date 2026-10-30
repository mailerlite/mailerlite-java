/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.steps;

import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.RawDataAdapterFactory;
import com.mailerlite.sdk.util.RawDataHolder;

/**
 * An automation step of any type. The type specific fields are in {@link #data}.
 */
@JsonAdapter(RawDataAdapterFactory.class)
public class GenericAutomationStep extends AutomationStep implements RawDataHolder {

	/** The email step type. */
	public static final String TYPE_EMAIL = "email";

	/** The condition step type. */
	public static final String TYPE_CONDITION = "condition";

	/** The delay step type. */
	public static final String TYPE_DELAY = "delay";

	/** The action step type. */
	public static final String TYPE_ACTION = "action";

	/** The A/B test step type. */
	public static final String TYPE_AB_TEST = "abtest";

	/** The webhook step type. */
	public static final String TYPE_WEBHOOK = "webhook";

	/** The yes branch of a condition. */
	public static final String BRANCH_YES = "yes";

	/** The no branch of a condition. */
	public static final String BRANCH_NO = "no";

	/** The A path of an A/B test. */
	public static final String BRANCH_A_PATH = "a_path";

	/** The B path of an A/B test. */
	public static final String BRANCH_B_PATH = "b_path";

	/** The C path of an A/B test. */
	public static final String BRANCH_C_PATH = "c_path";

	/** The update custom field action type. */
	public static final String ACTION_TYPE_UPDATE_CUSTOM_FIELD = "update_custom_field";

	/** The mark as unsubscribed action type. */
	public static final String ACTION_TYPE_MARK_AS_UNSUBSCRIBED = "mark_as_unsubscribed";

	/** The remove from group action type. */
	public static final String ACTION_TYPE_REMOVE_FROM_GROUP = "remove_from_group";

	/** The go to step action type. */
	public static final String ACTION_TYPE_GO_TO_STEP = "go_to_step";

	/** The copy to group action type. */
	public static final String ACTION_TYPE_COPY_TO_GROUP = "copy_to_group";

	/** The move to group action type. */
	public static final String ACTION_TYPE_MOVE_TO_GROUP = "move_to_group";

	/** The send internal email notification action type. */
	public static final String ACTION_TYPE_SEND_INTERNAL_EMAIL_NOTIFICATION = "send_internal_email_notification";

	/** The action type, set for action steps. */
	@SerializedName("action_type")
	public String actionType;

	/** The raw step object, including the type specific fields. */
	public transient JsonObject data;

	/**
	 * Sets the raw step object.
	 *
	 * @param data the raw step object
	 */
	@Override
	public void setData(JsonObject data) {
		this.data = data;
	}
}
