package com.mailerlite.sdk.automations.triggers;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.ecommerce.EcommerceShop;

public class TriggerPurchasedAnyProduct extends AutomationTrigger {

	@SerializedName("ecommerce_shop_id")
	public String ecommerceShopId;
	
	@SerializedName("ecommerce_shop")
	public EcommerceShop ecommerceShop;
}
