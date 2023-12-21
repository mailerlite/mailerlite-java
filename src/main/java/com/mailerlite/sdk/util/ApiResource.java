/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ApiResource {

	public void parseDates() {
		
		for (Field field : this.getClass().getDeclaredFields()) {
			
			if (field.isAnnotationPresent(ParseDate.class)) {
				
				ParseDate fieldToParse = field.getAnnotation(ParseDate.class);
				
				try {
					
					Field dateStrField = this.getClass().getDeclaredField(fieldToParse.value());
					
					if (dateStrField.get(this) != null) {
											
						String dateStr = dateStrField.get(this).toString();
						
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date date = formatter.parse(dateStr);
						
			            field.setAccessible(true);
			            
			            field.set(this, date);
					}
				} catch (NoSuchFieldException e) {

					e.printStackTrace();
				} catch (SecurityException e) {

					e.printStackTrace();
				} catch (IllegalArgumentException e) {

					e.printStackTrace();
				} catch (IllegalAccessException e) {

					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}

			}
			
			if (field.getClass().isAssignableFrom(ApiResource.class)) {
				
				try {

					Object fieldValue = field.get(this);
					
					Method parseDatesMethod = fieldValue.getClass().getDeclaredMethod("parseDates", new Class[]{});
					
					parseDatesMethod.invoke(fieldValue);
					
				} catch (NoSuchMethodException | SecurityException | IllegalArgumentException
						| IllegalAccessException e) {

					e.printStackTrace();
				} catch (InvocationTargetException e) {

					e.printStackTrace();
				}
			}
		}
	}
}
