/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.emails;

import java.io.IOException;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.ToNumberPolicy;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * Reads and writes the {@code editor_content} document of a block or a footer text.
 * <p>
 * The adapter does two things that the Gson default does not do:
 * <ol>
 * <li>It reads a whole number as a {@code Long} and other numbers as a {@code Double}. So {@code "linkId": 7}
 * is sent back as {@code 7}, not as {@code 7.0}.</li>
 * <li>It writes the null values in the map, so the SDK sends the document exactly as you give it.</li>
 * </ol>
 */
public class EditorContentAdapter extends TypeAdapter<Map<String, Object>> {

	/** The Gson object that reads and writes the map. */
	private static final Gson GSON = new GsonBuilder()
			.setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
			.serializeNulls()
			.create();

	/** The adapter for the map. */
	private static final TypeAdapter<Map<String, Object>> DELEGATE =
			GSON.getAdapter(new TypeToken<Map<String, Object>>() { });

	/**
	 * Writes the map, with its null values.
	 *
	 * @param out the writer
	 * @param value the map
	 * @throws IOException if the writer fails
	 */
	@Override
	public void write(JsonWriter out, Map<String, Object> value) throws IOException
	{
		if (value == null) {
			out.nullValue();
			return;
		}

		boolean serializeNulls = out.getSerializeNulls();
		out.setSerializeNulls(true);

		try {
			DELEGATE.write(out, value);
		} finally {
			out.setSerializeNulls(serializeNulls);
		}
	}

	/**
	 * Reads the map. Whole numbers become Long values, and other numbers become Double values.
	 *
	 * @param in the reader
	 * @return the map, or null
	 * @throws IOException if the reader fails
	 */
	@Override
	public Map<String, Object> read(JsonReader in) throws IOException
	{
		if (in.peek() == JsonToken.NULL) {
			in.nextNull();
			return null;
		}

		return DELEGATE.read(in);
	}
}
