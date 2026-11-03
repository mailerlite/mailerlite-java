/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.automations;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/**
 * Selects the automation activity that a bulk activity action applies to.
 */
public class AutomationActivityFilter {

	/** The status. */
	@SerializedName("status")
	public String status;
	
	/** The run ids. */
	@SerializedName("id")
	public ArrayList<String> ids;
	
	/** The search. */
	@SerializedName("search")
	public String search;
	
	/** The reason. */
	@SerializedName("reason")
	public String reason;
	
	/** The date from. */
	@SerializedName("date_from")
	public String dateFrom;
	
	/** The date to. */
	@SerializedName("date_to")
	public String dateTo;
	
	/** The scheduled from. */
	@SerializedName("scheduled_from")
	public String scheduledFrom;
	
	/** The scheduled to. */
	@SerializedName("scheduled_to")
	public String scheduledTo;
	
	/** The next step id. */
	@SerializedName("next_step")
	public String nextStep;
	
	/** The current step id. */
	@SerializedName("current_step")
	public String currentStep;
	
	/** The step id. */
	@SerializedName("step")
	public String step;
	
	/**
	 * Status.
	 *
	 * @param status the status, for example "active" or "completed"
	 * @return the automation activity filter
	 */
	public AutomationActivityFilter status(String status)
	{
		this.status = status;
		return this;
	}
	
	/**
	 * Adds a run id.
	 *
	 * @param id the run id
	 * @return the automation activity filter
	 */
	public AutomationActivityFilter addId(String id)
	{
		if (ids == null) {
			ids = new ArrayList<String>();
		}
		
		ids.add(id);
		return this;
	}
	
	/**
	 * Search.
	 *
	 * @param search the search
	 * @return the automation activity filter
	 */
	public AutomationActivityFilter search(String search)
	{
		this.search = search;
		return this;
	}
	
	/**
	 * Reason.
	 *
	 * @param reason the reason
	 * @return the automation activity filter
	 */
	public AutomationActivityFilter reason(String reason)
	{
		this.reason = reason;
		return this;
	}
	
	/**
	 * Date from.
	 *
	 * @param dateFrom the date from, as YYYY-MM-DD
	 * @return the automation activity filter
	 */
	public AutomationActivityFilter dateFrom(String dateFrom)
	{
		this.dateFrom = dateFrom;
		return this;
	}
	
	/**
	 * Date to.
	 *
	 * @param dateTo the date to, as YYYY-MM-DD
	 * @return the automation activity filter
	 */
	public AutomationActivityFilter dateTo(String dateTo)
	{
		this.dateTo = dateTo;
		return this;
	}
	
	/**
	 * Scheduled from.
	 *
	 * @param scheduledFrom the scheduled from, as YYYY-MM-DD
	 * @return the automation activity filter
	 */
	public AutomationActivityFilter scheduledFrom(String scheduledFrom)
	{
		this.scheduledFrom = scheduledFrom;
		return this;
	}
	
	/**
	 * Scheduled to.
	 *
	 * @param scheduledTo the scheduled to, as YYYY-MM-DD
	 * @return the automation activity filter
	 */
	public AutomationActivityFilter scheduledTo(String scheduledTo)
	{
		this.scheduledTo = scheduledTo;
		return this;
	}
	
	/**
	 * Next step.
	 *
	 * @param nextStep the next step id
	 * @return the automation activity filter
	 */
	public AutomationActivityFilter nextStep(String nextStep)
	{
		this.nextStep = nextStep;
		return this;
	}
	
	/**
	 * Current step.
	 *
	 * @param currentStep the current step id
	 * @return the automation activity filter
	 */
	public AutomationActivityFilter currentStep(String currentStep)
	{
		this.currentStep = currentStep;
		return this;
	}
	
	/**
	 * Step.
	 *
	 * @param step the step id
	 * @return the automation activity filter
	 */
	public AutomationActivityFilter step(String step)
	{
		this.step = step;
		return this;
	}
}
