/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.mailerlite.sdk.MailerLiteResponse;
import com.mailerlite.sdk.campaigns.Campaign;
import com.mailerlite.sdk.campaigns.CampaignDelivery;
import com.mailerlite.sdk.campaigns.CampaignScheduler;
import com.mailerlite.sdk.campaigns.CampaignSubscriberActivityList;
import com.mailerlite.sdk.campaigns.CampaignsList;
import com.mailerlite.sdk.campaigns.SingleCampaign;
import com.mailerlite.sdk.emails.EmailBase;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.vcr.VcrRecorder;

/**
 * The Class CampaignsTest.
 */
public class CampaignsTest extends TestBase {

	/**
	 * Sets the up each.
	 *
	 * @param info the new up each
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@BeforeEach
	public void setupEach(TestInfo info) throws IOException
	{
		VcrRecorder.useRecording("CampaignsTest_" + info.getDisplayName());
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
	 * Test get campaigns.
	 */
	@Test
	public void TestGetCampaigns()
	{
		
		try {
			
			CampaignsList campaigns = this.getMailerLite().campaigns().retriever().filter("status", "sent").get();
			
			for (Campaign c : campaigns.campaigns) {
				
				System.out.println(c.id);
				System.out.println(c.createdAt.toString());
			}
			
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
	
	
	/**
	 * Test get single campaign.
	 */
	@Test
	public void TestGetSingleCampaign()
	{
		try {
			
			CampaignsList campaigns = this.getMailerLite().campaigns().retriever().filter("status", "sent").get();
			
			Campaign campaign = this.getMailerLite().campaigns().retriever().getSingle(campaigns.campaigns[0].id).campaign;
			
			System.out.println(campaign.id);
			
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * Test create campaign.
	 */
	@Test
	public void TestCreateCampaign()
	{
		EmailBase email = new EmailBase();
		email.content = "<div>Test email content</div>";
		email.from = TestHelper.fromEmail;
		email.fromName = "test from name";
		email.subject = "test email subject";
		
		try {
		
			SingleCampaign newCampaign = this.getMailerLite().campaigns().builder()
			.name("test campaign name")
			.email(email)
			.type("regular")
			.create();
			
			System.out.println(newCampaign.campaign.id);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * Test update campaign.
	 */
	@Test
	public void testUpdateCampaign()
	{
		try {
			
			EmailBase email = new EmailBase();
			email.content = "<div>Test email content</div>";
			email.from = TestHelper.fromEmail;
			email.fromName = "test from name";
			email.subject = "test email subject";
			
			SingleCampaign newCampaign = this.createCampaign();;
			
			SingleCampaign updatedCampaign = this.getMailerLite().campaigns().builder()
					.name("Updated campaign name")
					.email(email)
					.type("regular")
					.update(newCampaign.campaign.id);
			
			assertEquals("Updated campaign name", updatedCampaign.campaign.name);
			
		} catch (MailerLiteException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	
	/**
	 * Test send campaign.
	 */
	@Test
	public void testSendCampaign()
	{
		try {
			
			SingleCampaign newCampaign = this.createCampaign("Campaign to send");
			
			SingleCampaign campaign = this.getMailerLite()
					.campaigns()
					.scheduler()
					.delivery(CampaignDelivery.INSTANT)
					.schedule(newCampaign.campaign.id);
			
			assertEquals(200, campaign.responseStatusCode);
			
		} catch (MailerLiteException e) {
			e.printStackTrace();
			fail();
		}
	}
		
	
	/**
	 * Test delete campaign.
	 */
	@Test
	public void testDeleteCampaign()
	{
		try {
			
			SingleCampaign newCampaign = this.createCampaign("Campaign to delete");
			
		
			MailerLiteResponse response = this.getMailerLite().campaigns().delete(newCampaign.campaign.id);
			
			assertEquals(204, response.responseStatusCode);
			
		} catch (MailerLiteException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	
	/**
	 * Test get subscriber activity.
	 */
	@Test
	public void testGetSubscriberActivity()
	{
		try {
						
			CampaignSubscriberActivityList activity = this.getMailerLite().campaigns().subscriberActivity().get(TestHelper.campaignIdToGetActivity);
			
			assertTrue(activity.activity.length > 0);
			
			
		} catch (MailerLiteException e) {
			e.printStackTrace();
			fail();
		}
		
	}
	
	
	/**
	 * Creates the campaign.
	 *
	 * @return the single campaign
	 * @throws MailerLiteException the mailer lite exception
	 */
	private SingleCampaign createCampaign() throws MailerLiteException
	{
		return this.createCampaign("test campaign name");
	}
	
	/**
	 * Creates the campaign.
	 *
	 * @param name the name
	 * @return the single campaign
	 * @throws MailerLiteException the mailer lite exception
	 */
	private SingleCampaign createCampaign(String name) throws MailerLiteException
	{
		EmailBase email = new EmailBase();
		email.content = "<div>Test email content</div>";
		email.from = TestHelper.fromEmail;
		email.fromName = "test from name";
		email.subject = name;
		
		return this.getMailerLite().campaigns().builder()
		.name(name)
		.email(email)
		.type("regular")
		.groupId(TestHelper.groupId)
		.create();
	}
}
