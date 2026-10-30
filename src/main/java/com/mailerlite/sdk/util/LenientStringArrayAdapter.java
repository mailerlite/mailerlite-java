/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * Reads a JSON array into a String array. Strings and numbers are kept as they are,
 * and objects or arrays are kept as their JSON text.
 */
public class LenientStringArrayAdapter extends TypeAdapter<String[]> {

	/**
	 * Writes the array.
	 *
	 * @param out the writer
	 * @param value the value
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	public void write(JsonWriter out, String[] value) throws IOException {
		if (value == null) {
			out.nullValue();
			return;
		}
		
		out.beginArray();
		for (String item : value) {
			out.value(item);
		}
		out.endArray();
	}

	/**
	 * Reads the array.
	 *
	 * @param in the reader
	 * @return the string array
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	public String[] read(JsonReader in) throws IOException {
		if (in.peek() == JsonToken.NULL) {
			in.nextNull();
			return null;
		}
		
		JsonElement element = JsonParser.parseReader(in);
		
		if (!element.isJsonArray()) {
			return new String[] { asString(element) };
		}
		
		List<String> items = new ArrayList<String>();
		for (JsonElement item : element.getAsJsonArray()) {
			items.add(asString(item));
		}
		
		return items.toArray(new String[0]);
	}
	
	private String asString(JsonElement element)
	{
		return element.isJsonPrimitive() ? element.getAsString() : element.toString();
	}
}
