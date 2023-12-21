/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.ecommerce;

import com.google.gson.annotations.SerializedName;

/**
 * The Class EcommerceProduct.
 */
public class EcommerceProduct {

	/** The id. */
	@SerializedName("id")
	public String id;
	
	/** The name. */
	@SerializedName("name")
	public String name;
}
