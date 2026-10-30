/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.groups;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.susbcribers.SingleSubscriberImport;

/**
 * The Class GroupSubscriberImport.
 */
public class GroupSubscriberImport extends SingleSubscriberImport {

	/** The import progress url. */
	@SerializedName("import_progress_url")
	public String importProgressUrl;
}
