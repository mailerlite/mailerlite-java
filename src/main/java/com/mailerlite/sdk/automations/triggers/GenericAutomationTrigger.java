/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.triggers;

import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import com.mailerlite.sdk.util.RawDataAdapterFactory;
import com.mailerlite.sdk.util.RawDataHolder;

/**
 * An automation trigger of any type. The type specific fields are in {@link #data}.
 */
@JsonAdapter(RawDataAdapterFactory.class)
public class GenericAutomationTrigger extends AutomationTrigger implements RawDataHolder {

	/** The subscriber joins group trigger type. */
	public static final String TYPE_SUBSCRIBER_JOINS_GROUP = "subscriber_joins_group";

	/** The subscriber joins segment trigger type. */
	public static final String TYPE_SUBSCRIBER_JOINS_SEGMENT = "subscriber_joins_segment";

	/** The clicks link trigger type. */
	public static final String TYPE_CLICKS_LINK = "clicks_link";

	/** The field updated trigger type. */
	public static final String TYPE_FIELD_UPDATED = "field_updated";

	/** The anniversary trigger type. */
	public static final String TYPE_ANNIVERSARY = "anniversary";

	/** The exact date trigger type. */
	public static final String TYPE_EXACT_DATE = "exact_date";

	/** The form completed trigger type. */
	public static final String TYPE_FORM_COMPLETED = "form_completed";

	/** The manual trigger type. */
	public static final String TYPE_MANUAL = "manual";

	/** The purchased any product trigger type. */
	public static final String TYPE_PURCHASED_ANY_PRODUCT = "purchased_any_product";

	/** The purchased specific product trigger type. */
	public static final String TYPE_PURCHASED_SPECIFIC_PRODUCT = "purchased_specific_product";

	/** The purchased from category trigger type. */
	public static final String TYPE_PURCHASED_FROM_CATEGORY = "purchased_from_category";

	/** The abandoned cart trigger type. */
	public static final String TYPE_ABANDONED_CART = "abandoned_cart";

	/** The abandoned checkout trigger type. */
	public static final String TYPE_ABANDONED_CHECKOUT = "abandoned_checkout";

	/** The purchase frequency trigger type. */
	public static final String TYPE_PURCHASE_FREQUENCY = "purchase_frequency";

	/** The raw trigger object, including the type specific fields. */
	public transient JsonObject data;

	/**
	 * Sets the raw trigger object.
	 *
	 * @param data the raw trigger object
	 */
	@Override
	public void setData(JsonObject data) {
		this.data = data;
	}
}
