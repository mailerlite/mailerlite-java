/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.triggers;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.ecommerce.EcommerceShop;

/**
 * The Class TriggerPurchasedAnyProduct.
 */
public class TriggerPurchasedAnyProduct extends AutomationTrigger {

	/** The ecommerce shop id. */
	@SerializedName("ecommerce_shop_id")
	public String ecommerceShopId;
	
	/** The ecommerce shop. */
	@SerializedName("ecommerce_shop")
	public EcommerceShop ecommerceShop;
}
