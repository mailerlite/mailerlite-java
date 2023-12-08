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

public class TriggerAbandonedCart extends AutomationTrigger {

	@SerializedName("ecommerce_shop_id")
	public String ecommerceShopId;
	
	@SerializedName("ecommerce_shop")
	public EcommerceShop ecommerceShop;
	
	@SerializedName("unit")
	public String unit;
	
	@SerializedName("value")
	public String value;
}
