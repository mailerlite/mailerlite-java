package com.mailerlite.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import com.mailerlite.sdk.MailerLiteResponse;
import com.mailerlite.sdk.exceptions.MailerLiteException;
import com.mailerlite.sdk.forms.Form;
import com.mailerlite.sdk.forms.FormsList;
import com.mailerlite.sdk.forms.SingleForm;
import com.mailerlite.sdk.susbcribers.Subscriber;
import com.mailerlite.sdk.susbcribers.SubscribersList;

public class FormsTest extends TestBase {

	@BeforeEach
	public void setupEach(TestInfo info) throws IOException
	{
		// VcrRecorder.useRecording("FormsTest_" + info.getDisplayName());
	}
	
	@AfterEach
	public void afterEach() throws IOException
	{
		// VcrRecorder.stopRecording();
	}

	@Test
	public void TestGetForms()
	{
		
		try {
			
			FormsList forms = this.getMailerLite().forms().retriever().get();
			
			for (Form f : forms.forms) {
				
				System.out.println(f.id);
				System.out.println(f.name);
			}
			
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void TestGetFormSubscribers()
	{
		try {
			
			FormsList forms = this.getMailerLite().forms().retriever().get();
			
			SubscribersList list = this.getMailerLite().forms().subscribers().get(forms.forms[0].id);
			
			for (Subscriber sub : list.subscribers) {
				System.out.println(sub.id);
				System.out.println(sub.email);
			}
			
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void TestUpdateForm()
	{
		try {
					
			FormsList forms = this.getMailerLite().forms().retriever().get();
			
			SingleForm form = this.getMailerLite().forms().update(forms.forms[0].id, "new form name");
			
			System.out.println(form.form.id);
			System.out.println(form.form.name);
			
			assertEquals("new form name", form.form.name);
			
		} catch (MailerLiteException e) {
			System.out.println(e.responseBody);
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void TestDeleteForm()
	{
		try {
			
			FormsList forms = this.getMailerLite().forms().retriever().get();
			
			MailerLiteResponse response = this.getMailerLite().forms().delete(forms.forms[0].id);
			
			assertEquals(204, response.responseStatusCode);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
}
