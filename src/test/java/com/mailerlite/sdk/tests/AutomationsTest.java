package com.mailerlite.sdk.tests;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.mailerlite.sdk.campaigns.Campaign;
import com.mailerlite.sdk.campaigns.CampaignsList;
import com.mailerlite.sdk.exceptions.MailerLiteException;

public class AutomationsTest extends TestBase {

	@BeforeEach
	public void setupEach(TestInfo info) throws IOException
	{
		// VcrRecorder.useRecording("AutomationsTest_" + info.getDisplayName());
	}
	
	@AfterEach
	public void afterEach() throws IOException
	{
		// VcrRecorder.stopRecording();
	}
	
	@Test
	public void TestGetAutomations()
	{
		
		try {
			
			this.getMailerLite().automations().retriever().get();
		
			
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
}
