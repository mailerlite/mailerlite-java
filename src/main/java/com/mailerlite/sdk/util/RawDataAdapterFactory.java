/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.util;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * Deserializes a {@link RawDataHolder} as usual and also hands it the raw JSON object.
 */
public class RawDataAdapterFactory implements TypeAdapterFactory {

	/**
	 * Creates the type adapter.
	 *
	 * @param <T> the generic type
	 * @param gson the gson instance
	 * @param type the type token
	 * @return the type adapter, or null for types that do not hold raw data
	 */
	@Override
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

		if (!RawDataHolder.class.isAssignableFrom(type.getRawType())) {
			return null;
		}

		TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
		TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);

		return new TypeAdapter<T>() {

			@Override
			public void write(JsonWriter out, T value) throws IOException {
				delegate.write(out, value);
			}

			@Override
			public T read(JsonReader in) throws IOException {
				JsonElement tree = elementAdapter.read(in);
				T value = delegate.fromJsonTree(tree);

				if (value != null && tree.isJsonObject()) {
					((RawDataHolder) value).setData(tree.getAsJsonObject());
				}

				return value;
			}
		};
	}
}
