package com.mailerlite.sdk;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.NoSuchElementException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.util.MailerLiteHttpClientFactory;
import com.mailerlite.sdk.exceptions.JsonResponseError;
import com.mailerlite.sdk.util.JsonSerializationDeserializationStrategy;

public class MailerLiteApi {
	
    private final String endpointBase = "https://connect.mailerlite.com/api";
    
    private String apiToken = "";
    
    private HttpClient client;
    
    
    public MailerLiteApi() {
    	
    	this.client = MailerLiteHttpClientFactory.getInstance().createClient();
    }
    
    
    public void setToken(String token) {
        
        this.apiToken = token;
    }
    
    
    /**
     * Does a GET request to the given endpoint of the MailerLite API
     *
     * @param endpoint The MailerLite API endpoint
     * @param responseClass The class of the response object
     * @return T
     * @throws MailerLiteException if an error is returned from the API this exception will contain the details
     */
    @SuppressWarnings("unchecked")
    public <T extends MailerLiteResponse> T getRequest(String endpoint, Class<T> responseClass) throws MailerLiteException {
        
        HttpRequest request = HttpRequest.newBuilder(URI.create(this.endpointBase.concat(endpoint)))
                .header("Content-type", "applicateion/json")
                .header("Authorization", "Bearer ".concat(this.apiToken))
                .GET()
                .build();
        
        HttpResponse<String> responseObject = null;
        
        try {
            
        	responseObject = this.client.send(request, BodyHandlers.ofString());
                        
        } catch (IOException | InterruptedException e) {

        	MailerLiteException ex = (MailerLiteException) e;
            
            throw ex;
        }
        
        if (responseClass == MailerLiteStringResponse.class) {
            
        	MailerLiteStringResponse response = new MailerLiteStringResponse();
            response.responseString = responseObject.body().toString();
            
            return (T) response;
        }
        
        
        return this.handleApiResponse(responseObject, responseClass);
    }
    
    
    @SuppressWarnings("unchecked")
	public <T extends MailerLiteResponse> T postRequest(String endpoint, String requestBody, Class<T> responseClass) throws MailerLiteException {
    	
        HttpRequest request = HttpRequest.newBuilder(URI.create(this.endpointBase.concat(endpoint)))
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer ".concat(this.apiToken))
                .POST(BodyPublishers.ofString(requestBody))
                .build();
        
        HttpResponse<String> responseObject = null;
        
        try {
            
            responseObject = this.client.send(request, BodyHandlers.ofString());
                        
        } catch (IOException | InterruptedException e) {

        	MailerLiteException ex = (MailerLiteException) e;
            
            throw ex;
        }
        
        if (responseClass == MailerLiteStringResponse.class) {
            
        	MailerLiteStringResponse response = new MailerLiteStringResponse();
            response.responseString = responseObject.body().toString();
            
            return (T) response;
        }
        
        return this.handleApiResponse(responseObject, responseClass);
    }
    
    
    /**
     * Does a PUT request to the given endpoint of the MailerSend API
     *
     * @param endpoint The MailerLite API endpoint
     * @param requestBody The body of the PUT request
     * @param responseClass The class of the response object
     * @return T
     * @throws com.mailerlite.sdk.exceptions.MailerLiteException if an error is returned from the API this exception will contain the details
     */
    @SuppressWarnings("unchecked")
	public <T extends MailerLiteResponse> T putRequest(String endpoint, String requestBody, Class<T> responseClass) throws MailerLiteException {
       
    	if (requestBody == null) {
    		requestBody = "";
    	}
    	
        HttpRequest request = HttpRequest.newBuilder(URI.create(this.endpointBase.concat(endpoint)))
                .header("Content-type", "applicateion/json")
                .header("Authorization", "Bearer ".concat(this.apiToken))
                .PUT(BodyPublishers.ofString(requestBody))
                .build();
        
        HttpResponse<String> responseObject = null;
        
        try {
            
            responseObject = this.client.send(request, BodyHandlers.ofString());
                        
        } catch (IOException | InterruptedException e) {

        	MailerLiteException ex = (MailerLiteException) e;
            
            throw ex;
        }
        
        if (responseClass == MailerLiteStringResponse.class) {
            
        	MailerLiteStringResponse response = new MailerLiteStringResponse();
            response.responseString = responseObject.body().toString();
            
            return (T) response;
        }
        
        return this.handleApiResponse(responseObject, responseClass);
    }
    
    
    /**
     * Handles the response from the MailerLite API. It deserializes the JSON response into an object with the given type
     * @param <T> The type of what the response will be deserialized to
     * @param responseObject The HttpResponse object of the request
     * @param responseClass The class of the response object
     * @return T
     * @throws MailerLiteException
     */
    private <T extends MailerLiteResponse> T handleApiResponse(HttpResponse<String> responseObject, Class<T> responseClass) throws MailerLiteException {
        
        String stringResponse = "";
        
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();
        
        int[] successCodes = {200, 201, 202, 204};
        
        boolean isSuccess = false;
        
        for (int code : successCodes) {
            
            if (code == responseObject.statusCode()) {
                
                isSuccess = true;
                break;
            }
        }
        
        if (responseObject != null && !isSuccess) {
            
            stringResponse = responseObject.body().toString();
            
            MailerLiteException responseError;
            
            try {
            	
            	JsonResponseError error = gson.fromJson(stringResponse, JsonResponseError.class);
	            
	            responseError = new MailerLiteException(error.message);
	         
	            responseError.errors = error.errors;
            } catch (Exception ex) {
            	
            	responseError = new MailerLiteException("Error parsing API response");
            }
            
            responseError.responseBody = stringResponse;
            responseError.code = responseObject.statusCode();
            
            throw responseError;
        }
        
        stringResponse = responseObject.body().toString();
        
        T response = null;
        
        if (!stringResponse.equals("") && !stringResponse.equals("[]")) {
            
            response = gson.fromJson(stringResponse, responseClass);
        } else {
            
            try {
                
                response = responseClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                
                MailerLiteException ex = (MailerLiteException) e;
                
                throw ex;
            }
        }
        
        // get the response headers
        
        try {
            
            response.rateLimit = Integer.parseInt(responseObject.headers().firstValue("x-ratelimit-limit").get());
        } catch (NumberFormatException | NoSuchElementException e) {
            
            // left empty on purpose
        }
        
        try {
            
            response.rateLimitRemaining = Integer.parseInt(responseObject.headers().firstValue("x-ratelimit-remaining").get());
        } catch (NumberFormatException | NoSuchElementException e) {
            
            // left empty on purpose
        }
        
        response.headers = responseObject.headers().map();
        
        response.responseStatusCode = responseObject.statusCode();
        
        return response;
    }
}
