package com.mailerlite.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.mailerlite.sdk.campaigns.Campaign;
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
			
			CampaignsList campaigns = this.getMailerLite().campaigns().retriever().filter("status", "draft").get();
			
			SingleCampaign updatedCampaign = this.getMailerLite().campaigns().builder()
					.name("Updated campaign name")
					.email(email)
					.type("regular")
					.update(campaigns.campaigns[0].id);
			
			assertEquals("Updated campaign name", updatedCampaign.campaign.name);
			
		} catch (MailerLiteException e) {
			e.printStackTrace();
			fail();
		}
	}
}
