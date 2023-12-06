package com.mailerlite.sdk.automations;

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
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.util.JsonSerializationDeserializationStrategy;
import com.mailerlite.sdk.util.PaginatedRequest;

public class AutomationRetriever extends PaginatedRequest<AutomationRetriever> {
	private MailerLite apiObjectReference;
	
	public AutomationRetriever(MailerLite apiReference)
	{
		apiObjectReference = apiReference;
	}
	
	public AutomationRetriever filter(String name, String value)
	{
		this.addQueryParameter("filter[".concat(name).concat("]"), value);
		
		return this;
	}
	
	public void get() throws MailerLiteException
	{
		String endpoint = "/automations/".concat(this.getQueryParameters());
	
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		MailerLiteStringResponse responseObj = api.getRequest(endpoint, MailerLiteStringResponse.class);
		
		String response = responseObj.responseString;
		
		JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
        
		JsonArray data = jsonObject.getAsJsonArray("data");
		
		ArrayList<Automation> automations = new ArrayList<Automation>();
		
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .registerTypeAdapter(Automation.class, new AutomationDeserializer())
                .create();
        
		
		for(JsonElement obj : data) {
			
			JsonObject ob = obj.getAsJsonObject();
			
			Automation auto = gson.fromJson(ob, Automation.class);
		}
		
		int a =5 ;
	}

	@Override
	protected AutomationRetriever getInstance() {
		
		return this;
	}
	
	
}
