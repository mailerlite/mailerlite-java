/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.fields;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.ApiResource;

/**
 * The Class Field.
 */
public class Field extends ApiResource {

	/** The id. */
	@SerializedName("id")
	public String id;
	
	/** The name. */
	@SerializedName("name")
	public String name;
	
	/** The key. */
	@SerializedName("key")
	public String key;
	
	/** The type. */
	@SerializedName("type")
	public String type;
}
