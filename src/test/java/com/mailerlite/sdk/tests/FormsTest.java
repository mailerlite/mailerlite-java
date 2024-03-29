/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
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
import com.mailerlite.sdk.vcr.VcrRecorder;

/**
 * The Class FormsTest.
 */
public class FormsTest extends TestBase {

	/**
	 * Sets the up each.
	 *
	 * @param info the new up each
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@BeforeEach
	public void setupEach(TestInfo info) throws IOException
	{
		VcrRecorder.useRecording("FormsTest_" + info.getDisplayName());
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
	 * Test get forms.
	 */
	@Test
	public void TestGetForms()
	{
		
		try {
			
			FormsList forms = this.getMailerLite().forms().retriever().get("embedded");
			
			for (Form f : forms.forms) {
				
				System.out.println(f.id);
				System.out.println(f.name);
			}
			
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * Test get single form.
	 */
	@Test
	public void TestGetSingleForm()
	{
		try {
			
			FormsList forms = this.getMailerLite().forms().retriever().get("embedded");
			
			SingleForm f = this.getMailerLite().forms().retriever().getSingle(forms.forms[0].id);
			
			System.out.println(f.form.id);
			System.out.println(f.form.name);
			
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * Test get form subscribers.
	 */
	@Test
	public void TestGetFormSubscribers()
	{
		try {
			
			FormsList forms = this.getMailerLite().forms().retriever().get("embedded");
			
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
	
	/**
	 * Test update form.
	 */
	@Test
	public void TestUpdateForm()
	{
		try {
					
			FormsList forms = this.getMailerLite().forms().retriever().get("embedded");
			
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
	
	/**
	 * Test delete form.
	 */
	@Test
	public void TestDeleteForm()
	{
		try {
			
			FormsList forms = this.getMailerLite().forms().retriever().get("embedded");
			
			MailerLiteResponse response = this.getMailerLite().forms().delete(forms.forms[0].id);
			
			assertEquals(204, response.responseStatusCode);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
}
