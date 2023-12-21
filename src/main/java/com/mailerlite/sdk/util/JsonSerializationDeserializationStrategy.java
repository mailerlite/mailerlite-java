/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.annotations.Expose;

/**
 * Handles the serialization and deserialization strategy for all API objects (Email, Campaign, etc.).
 * If a field does not have the @Expose annotiation, it gets de/serialized normally.
 * If it has the @Expose annotation, the annotation's parameters determine the behaviour
 *
 * @version $Id: $Id
 */
public class JsonSerializationDeserializationStrategy implements ExclusionStrategy {

    /** The deserialize. */
    private boolean deserialize = false;

    /**
     * <p>Constructor for JsonSerializationDeserializationStrategy.</p>
     *
     * @param isDeserialize a boolean.
     */
    public JsonSerializationDeserializationStrategy(boolean isDeserialize) {
        
        this.deserialize = isDeserialize;
    }
    
    
    /**
     * {@inheritDoc}
     *
     * Just keeping it here because it is required by the ExclusionStrategy interface
     * Always returns false
     */
    @Override
    public boolean shouldSkipClass(Class<?> sClass) {
        return false;
    }

    
    /**
     * {@inheritDoc}
     *
     * Implements the field exclusion strategy
     * If a field does not have the @Expose annotiation, it gets de/serialized normally.
     * If it has the @Expose annotation, the annotation's parameters determine the behaviour
     */
    @Override
    public boolean shouldSkipField(FieldAttributes field) {
        
        if (field.getAnnotation(Expose.class) == null) {
            
            return false;
        }
       
        if (this.deserialize) {
            
            return !field.getAnnotation(Expose.class).deserialize();
        } else {
            
            return !field.getAnnotation(Expose.class).serialize();
        }
    }
}
