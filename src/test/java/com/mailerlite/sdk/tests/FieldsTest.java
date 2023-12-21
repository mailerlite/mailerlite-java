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
import com.mailerlite.sdk.fields.Field;
import com.mailerlite.sdk.fields.FieldList;
import com.mailerlite.sdk.fields.SingleField;
import com.mailerlite.sdk.vcr.VcrRecorder;

/**
 * The Class FieldsTest.
 */
public class FieldsTest extends TestBase {

	/**
	 * Sets the up each.
	 *
	 * @param info the new up each
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@BeforeEach
	public void setupEach(TestInfo info) throws IOException
	{
		VcrRecorder.useRecording("FieldsTest_" + info.getDisplayName());
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
	 * Test get fields.
	 */
	@Test
	public void TestGetFields()
	{
		
		try {
			
			FieldList fields = this.getMailerLite().fields().retriever().get();
			
			for (Field f : fields.fields) {
				
				System.out.println(f.id);
				System.out.println(f.name);
			}
			
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * Test create field.
	 */
	@Test
	public void TestCreateField()
	{
		try {
			
			SingleField field = this.getMailerLite().fields().builder().create("test create field", "text");
			
			System.out.println(field.field.id);
			System.out.println(field.field.name);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
	
	
	/**
	 * Test update field.
	 */
	@Test
	public void TestUpdateField()
	{
		try {
						
			SingleField field = this.getMailerLite().fields().builder().update(TestHelper.fieldToUpdateId, "new field name");
			
			System.out.println(field.field.id);
			System.out.println(field.field.name);
			
			assertEquals("new field name", field.field.name);
			
		} catch (MailerLiteException e) {
			System.out.println(e.responseBody);
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * Test delete field.
	 */
	@Test
	public void TestDeleteField()
	{
		try {
			
			MailerLiteResponse response = this.getMailerLite().fields().delete(TestHelper.fieldToDeleteId);
			
			assertEquals(204, response.responseStatusCode);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
			fail();
		}
	}
}
