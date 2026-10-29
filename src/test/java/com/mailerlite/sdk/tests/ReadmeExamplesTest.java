/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 *
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import com.google.gson.Gson;
import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.support.Fixtures;
import com.mailerlite.sdk.support.ReadmeExampleBase;
import com.mailerlite.sdk.support.RecordingHttpClient;
import com.mailerlite.sdk.support.RecordingHttpClient.RecordedRequest;

/**
 * Compiles the examples of the new README sections and runs each one against the recording HTTP client.
 * <p>
 * Each example must send the expected requests and use all the queued responses. A MailerLiteException
 * in an example fails the test, because the test changes "e.printStackTrace();" to a throw.
 */
public class ReadmeExamplesTest {

	/** The base URL of the API. */
	private static final String BASE = "https://connect.mailerlite.com/api";

	/** The imports that the README examples get. The README shows them as "// import ....". */
	private static final List<String> IMPORTS = Arrays.asList(
			"com.mailerlite.sdk.*",
			"com.mailerlite.sdk.campaigns.*",
			"com.mailerlite.sdk.emails.*",
			"com.mailerlite.sdk.exceptions.*",
			"java.util.*");

	/**
	 * A README example: the heading of its section, the responses to queue and the expected requests.
	 */
	private static class Example {

		/** The heading of the README section, without "### ". */
		final String heading;

		/** The responses to queue. */
		final List<Object[]> responses = new ArrayList<Object[]>();

		/** The expected requests, as "METHOD /path?query". */
		final List<String> requests = new ArrayList<String>();

		/**
		 * Instantiates a new example.
		 *
		 * @param heading the heading
		 */
		Example(String heading)
		{
			this.heading = heading;
		}

		/**
		 * Queues a response.
		 *
		 * @param status the status
		 * @param body the body
		 * @return this example
		 */
		Example respond(int status, String body)
		{
			return respond(status, body, Collections.<String, String>emptyMap());
		}

		/**
		 * Queues a response with headers.
		 *
		 * @param status the status
		 * @param body the body
		 * @param headers the headers
		 * @return this example
		 */
		Example respond(int status, String body, Map<String, String> headers)
		{
			responses.add(new Object[] {status, body, headers});
			return this;
		}

		/**
		 * Queues a network error.
		 *
		 * @param error the error
		 * @return this example
		 */
		Example fail(Exception error)
		{
			responses.add(new Object[] {error});
			return this;
		}

		/**
		 * Adds an expected request.
		 *
		 * @param request the request, as "METHOD /path?query"
		 * @return this example
		 */
		Example expect(String request)
		{
			requests.add(request);
			return this;
		}
	}

	/**
	 * The examples to run, one for each new README section.
	 *
	 * @return the examples
	 */
	private static List<Example> examples()
	{
		List<Example> list = new ArrayList<Example>();

		list.add(new Example("Get an email")
				.respond(200, Fixtures.read("email.json"))
				.expect("GET /emails/123456"));

		list.add(new Example("Get the HTML of an email")
				.respond(200, Fixtures.read("email_html.json"))
				.expect("GET /emails/123456/html"));

		list.add(new Example("Get a block")
				.respond(200, Fixtures.read("email_block.json"))
				.expect("GET /emails/123456/blocks/57"));

		list.add(new Example("Insert a block")
				.respond(200, Fixtures.read("email.json"))
				.respond(201, Fixtures.read("email_block_created.json"))
				.expect("GET /emails/123456")
				.expect("POST /emails/123456/blocks"));

		list.add(new Example("Update a block")
				.respond(200, Fixtures.read("email_block.json"))
				.respond(200, Fixtures.read("email_block_updated.json"))
				.expect("GET /emails/123456/blocks/57")
				.expect("PATCH /emails/123456/blocks/57"));

		list.add(new Example("Delete a block")
				.respond(200, Fixtures.read("email_etag.json"))
				.expect("DELETE /emails/123456/blocks/57"));

		list.add(new Example("Move a block")
				.respond(200, Fixtures.read("email_block_moved.json"))
				.expect("POST /emails/123456/blocks/57/move"));

		list.add(new Example("Validate a block")
				.respond(200, Fixtures.read("email_block_validation_invalid.json"))
				.expect("POST /emails/blocks/validate"));

		list.add(new Example("Update the design settings")
				.respond(200, Fixtures.read("email_settings.json"))
				.expect("PATCH /emails/123456/settings"));

		list.add(new Example("Validate the design settings")
				.respond(200, Fixtures.read("email_settings_validation.json"))
				.expect("POST /emails/123456/settings/validate"));

		list.add(new Example("Save an email as a template")
				.respond(201, Fixtures.read("email_template.json"))
				.expect("POST /emails/123456/templates"));

		return list;
	}

	/**
	 * Compiles and runs the README examples.
	 *
	 * @return a test for each example
	 * @throws Exception if the README cannot be read or the examples do not compile
	 */
	@TestFactory
	public List<DynamicTest> readmeExamplesRun() throws Exception
	{
		String readme = new String(Files.readAllBytes(Paths.get("README.md")), StandardCharsets.UTF_8);
		List<Example> examples = examples();

		Path dir = Files.createTempDirectory("readme-examples");
		Path packageDir = Files.createDirectories(dir.resolve("readme"));
		List<File> sources = new ArrayList<File>();

		for (int i = 0; i < examples.size(); i++) {

			String code = extractCode(readme, examples.get(i).heading);
			Path source = packageDir.resolve("Example" + i + ".java");
			Files.write(source, toSource(code, "Example" + i).getBytes(StandardCharsets.UTF_8));
			sources.add(source.toFile());
		}

		compile(dir, sources);

		URLClassLoader loader = new URLClassLoader(new URL[] {dir.toUri().toURL()}, getClass().getClassLoader());
		List<DynamicTest> tests = new ArrayList<DynamicTest>();

		for (int i = 0; i < examples.size(); i++) {

			Example example = examples.get(i);
			String className = "readme.Example" + i;

			tests.add(dynamicTest(example.heading, () -> run(loader, className, example)));
		}

		return tests;
	}

	/**
	 * Runs one compiled example and checks its requests.
	 *
	 * @param loader the class loader of the compiled examples
	 * @param className the class name
	 * @param example the example
	 * @throws Throwable if the example fails
	 */
	private void run(ClassLoader loader, String className, Example example) throws Throwable
	{
		RecordingHttpClient client = RecordingHttpClient.install();

		try {
			for (Object[] response : example.responses) {

				if (response.length == 1) {
					client.fail((Exception) response[0]);
				} else {
					@SuppressWarnings("unchecked")
					Map<String, String> headers = (Map<String, String>) response[2];
					client.respond((Integer) response[0], (String) response[1], headers);
				}
			}

			Object instance = loader.loadClass(className).getDeclaredConstructor().newInstance();

			try {
				instance.getClass().getMethod("example").invoke(instance);
			} catch (InvocationTargetException e) {
				throw e.getCause();
			}

			List<String> sent = new ArrayList<String>();

			for (RecordedRequest request : client.requests()) {
				sent.add(request.method + " " + request.uri.toString().substring(BASE.length()));
			}

			assertEquals(example.requests, sent, "requests of the example");
			assertEquals(0, client.pendingResponses(), "the example must use all the queued responses");
		} finally {
			RecordingHttpClient.uninstall();
		}
	}

	/**
	 * Finds the first Java code block of a README section.
	 *
	 * @param readme the README
	 * @param heading the heading of the section, without "### "
	 * @return the code
	 */
	private static String extractCode(String readme, String heading)
	{
		List<String> lines = Arrays.asList(readme.split("\n", -1));
		int start = lines.indexOf("### " + heading);

		if (start < 0) {
			throw new AssertionError("The README has no section \"### " + heading + "\"");
		}

		StringBuilder code = new StringBuilder();
		boolean inCode = false;

		for (int i = start + 1; i < lines.size(); i++) {

			String line = lines.get(i);

			if (!inCode && line.startsWith("#")) {
				break;
			}

			if (!inCode && line.equals("```java")) {
				inCode = true;
			} else if (inCode && line.equals("```")) {
				return code.toString();
			} else if (inCode) {
				code.append(line).append("\n");
			}
		}

		throw new AssertionError("The README section \"### " + heading + "\" has no Java code block");
	}

	/**
	 * Makes a compilable class from a README example.
	 *
	 * @param code the example code
	 * @param className the class name
	 * @return the source
	 */
	private static String toSource(String code, String className)
	{
		if (!code.contains("class Example {") || !code.contains("public void example()")) {
			throw new AssertionError("A README example must have \"class Example {\" and \"public void example()\":\n" + code);
		}

		StringBuilder source = new StringBuilder("package readme;\n\n");

		for (String name : IMPORTS) {
			source.append("import ").append(name).append(";\n");
		}

		source.append("\n");
		source.append(code
				.replace("class Example {", "public class " + className + " extends " + ReadmeExampleBase.class.getName() + " {")
				.replace("e.printStackTrace();", "throw new RuntimeException(e);"));

		return source.toString();
	}

	/**
	 * Compiles the examples for Java 11.
	 *
	 * @param dir the output folder
	 * @param sources the source files
	 * @throws Exception if the compiler fails
	 */
	private static void compile(Path dir, List<File> sources) throws Exception
	{
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

		String classPath = String.join(File.pathSeparator,
				location(MailerLite.class), location(ReadmeExampleBase.class), location(Gson.class));

		try (StandardJavaFileManager files = compiler.getStandardFileManager(diagnostics, null, StandardCharsets.UTF_8)) {

			List<String> options = Arrays.asList("--release", "11", "-classpath", classPath, "-d", dir.toString());
			boolean ok = compiler.getTask(null, files, diagnostics, options, null, files.getJavaFileObjectsFromFiles(sources)).call();

			if (!ok) {
				StringBuilder message = new StringBuilder("The README examples do not compile:\n");

				for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
					message.append(diagnostic.toString()).append("\n");
				}

				throw new AssertionError(message.toString());
			}
		}
	}

	/**
	 * Gets the class path entry of a class.
	 *
	 * @param type the class
	 * @return the path of its folder or JAR file
	 * @throws Exception if the location is not a file
	 */
	private static String location(Class<?> type) throws Exception
	{
		return Paths.get(type.getProtectionDomain().getCodeSource().getLocation().toURI()).toString();
	}
}
