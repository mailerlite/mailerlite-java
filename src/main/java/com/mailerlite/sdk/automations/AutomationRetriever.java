/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
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

/**
 * The Class AutomationRetriever.
 */
public class AutomationRetriever extends PaginatedRequest<AutomationRetriever> {
	
	/** The api object reference. */
	private MailerLite apiObjectReference;
	
	/**
	 * Instantiates a new automation retriever.
	 *
	 * @param apiReference the api reference
	 */
	public AutomationRetriever(MailerLite apiReference)
	{
		apiObjectReference = apiReference;
	}
	
	
	/**
	 * Filter.
	 *
	 * @param name the name
	 * @param value the value
	 * @return the automation retriever
	 */
	public AutomationRetriever filter(String name, String value)
	{
		this.addQueryParameter("filter[".concat(name).concat("]"), value);
		
		return this;
	}
	
	
	/**
	 * Gets the.
	 *
	 * @return the list
	 * @throws MailerLiteException the mailer lite exception
	 */
	public List<Automation> get() throws MailerLiteException
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
			
			automations.add(gson.fromJson(ob, Automation.class));
		}
		
		return automations;
	}
	
	
	/**
	 * Gets the single.
	 *
	 * @param id the id
	 * @return the single
	 * @throws MailerLiteException the mailer lite exception
	 */
	public Automation getSingle(String id) throws MailerLiteException
	{
		String endpoint = "/automations/".concat(id);
		
		MailerLiteApi api = new MailerLiteApi();
		api.setToken(apiObjectReference.getToken());
		
		MailerLiteStringResponse responseObj = api.getRequest(endpoint, MailerLiteStringResponse.class);
		
		String response = responseObj.responseString;
		
		JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
        
		JsonObject data = jsonObject.get("data").getAsJsonObject();
		
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .registerTypeAdapter(Automation.class, new AutomationDeserializer())
                .create();
        
        return gson.fromJson(data, Automation.class);
	}

	
	/**
	 * Gets the single instance of AutomationRetriever.
	 *
	 * @return single instance of AutomationRetriever
	 */
	@Override
	protected AutomationRetriever getInstance() {
		
		return this;
	}
	
	
}
