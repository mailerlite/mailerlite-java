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

/**
 * The Class TriggerPurchasedSpecificProduct.
 */
public class TriggerPurchasedSpecificProduct extends AutomationTrigger {

	/** The ecommerce shop id. */
	@SerializedName("ecommerce_shop_id")
	public String ecommerceShopId;
	
	/** The ecommerce shop. */
	@SerializedName("ecommerce_shop")
	public EcommerceShop ecommerceShop;
	
	/** The ecommerce product id. */
	@SerializedName("ecommerce_product_id")
	public String ecommerceProductId;
	
	/** The ecommerce product. */
	@SerializedName("ecommerce_product")
	public EcommerceProduct ecommerceProduct;
}
