/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.triggers;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.ecommerce.EcommerceProduct;
import com.mailerlite.sdk.ecommerce.EcommerceShop;

public class TriggerPurchasedSpecificProduct extends AutomationTrigger {

	@SerializedName("ecommerce_shop_id")
	public String ecommerceShopId;
	
	@SerializedName("ecommerce_shop")
	public EcommerceShop ecommerceShop;
	
	@SerializedName("ecommerce_product_id")
	public String ecommerceProductId;
	
	@SerializedName("ecommerce_product")
	public EcommerceProduct ecommerceProduct;
}
