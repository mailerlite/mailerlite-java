/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.support;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

public final class Fixtures {

	private Fixtures()
	{
	}

	public static String read(String name)
	{
		try (InputStream stream = Fixtures.class.getResourceAsStream("/responses/" + name)) {

			if (stream == null) {
				throw new IllegalArgumentException("No fixture with the name " + name);
			}

			return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
		} catch (IOException e) {

			throw new UncheckedIOException(e);
		}
	}
}
