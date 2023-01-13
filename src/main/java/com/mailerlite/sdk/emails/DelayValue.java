package com.mailerlite.sdk.emails;

import java.time.LocalDateTime;

import com.google.gson.annotations.SerializedName;
import com.mailerlite.sdk.util.ApiResource;
import com.mailerlite.sdk.util.ParseDate;

public class DelayValue extends ApiResource {

	@SerializedName("unit")
	public String unit;
	
	@SerializedName("value")
	public String value;
	
	@SerializedName("date")
	public String dateStr;
	
	@ParseDate("dateStr")
	public LocalDateTime delayDate;
}
