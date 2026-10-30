/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations.templates;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

/**
 * The Class AutomationTemplate.
 */
public class AutomationTemplate {

	/** The id. */
	@SerializedName("id")
	public String id;
	
	/** The name. */
	@SerializedName("name")
	public String name;
	
	/** The type. */
	@SerializedName("type")
	public String type;
	
	/** The workflow. */
	@SerializedName("workflow")
	public JsonElement workflow;
	
	/** The description. */
	@SerializedName("description")
	public String description;
	
	/** The excerpt. */
	@SerializedName("excerpt")
	public String excerpt;
	
	/** The workflow steps description. */
	@SerializedName("workflow_steps_description")
	public String[] workflowStepsDescription;
	
	/** The url. */
	@SerializedName("url")
	public String url;
}
