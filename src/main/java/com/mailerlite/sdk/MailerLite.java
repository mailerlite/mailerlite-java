/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk;

import com.mailerlite.sdk.automations.Automations;
import com.mailerlite.sdk.brandstyles.BrandStyles;
import com.mailerlite.sdk.campaigns.Campaigns;
import com.mailerlite.sdk.emails.Emails;
import com.mailerlite.sdk.fields.Fields;
import com.mailerlite.sdk.files.MediaFiles;
import com.mailerlite.sdk.forms.Forms;
import com.mailerlite.sdk.groups.Groups;
import com.mailerlite.sdk.segments.Segments;
import com.mailerlite.sdk.susbcribers.Subscribers;
import com.mailerlite.sdk.webhooks.Webhooks;

/**
 * The Class MailerLite.
 */
public class MailerLite 
{
	
	/** The token. */
	protected String token;
	
	/** The campaigns. */
	protected Campaigns campaigns;
	
	/** The subscribers. */
	protected Subscribers subscribers;
	
	/** The groups. */
	protected Groups groups;
	
	/** The segments. */
	protected Segments segments;
	
	/** The fields. */
	protected Fields fields;
	
	/** The automations. */
	protected Automations automations;
	
	/** The webhooks. */
	protected Webhooks webhooks;
	
	/** The forms. */
	protected Forms forms;
	
	/** The emails. */
	protected Emails emails;
	
	/** The brand styles. */
	protected BrandStyles brandStyles;
	
	/** The files of the media library. */
	protected MediaFiles files;
	
	/**
	 * Campaigns.
	 *
	 * @return the campaigns
	 */
	public Campaigns campaigns()
	{
		
		if (campaigns == null) {
			
			campaigns = new Campaigns(this);
		}
		
		return campaigns;
	}
	
	
	/**
	 * Subscribers.
	 *
	 * @return the subscribers
	 */
	public Subscribers subscribers()
	{
		if (subscribers == null) {
			
			subscribers = new Subscribers(this);
		}
		
		return subscribers;
	}
	
	
	/**
	 * Groups.
	 *
	 * @return the groups
	 */
	public Groups groups()
	{
		if (groups == null) {
			groups = new Groups(this);
		}
		
		return groups;
	}
	
	
	/**
	 * Segments.
	 *
	 * @return the segments
	 */
	public Segments segments()
	{
		if (segments == null) {
			
			segments = new Segments(this);
		}
		
		return segments;
	}
	
	
	/**
	 * Fields.
	 *
	 * @return the fields
	 */
	public Fields fields()
	{
		if (fields == null) {
			fields = new Fields(this);
		}
		
		return fields;
	}
	
	
	/**
	 * Automations.
	 *
	 * @return the automations
	 */
	public Automations automations()
	{
		if (automations == null) {
			automations = new Automations(this);
		}
		
		return automations;
	}
	
	
	/**
	 * Webhooks.
	 *
	 * @return the webhooks
	 */
	public Webhooks webhooks()
	{
		if (webhooks == null) {
			webhooks = new Webhooks(this);
		}
		
		return webhooks;
	}
	
	
	/**
	 * Forms.
	 *
	 * @return the forms
	 */
	public Forms forms()
	{
		if (forms == null) {
			forms = new Forms(this);
		}
		
		return forms;
	}
	
	
	/**
	 * Emails built with the simple editor.
	 *
	 * @return the emails
	 */
	public Emails emails()
	{
		if (emails == null) {
			emails = new Emails(this);
		}
		
		return emails;
	}
	
	
	/**
	 * Brand styles.
	 *
	 * @return the brand styles
	 */
	public BrandStyles brandStyles()
	{
		if (brandStyles == null) {
			brandStyles = new BrandStyles(this);
		}
		
		return brandStyles;
	}
	
	
	/**
	 * Files of the media library.
	 *
	 * @return the files
	 */
	public MediaFiles files()
	{
		if (files == null) {
			files = new MediaFiles(this);
		}
		
		return files;
	}
	
	
	/**
	 * Sets the MailerLite API token.
	 *
	 * @param token the new token
	 */
	public void setToken(String token)
	{
		this.token = token;
	}
	
	
    /**
     * Returns the MailerLite token.
     *
     * @return the MailerLite token
     */
    public String getToken() {
        
        return this.token;
    }
}
