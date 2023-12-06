package com.mailerlite.sdk.automations.activity;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.MailerLiteApi;
import com.mailerlite.sdk.MailerLiteStringResponse;
import com.mailerlite.sdk.automations.Automation;
import com.mailerlite.sdk.automations.AutomationDeserializer;
import com.mailerlite.sdk.automations.AutomationRetriever;
import com.mailerlite.sdk.automations.AutomationStepDeserializer;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.util.JsonSerializationDeserializationStrategy;
import com.mailerlite.sdk.util.PaginatedRequest;

public class AutomationSubscriberActivityRetriever extends PaginatedRequest<AutomationSubscriberActivityRetriever>  {

	
	private MailerLite apiObjectReference;
	
	public AutomationSubscriberActivityRetriever(MailerLite apiReference)
	{
		apiObjectReference = apiReference;
	}
	
	
	public AutomationSubscriberActivityRetriever filter(String name, String value)
	{
		this.addQueryParameter("filter[".concat(name).concat("]"), value);
		
		return this;
	}
	
	
	public List<SubscriberActivity> get(String automationId) throws MailerLiteException
	{
		String endpoint = "/automations/".concat(automationId).concat("/activity").concat(this.getQueryParameters());
	
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		MailerLiteStringResponse responseObj = api.getRequest(endpoint, MailerLiteStringResponse.class);
		
		String response = responseObj.responseString;
		
		JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
        
		JsonArray data = jsonObject.getAsJsonArray("data");
		
		ArrayList<SubscriberActivity> activity = new ArrayList<SubscriberActivity>();
		
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();
        
		AutomationStepDeserializer automationStepDeserializer = new AutomationStepDeserializer();
        
        
		for(JsonElement obj : data) {
			
			JsonObject ob = obj.getAsJsonObject();
			
			SubscriberActivity subscriberActivity = gson.fromJson(obj, SubscriberActivity.class);
			
			JsonObject nextStep = ob.get("nextStep").getAsJsonObject();
			JsonObject currentStep = ob.get("currentStep").getAsJsonObject();
			
			subscriberActivity.currentStep = automationStepDeserializer.deserializeAutomationStep(currentStep, gson);
			subscriberActivity.nextStep = automationStepDeserializer.deserializeAutomationStep(nextStep, gson);	
			
			activity.add(subscriberActivity);
		}
		
		return activity;
	}
	
	
	@Override
	protected AutomationSubscriberActivityRetriever getInstance() {
		return this;
	}

	
}
