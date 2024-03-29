/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.vcr;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.mailerlite.sdk.util.MailerLiteHttpClientFactory;

/**
 * The Class VcrRecorder.
 */
public class VcrRecorder {

	/** The tape. */
	private static VcrTape tape;
	
	/**
	 * Loads the tape and sets up HttpClientVcr to the MaielrLiteHttpClientFactory.
	 *
	 * @param tapeName the tape name
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void useRecording(String tapeName) throws IOException
	{
		Path resourceDirectory = Paths.get("src","test","resources", "fixtures", tapeName + ".json");
		String absolutePath = resourceDirectory.toFile().getAbsolutePath();
		
		if (tape != null) {
			tape.saveTape();
		}
		
		tape = new VcrTape();
		tape.loadTape(absolutePath);
		
    	HttpClientVcr vcr = new HttpClientVcr();
    	vcr.setTape(tape);
    	
    	MailerLiteHttpClientFactory.getInstance().setClient(vcr);
	}
	
	/**
	 * Stops the recording and saves the tape.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void stopRecording() throws IOException
	{
		if (tape != null) {
			tape.saveTape();
			tape = null;
		}
	}
	
}
