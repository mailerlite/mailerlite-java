package com.mailerlite.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.mailerlite.sdk.campaigns.CampaignsList;
import com.mailerlite.sdk.campaigns.SingleCampaign;
import com.mailerlite.sdk.emails.EmailBase;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.vcr.VcrRecorder;

public class CampaignsTest extends TestBase {

	@BeforeEach
	public void setupEach(TestInfo info) throws IOException
	{
		// VcrRecorder.useRecording("CampaignsTest_" + info.getDisplayName());
	}
	
	@AfterEach
	public void afterEach() throws IOException
	{
		// VcrRecorder.stopRecording();
	}
	
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
	
	@Test
	public void testScheduleCampaign()
	{
		try {
			
			SingleCampaign newCampaign = this.createCampaign("Campaign to schedule");
			
			CampaignScheduler scheduler = this.getMailerLite().campaigns().scheduler();
			
			scheduler.scheduleSettings()
			.date("2023-03-01")
			.hours("09:00")
			.minutes("00:00");
			
			SingleCampaign campaign = scheduler
			.delivery(CampaignDelivery.SCHEDULED)
			.schedule(newCampaign.campaign.id);

			
			assertEquals(200, campaign.responseStatusCode);
			
		} catch (MailerLiteException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	
	@Test
	public void testCancelCampaign()
	{
		try {
			
			SingleCampaign newCampaign = this.createCampaign("Campaign to cancel");
			
			CampaignScheduler scheduler = this.getMailerLite().campaigns().scheduler();
			
			scheduler.scheduleSettings()
			.date("2023-03-01")
			.hours("09")
			.minutes("00");
			
			SingleCampaign campaign = scheduler
			.delivery(CampaignDelivery.SCHEDULED)
			.schedule(newCampaign.campaign.id);

			
			assertEquals(200, campaign.responseStatusCode);
			
			SingleCampaign canceledCampaign = this.getMailerLite().campaigns().scheduler().cancel(campaign.campaign.id);
			
			assertEquals(200, canceledCampaign.responseStatusCode);
			
		} catch (MailerLiteException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	
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
	
	
	private SingleCampaign createCampaign() throws MailerLiteException
	{
		return this.createCampaign("test campaign name");
	}
	
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
