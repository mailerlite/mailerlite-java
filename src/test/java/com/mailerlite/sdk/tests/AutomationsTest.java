/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.tests;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.mailerlite.sdk.automations.Automation;
import com.mailerlite.sdk.automations.activity.SubscriberActivity;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.vcr.VcrRecorder;

/**
 * The Class AutomationsTest.
 */
public class AutomationsTest extends TestBase {

	/**
	 * Sets the up each.
	 *
	 * @param info the new up each
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@BeforeEach
	public void setupEach(TestInfo info) throws IOException
	{
		VcrRecorder.useRecording("AutomationsTest_" + info.getDisplayName());
	}
	
	/**
	 * After each.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@AfterEach
	public void afterEach() throws IOException
	{
		VcrRecorder.stopRecording();
	}
	
	/**
	 * Test get automations.
	 */
	@Test
	public void TestGetAutomations()
	{
		
		try {
			
			List<Automation> automations = this.getMailerLite().automations().retriever().get();
		
			for (Automation automation : automations) {
				System.out.println(automation.id);
				
				for (Object trigger : automation.triggers) {
					
					System.out.println(trigger.getClass());
				}
			}
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * Test get single automation.
	 */
	@Test
	public void TestGetSingleAutomation()
	{
		try {
			
			List<Automation> automations = this.getMailerLite().automations().retriever().get();
		
			Automation automation = this.getMailerLite().automations().retriever().getSingle(automations.get(0).id);
			
			System.out.println(automation.id);
			for (Object trigger : automation.triggers) {
				
				System.out.println(trigger.getClass());
			}
			
			for (Object step : automation.steps) {
				
				System.out.println(step.getClass());
			}
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * Test get automation subscriber activity.
	 */
	@Test
	public void TestGetAutomationSubscriberActivity()
	{
		try {
			
			List<Automation> automations = this.getMailerLite().automations().retriever().get();
		
			List<SubscriberActivity> activity = this.getMailerLite()
					.automations()
					.subscriberActivityRetriever()
					.filter("status", "completed")
					.get(automations.get(0).id);
			
			for (SubscriberActivity act : activity) {
				System.out.println(act.id);
				System.out.println(act.status);
			}
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
}
