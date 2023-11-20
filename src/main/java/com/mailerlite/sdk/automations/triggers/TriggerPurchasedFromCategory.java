package com.mailerlite.sdk.automations.triggers;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.ecommerce.EcommerceCategory;
import com.mailerlite.sdk.ecommerce.EcommerceShop;

public class TriggerPurchasedFromCategory extends AutomationTrigger {

	@SerializedName("ecommerce_shop_id")
	public String ecommerceShopId;
	
	@SerializedName("ecommerce_shop")
	public EcommerceShop ecommerceShop;
	
	@SerializedName("ecommerce_category_id")
	public String ecommerceCategoryId;
	
	@SerializedName("ecommerce_category")
	public EcommerceCategory ecommerceCategory;
}
