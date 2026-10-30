/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.mailerlite.sdk.emails.EmailErrorCodes;
import com.mailerlite.sdk.exceptions.MailerLiteException;

public class MailerLiteExceptionTest {

	/** The emails package uses the exceptions package, so the exceptions package must not use the emails package. */
	@Test
	public void exceptionsPackageDoesNotImportEmailsPackage() throws IOException
	{
		List<Path> files;

		try (Stream<Path> paths = Files.list(Paths.get("src/main/java/com/mailerlite/sdk/exceptions"))) {
			files = paths.filter(p -> p.toString().endsWith(".java")).collect(Collectors.toList());
		}

		assertFalse(files.isEmpty());

		for (Path file : files) {

			String source = new String(Files.readAllBytes(file), StandardCharsets.UTF_8);
			assertFalse(source.contains("com.mailerlite.sdk.emails"), file.getFileName().toString());
		}
	}

	@Test
	public void versionConflictCodeIsTheSameConstant()
	{
		assertEquals("version_conflict", MailerLiteException.VERSION_CONFLICT);
		assertSame(MailerLiteException.VERSION_CONFLICT, EmailErrorCodes.VERSION_CONFLICT);
	}

	@Test
	public void isVersionConflictComparesTheErrorCode()
	{
		MailerLiteException e = new MailerLiteException("conflict");
		assertFalse(e.isVersionConflict());

		e.errorCode = "other";
		assertFalse(e.isVersionConflict());

		e.errorCode = new String("version_conflict");
		assertTrue(e.isVersionConflict());
	}
}
