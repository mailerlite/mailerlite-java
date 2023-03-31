/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk;

import com.mailerlite.sdk.campaigns.Campaigns;
import com.mailerlite.sdk.susbcribers.Subscribers;

public class MailerLite 
{
	protected String token;
	
	protected Campaigns campaigns;
	
	protected Subscribers subscribers;
	
	public Campaigns campaigns()
	{
		
		if (campaigns == null) {
			
			campaigns = new Campaigns(this);
		}
		
		return campaigns;
	}
	
	public Subscribers subscribers()
	{
		if (subscribers == null) {
			
			subscribers = new Subscribers(this);
		}
		
		return subscribers;
	}
	
	/**
	 * Sets the MailerLite API token
	 * @param token
	 */
	public void setToken(String token)
	{
		this.token = token;
	}
	
	
    /**
     * Returns the MailerLite token
     *
     * @return the MailerLite token
     */
    public String getToken() {
        
        return this.token;
    }
}
