/*************************************************
 * MailerLite Java SDK
 * https://github.com/mailerlite/mailerlite-java
 * 
 * @author MailerLite <support@mailerlite.com>
 * https://mailerlite.com
 **************************************************/
package com.mailerlite.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mailerlite.sdk.automations.Automation;
import com.mailerlite.sdk.automations.AutomationActivityFilter;
import com.mailerlite.sdk.automations.SingleAutomation;
import com.mailerlite.sdk.automations.activity.SubscriberActivity;
import com.mailerlite.sdk.automations.reports.AbTestStats;
import com.mailerlite.sdk.automations.reports.AutomationEmailStats;
import com.mailerlite.sdk.automations.reports.EcommerceStats;
import com.mailerlite.sdk.automations.reports.OrderActivity;
import com.mailerlite.sdk.automations.reports.RunSubscriberList;
import com.mailerlite.sdk.automations.reports.TriggersRunCounts;
import com.mailerlite.sdk.automations.reports.UnsubscribeReason;
import com.mailerlite.sdk.automations.steps.AbStep;
import com.mailerlite.sdk.automations.steps.AutomationStep;
import com.mailerlite.sdk.automations.steps.DelayStep;
import com.mailerlite.sdk.automations.steps.EmailStep;
import com.mailerlite.sdk.automations.steps.GenericAutomationStep;
import com.mailerlite.sdk.automations.steps.action.CopyToGroupAction;
import com.mailerlite.sdk.automations.steps.condition.ConditionStep;
import com.mailerlite.sdk.automations.templates.AutomationTemplateList;
import com.mailerlite.sdk.automations.triggers.AutomationTrigger;
import com.mailerlite.sdk.automations.triggers.GenericAutomationTrigger;
import com.mailerlite.sdk.automations.triggers.TriggerSubscriberJoinsGroup;
import com.mailerlite.sdk.automations.triggers.UpdatedAutomationTrigger;
import com.mailerlite.sdk.exceptions.MailerLiteException;

/**
 * The Class AutomationsRequestTest.
 */
public class AutomationsRequestTest extends MockTestBase {

	/** An automation with trigger, step and action types the typed parser does not know. */
	private static final String AUTOMATION_JSON = "{\"data\":{\"id\":\"100\",\"name\":\"Flow\",\"enabled\":false,"
			+ "\"automation_template_id\":7,\"automation_template_name\":\"Welcome\",\"building_in_progress\":false,"
			+ "\"stats\":{\"completed_subscribers_count\":4,\"subscribers_in_queue_count\":2},"
			+ "\"triggers\":["
			+ "{\"id\":\"1\",\"type\":\"subscriber_joins_group\",\"group_ids\":[\"5\"],\"complete\":true,\"broken\":false},"
			+ "{\"id\":\"2\",\"type\":\"subscriber_joins_segment\",\"segment_id\":\"9\",\"complete\":true,\"broken\":false},"
			+ "{\"id\":\"3\",\"type\":\"manual\",\"complete\":true,\"broken\":false},"
			+ "{\"id\":\"4\",\"type\":\"abandoned_checkout\",\"complete\":true,\"broken\":false},"
			+ "{\"id\":\"5\",\"type\":\"purchase_frequency\",\"interval\":30,\"complete\":true,\"broken\":false}],"
			+ "\"steps\":["
			+ "{\"id\":\"11\",\"type\":\"email\",\"name\":\"Hi\",\"description\":\"Send email\",\"type_count\":1},"
			+ "{\"id\":\"12\",\"type\":\"webhook\",\"url\":\"https://example.com/hook\",\"parent_id\":\"11\"},"
			+ "{\"id\":\"13\",\"type\":\"action\",\"action_type\":\"send_internal_email_notification\",\"parent_id\":\"12\"},"
			+ "{\"id\":\"14\",\"type\":\"action\",\"action_type\":null,\"parent_id\":\"13\"},"
			+ "{\"id\":\"15\",\"type\":\"abtest\",\"parent_id\":\"14\",\"split_percentages\":[{\"label\":\"A\",\"path\":\"a_path\",\"value\":50},{\"label\":\"B\",\"path\":\"b_path\",\"value\":50}]}]}}";
	
	/** A single trigger. */
	private static final String TRIGGER_JSON = "{\"data\":{\"id\":\"3\",\"type\":\"manual\",\"complete\":true,\"broken\":false}}";
	
	/** A single step. */
	private static final String STEP_JSON = "{\"data\":{\"id\":\"12\",\"type\":\"webhook\",\"parent_id\":\"11\",\"url\":\"https://example.com/hook\",\"complete\":true,\"broken\":false,\"description\":\"Webhook\"}}";
	
	/** A/B test stats. */
	private static final String AB_STATS_JSON = "{\"step_id\":\"20\",\"name\":\"Test\",\"ab_test_winner\":\"a\",\"can_select_winner\":true,\"split_percentages\":[{\"label\":\"A\",\"path\":\"a_path\",\"value\":50},{\"label\":\"B\",\"path\":\"b_path\",\"value\":50}],"
			+ "\"stats\":{\"a_path\":{\"subscribers\":10,\"sent\":10,\"opens\":5,\"clicks\":1,\"open_rate\":{\"float\":0.5,\"string\":\"50%\"}}}}";
	
	/**
	 * Test the parser falls back to the generic types for unknown triggers, steps and actions.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestParserNeverReturnsNullForUnknownTypes() throws MailerLiteException
	{
		http.respondWith(200, AUTOMATION_JSON);
		
		Automation automation = this.getMailerLite().automations().retriever().getSingle("100");
		
		assertEquals("GET", http.method());
		assertEquals("/automations/100", http.path());
		assertEquals(5, automation.triggers.length);
		assertInstanceOf(TriggerSubscriberJoinsGroup.class, automation.triggers[0]);
		assertEquals("1", ((TriggerSubscriberJoinsGroup) automation.triggers[0]).id);
		
		for (int i = 1; i < 5; i++) {
			assertInstanceOf(GenericAutomationTrigger.class, automation.triggers[i]);
		}
		
		GenericAutomationTrigger segmentTrigger = (GenericAutomationTrigger) automation.triggers[1];
		assertEquals(GenericAutomationTrigger.TYPE_SUBSCRIBER_JOINS_SEGMENT, segmentTrigger.type);
		assertEquals("2", segmentTrigger.id);
		assertTrue(segmentTrigger.complete);
		assertEquals("9", segmentTrigger.data.get("segment_id").getAsString());
		assertEquals(GenericAutomationTrigger.TYPE_MANUAL, ((GenericAutomationTrigger) automation.triggers[2]).type);
		assertEquals(GenericAutomationTrigger.TYPE_ABANDONED_CHECKOUT, ((GenericAutomationTrigger) automation.triggers[3]).type);
		assertEquals(GenericAutomationTrigger.TYPE_PURCHASE_FREQUENCY, ((GenericAutomationTrigger) automation.triggers[4]).type);
		
		assertEquals(5, automation.steps.length);
		assertInstanceOf(AbStep.class, automation.steps[4]);
		assertEquals("a_path", JsonParser.parseString(((AbStep) automation.steps[4]).splitPercentages[0]).getAsJsonObject().get("path").getAsString());
		assertInstanceOf(EmailStep.class, automation.steps[0]);
		assertEquals("Send email", ((EmailStep) automation.steps[0]).description);
		
		GenericAutomationStep webhook = (GenericAutomationStep) automation.steps[1];
		assertEquals(GenericAutomationStep.TYPE_WEBHOOK, webhook.type);
		assertEquals("https://example.com/hook", webhook.data.get("url").getAsString());
		
		GenericAutomationStep notification = (GenericAutomationStep) automation.steps[2];
		assertEquals(GenericAutomationStep.ACTION_TYPE_SEND_INTERNAL_EMAIL_NOTIFICATION, notification.actionType);
		assertInstanceOf(GenericAutomationStep.class, automation.steps[3]);
		
		assertEquals("7", automation.automationTemplateId);
		assertEquals("Welcome", automation.automationTemplateName);
		assertEquals(4, automation.stats.completedSubscribersCount);
		assertEquals(2, automation.stats.subscribersInQueueCount);
	}
	
	/**
	 * Test list sends page, sort and with_steps.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestListSendsPageSortAndWithSteps() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":[{\"id\":\"1\",\"name\":\"Flow\"}],\"links\":{},\"meta\":{}}");
		
		List<Automation> automations = this.getMailerLite().automations().retriever()
			.page(2)
			.limit(10)
			.sort("-created_at")
			.withSteps(false)
			.filter("enabled", "true")
			.get();
		
		Map<String, String> query = http.query();
		
		assertEquals("GET", http.method());
		assertEquals("/automations/", http.path());
		assertEquals("2", query.get("page"));
		assertEquals("10", query.get("limit"));
		assertEquals("-created_at", query.get("sort"));
		assertEquals("0", query.get("with_steps"));
		assertEquals("true", query.get("filter[enabled]"));
		assertEquals(1, automations.size());
		assertEquals(0, automations.get(0).steps.length);
	}
	
	/**
	 * Test create.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestCreate() throws MailerLiteException
	{
		http.respondWith(200, AUTOMATION_JSON);
		
		SingleAutomation response = this.getMailerLite().automations().builder().name("Flow").create();
		
		JsonObject body = http.body();
		
		assertEquals("POST", http.method());
		assertEquals("/automations", http.path());
		assertEquals("Flow", body.get("name").getAsString());
		assertEquals(1, body.size());
		assertEquals("100", response.automation.id);
		assertEquals(5, response.automation.triggers.length);
	}
	
	/**
	 * Test create refuses triggers and trigger data, which the create endpoint does not accept.
	 */
	@Test
	public void TestCreateRejectsTriggers()
	{
		Map<String, Object> trigger = new HashMap<String, Object>();
		trigger.put("type", "subscriber_joins_group");
		
		assertThrows(IllegalStateException.class, () -> this.getMailerLite().automations().builder().name("Flow").addTrigger(trigger).create());
		assertThrows(IllegalStateException.class, () -> this.getMailerLite().automations().builder().name("Flow").triggerData(new HashMap<String, Object>()).create());
		assertFalse(http.hasRequest());
	}
	
	/**
	 * Test update requires a name.
	 */
	@Test
	public void TestUpdateRequiresName()
	{
		assertThrows(IllegalStateException.class, () -> this.getMailerLite().automations().builder().triggerData(new HashMap<String, Object>()).update("100"));
		assertFalse(http.hasRequest());
	}
	
	/**
	 * Test with_subscribers is sent only when true.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestActivityWithSubscribersFalseSendsNothing() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":[]}");
		
		this.getMailerLite().automations().subscriberActivityRetriever().filter("status", "active").withSubscribers(false).get("100");
		assertFalse(http.query().containsKey("with_subscribers"));
		
		this.getMailerLite().automations().subscriberActivityRetriever().filter("status", "active").withSubscribers(true).withSubscribers(false).get("100");
		assertFalse(http.query().containsKey("with_subscribers"));
		
		this.getMailerLite().automations().subscriberActivityRetriever().filter("status", "active").withSubscribers(true).get("100");
		assertEquals("1", http.query().get("with_subscribers"));
	}
	
	/**
	 * Test update.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestUpdate() throws MailerLiteException
	{
		http.respondWith(200, AUTOMATION_JSON);
		
		Map<String, Object> triggerData = new HashMap<String, Object>();
		triggerData.put("repeatable", true);
		
		Map<String, Object> trigger = new HashMap<String, Object>();
		trigger.put("type", "subscriber_joins_group");
		
		this.getMailerLite().automations().builder().name("Renamed").triggerData(triggerData).addTrigger(trigger).update("100");
		
		JsonObject body = http.body();
		
		assertEquals("PUT", http.method());
		assertEquals("/automations/100", http.path());
		assertEquals("Renamed", body.get("name").getAsString());
		assertTrue(body.getAsJsonObject("trigger_data").get("repeatable").getAsBoolean());
		assertEquals("subscriber_joins_group", body.getAsJsonArray("triggers").get(0).getAsJsonObject().get("type").getAsString());
	}
	
	/**
	 * Test delete.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestDelete() throws MailerLiteException
	{
		http.respondWith(204, "");
		
		assertEquals(204, this.getMailerLite().automations().delete("100").responseStatusCode);
		assertEquals("DELETE", http.method());
		assertEquals("/automations/100", http.path());
	}
	
	/**
	 * Test copy.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestCopy() throws MailerLiteException
	{
		http.respondWith(200, AUTOMATION_JSON);
		
		assertEquals("100", this.getMailerLite().automations().copy("100").automation.id);
		assertEquals("POST", http.method());
		assertEquals("/automations/100/copy", http.path());
	}
	
	/**
	 * Test enable.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestEnable() throws MailerLiteException
	{
		http.respondWith(200, AUTOMATION_JSON);
		
		assertEquals("100", this.getMailerLite().automations().enable("100").automation.id);
		assertEquals("POST", http.method());
		assertEquals("/automations/100/enable", http.path());
	}
	
	/**
	 * Test disable.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestDisable() throws MailerLiteException
	{
		http.respondWith(200, AUTOMATION_JSON);
		
		assertEquals("100", this.getMailerLite().automations().disable("100").automation.id);
		assertEquals("POST", http.method());
		assertEquals("/automations/100/disable", http.path());
	}
	
	/**
	 * Test send test emails.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestTest() throws MailerLiteException
	{
		http.respondWith(204, "");
		
		this.getMailerLite().automations().test("100", "me@example.com");
		
		assertEquals("POST", http.method());
		assertEquals("/automations/100/test", http.path());
		assertEquals("me@example.com", http.body().get("email").getAsString());
	}
	
	/**
	 * Test activity sends page and sort and parses the subscriber.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestActivity() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":[{\"id\":\"1\",\"status\":\"Completed\",\"subscriber\":{\"id\":\"9\",\"email\":\"a@example.com\",\"name\":\"A\"}}],\"links\":{},\"meta\":{}}");
		
		List<SubscriberActivity> activity = this.getMailerLite().automations().subscriberActivityRetriever()
			.page(2)
			.limit(10)
			.sort("-date")
			.withSubscribers(true)
			.filter("status", "completed")
			.filter("search", "a+b@example.com")
			.filter("date_from", "2024-01-01")
			.get("100");
		
		Map<String, String> query = http.query();
		
		assertEquals("GET", http.method());
		assertEquals("/automations/100/activity", http.path());
		assertEquals("2", query.get("page"));
		assertEquals("10", query.get("limit"));
		assertEquals("-date", query.get("sort"));
		assertEquals("1", query.get("with_subscribers"));
		assertEquals("completed", query.get("filter[status]"));
		assertEquals("a+b@example.com", query.get("filter[search]"));
		assertEquals("2024-01-01", query.get("filter[date_from]"));
		assertEquals("a@example.com", activity.get(0).subscriber.email);
	}
	
	/**
	 * Test add subscribers.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestAddSubscribers() throws MailerLiteException
	{
		http.respondWith(204, "");
		
		this.getMailerLite().automations().addSubscribers("100", Arrays.asList("a@example.com"), Arrays.asList("5"), "11");
		
		JsonObject body = http.body();
		
		assertEquals("POST", http.method());
		assertEquals("/automations/100/add-subscribers", http.path());
		assertEquals("a@example.com", body.getAsJsonArray("emails").get(0).getAsString());
		assertEquals("5", body.getAsJsonArray("groups").get(0).getAsString());
		assertEquals("11", body.get("step_id").getAsString());
	}
	
	/**
	 * Test readd.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestReadd() throws MailerLiteException
	{
		http.respondWith(204, "");
		
		AutomationActivityFilter filter = new AutomationActivityFilter().status("failed").addId("1").search("a@").dateFrom("2024-01-01").dateTo("2024-02-01").step("11");
		
		this.getMailerLite().automations().readd("100", "selected_step", "11", filter);
		
		JsonObject body = http.body();
		JsonObject sentFilter = body.getAsJsonObject("filter");
		
		assertEquals("POST", http.method());
		assertEquals("/automations/100/readd", http.path());
		assertEquals("selected_step", body.get("readd_subscribers_to").getAsString());
		assertEquals("11", body.get("selected_step_id").getAsString());
		assertEquals("failed", sentFilter.get("status").getAsString());
		assertEquals("1", sentFilter.getAsJsonArray("id").get(0).getAsString());
		assertEquals("a@", sentFilter.get("search").getAsString());
		assertEquals("2024-01-01", sentFilter.get("date_from").getAsString());
		assertEquals("2024-02-01", sentFilter.get("date_to").getAsString());
		assertEquals("11", sentFilter.get("step").getAsString());
	}
	
	/**
	 * Test add activity to groups.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestAddActivityToGroups() throws MailerLiteException
	{
		http.respondWith(204, "");
		
		AutomationActivityFilter filter = new AutomationActivityFilter().status("active").reason("x").scheduledFrom("2024-01-01").scheduledTo("2024-02-01").nextStep("12").currentStep("11");
		
		this.getMailerLite().automations().addActivityToGroups("100", Arrays.asList("5"), filter);
		
		JsonObject body = http.body();
		JsonObject sentFilter = body.getAsJsonObject("filter");
		
		assertEquals("POST", http.method());
		assertEquals("/automations/100/activity/add-to-group", http.path());
		assertEquals("5", body.getAsJsonArray("destination_groups").get(0).getAsString());
		assertEquals("active", sentFilter.get("status").getAsString());
		assertEquals("x", sentFilter.get("reason").getAsString());
		assertEquals("2024-01-01", sentFilter.get("scheduled_from").getAsString());
		assertEquals("2024-02-01", sentFilter.get("scheduled_to").getAsString());
		assertEquals("12", sentFilter.get("next_step").getAsString());
		assertEquals("11", sentFilter.get("current_step").getAsString());
	}
	
	/**
	 * Test cancel activity.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestCancelActivity() throws MailerLiteException
	{
		http.respondWith(204, "");
		
		this.getMailerLite().automations().cancelActivity("100", new AutomationActivityFilter().status("active"));
		
		JsonObject body = http.body();
		
		assertEquals("POST", http.method());
		assertEquals("/automations/100/activity/cancel-automation", http.path());
		assertEquals("active", body.getAsJsonObject("filter").get("status").getAsString());
		assertEquals(1, body.size());
	}
	
	/**
	 * Test skip activity delay.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestSkipActivityDelay() throws MailerLiteException
	{
		http.respondWith(204, "");
		
		this.getMailerLite().automations().skipActivityDelay("100", new AutomationActivityFilter().status("active"));
		
		JsonObject body = http.body();
		
		assertEquals("POST", http.method());
		assertEquals("/automations/100/activity/skip-delay", http.path());
		assertEquals("active", body.getAsJsonObject("filter").get("status").getAsString());
		assertEquals(1, body.size());
	}
	
	/**
	 * Test list triggers.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestListTriggers() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":[{\"id\":\"3\",\"type\":\"manual\",\"complete\":true},{\"id\":\"4\",\"type\":\"subscriber_joins_group\",\"group_ids\":[\"5\"]}]}");
		
		AutomationTrigger[] triggers = this.getMailerLite().automations().triggers("100").get().triggers;
		
		assertEquals("GET", http.method());
		assertEquals("/automations/100/triggers", http.path());
		GenericAutomationTrigger manual = assertInstanceOf(GenericAutomationTrigger.class, triggers[0]);
		assertEquals(GenericAutomationTrigger.TYPE_MANUAL, manual.type);
		assertEquals("3", manual.data.get("id").getAsString());
		TriggerSubscriberJoinsGroup joins = assertInstanceOf(TriggerSubscriberJoinsGroup.class, triggers[1]);
		assertEquals("5", joins.groupIds[0]);
	}
	
	/**
	 * Test get trigger.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestGetTrigger() throws MailerLiteException
	{
		http.respondWith(200, TRIGGER_JSON);
		
		assertInstanceOf(GenericAutomationTrigger.class, this.getMailerLite().automations().triggers("100").getSingle("3").trigger);
		assertEquals("GET", http.method());
		assertEquals("/automations/100/triggers/3", http.path());
	}
	
	/**
	 * Test create trigger.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestCreateTrigger() throws MailerLiteException
	{
		http.respondWith(200, TRIGGER_JSON);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("group_ids", Arrays.asList("5"));
		
		this.getMailerLite().automations().triggers("100").create(GenericAutomationTrigger.TYPE_SUBSCRIBER_JOINS_GROUP, data);
		
		JsonObject body = http.body();
		
		assertEquals("POST", http.method());
		assertEquals("/automations/100/triggers", http.path());
		assertEquals("subscriber_joins_group", body.get("type").getAsString());
		assertEquals("5", body.getAsJsonObject("data").getAsJsonArray("group_ids").get(0).getAsString());
	}
	
	/**
	 * Test update trigger.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestUpdateTrigger() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":{\"trigger\":{\"id\":\"3\",\"type\":\"subscriber_joins_segment\",\"segment_id\":\"9\",\"segment\":{\"id\":\"9\",\"name\":\"S\"},\"broken\":false,\"complete\":true},"
				+ "\"trigger_data\":{\"track_ecommerce\":false,\"broken_workflow\":false,\"complete_workflow\":false,\"repeatable\":true,\"valid\":true},\"qualified_subscribers_count\":12}}");
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("segment_id", "9");
		
		UpdatedAutomationTrigger response = this.getMailerLite().automations().triggers("100").update("3", data);
		
		JsonObject body = http.body();
		
		assertEquals("PUT", http.method());
		assertEquals("/automations/100/triggers/3", http.path());
		assertEquals("9", body.getAsJsonObject("data").get("segment_id").getAsString());
		assertEquals(1, body.size());
		assertEquals("3", response.update.trigger.id);
		assertEquals(GenericAutomationTrigger.TYPE_SUBSCRIBER_JOINS_SEGMENT, response.update.trigger.type);
		assertEquals("9", assertInstanceOf(GenericAutomationTrigger.class, response.update.trigger).data.get("segment_id").getAsString());
		assertTrue(response.update.triggerData.repeatable);
		assertTrue(response.update.triggerData.valid);
		assertEquals(12, (int) response.update.qualifiedSubscribersCount);
	}
	
	/**
	 * Test delete trigger.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestDeleteTrigger() throws MailerLiteException
	{
		http.respondWith(204, "");
		
		this.getMailerLite().automations().triggers("100").delete("3");
		
		assertEquals("DELETE", http.method());
		assertEquals("/automations/100/triggers/3", http.path());
	}
	
	/**
	 * Test list steps.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestListSteps() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":[{\"id\":\"12\",\"type\":\"webhook\",\"url\":\"https://example.com/hook\"},"
				+ "{\"id\":\"13\",\"type\":\"email\",\"subject\":\"Hi\"},{\"id\":\"14\",\"type\":\"delay\",\"unit\":\"days\"},"
				+ "{\"id\":\"15\",\"type\":\"action\",\"action_type\":\"copy_to_group\",\"group_ids\":[\"5\"]},{\"id\":\"16\",\"type\":\"condition\"}]}");
		
		AutomationStep[] steps = this.getMailerLite().automations().steps("100").get().steps;
		
		assertEquals("GET", http.method());
		assertEquals("/automations/100/steps/index", http.path());
		assertEquals("https://example.com/hook", assertInstanceOf(GenericAutomationStep.class, steps[0]).data.get("url").getAsString());
		assertEquals("Hi", assertInstanceOf(EmailStep.class, steps[1]).subject);
		assertEquals("days", assertInstanceOf(DelayStep.class, steps[2]).unit);
		assertEquals("5", assertInstanceOf(CopyToGroupAction.class, steps[3]).groupIds[0]);
		assertEquals(0, assertInstanceOf(ConditionStep.class, steps[4]).conditions.length);
	}
	
	/**
	 * Test get step.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestGetStep() throws MailerLiteException
	{
		http.respondWith(200, STEP_JSON);
		
		GenericAutomationStep step = assertInstanceOf(GenericAutomationStep.class, this.getMailerLite().automations().steps("100").getSingle("12").step);
		
		assertEquals("GET", http.method());
		assertEquals("/automations/100/steps/12", http.path());
		assertEquals("11", step.parentId);
		assertEquals("Webhook", step.description);
	}
	
	/**
	 * Test create step.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestCreateStep() throws MailerLiteException
	{
		http.respondWith(200, STEP_JSON);
		
		this.getMailerLite().automations().steps("100").builder()
			.type(GenericAutomationStep.TYPE_ACTION)
			.parentId("11")
			.branch(GenericAutomationStep.BRANCH_YES)
			.moveToBranch(GenericAutomationStep.BRANCH_NO)
			.addData("action_type", GenericAutomationStep.ACTION_TYPE_COPY_TO_GROUP)
			.rescheduleWaitingSubscribers(true)
			.create();
		
		JsonObject body = http.body();
		
		assertEquals("POST", http.method());
		assertEquals("/automations/100/steps", http.path());
		assertEquals("action", body.get("type").getAsString());
		assertEquals("11", body.get("parent_id").getAsString());
		assertEquals("yes", body.get("branch").getAsString());
		assertEquals("no", body.get("move_to_branch").getAsString());
		assertEquals("copy_to_group", body.getAsJsonObject("data").get("action_type").getAsString());
		assertEquals(5, body.size());
	}
	
	/**
	 * Test update step.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestUpdateStep() throws MailerLiteException
	{
		http.respondWith(200, STEP_JSON);
		
		this.getMailerLite().automations().steps("100").builder()
			.type(GenericAutomationStep.TYPE_DELAY)
			.parentId("11")
			.branch(GenericAutomationStep.BRANCH_YES)
			.addData("value", 2)
			.rescheduleWaitingSubscribers(true)
			.update("12");
		
		JsonObject body = http.body();
		
		assertEquals("PUT", http.method());
		assertEquals("/automations/100/steps/12", http.path());
		assertEquals("11", body.get("parent_id").getAsString());
		assertEquals(2, body.getAsJsonObject("data").get("value").getAsInt());
		assertTrue(body.get("reschedule_waiting_subscribers").getAsBoolean());
		assertEquals(3, body.size());
	}
	
	/**
	 * Test delete step.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestDeleteStep() throws MailerLiteException
	{
		http.respondWith(204, "");
		
		this.getMailerLite().automations().steps("100").delete("12");
		
		assertEquals("DELETE", http.method());
		assertEquals("/automations/100/steps/12", http.path());
		assertTrue(http.query().isEmpty());
		
		this.getMailerLite().automations().steps("100").delete("12", "yes");
		
		assertEquals("DELETE", http.method());
		assertEquals("/automations/100/steps/12", http.path());
		assertEquals("yes", http.query().get("branch_to_delete"));
	}
	
	/**
	 * Test copy step.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestCopyStep() throws MailerLiteException
	{
		http.respondWith(200, AUTOMATION_JSON);
		
		SingleAutomation response = this.getMailerLite().automations().steps("100").builder()
			.parentId("11")
			.branch(GenericAutomationStep.BRANCH_A_PATH)
			.moveToBranch(GenericAutomationStep.BRANCH_YES)
			.copy("12");
		
		JsonObject body = http.body();
		
		assertEquals("POST", http.method());
		assertEquals("/automations/100/steps/12/copy", http.path());
		assertEquals("11", body.get("parent_id").getAsString());
		assertEquals("a_path", body.get("branch").getAsString());
		assertEquals("yes", body.get("move_to_branch").getAsString());
		assertEquals("100", response.automation.id);
	}
	
	/**
	 * Test reorder steps.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestReorderSteps() throws MailerLiteException
	{
		http.respondWith(200, AUTOMATION_JSON);
		
		this.getMailerLite().automations().steps("100").reorder("12", "11", GenericAutomationStep.BRANCH_NO, GenericAutomationStep.BRANCH_YES);
		
		JsonObject body = http.body();
		
		assertEquals("POST", http.method());
		assertEquals("/automations/100/steps/reorder", http.path());
		assertEquals("12", body.get("dragged_step_id").getAsString());
		assertEquals("11", body.get("new_parent_step_id").getAsString());
		assertEquals("no", body.get("branch").getAsString());
		assertEquals("yes", body.get("branch_for_child").getAsString());
	}
	
	/**
	 * Test select A/B test winner.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestSelectAbTestWinner() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":" + AB_STATS_JSON + "}");
		
		String winner = this.getMailerLite().automations().steps("100").selectAbTestWinner("20", "a").abTestStats.abTestWinner;
		
		assertEquals("PUT", http.method());
		assertEquals("/automations/100/steps/20/select-ab-test-winner", http.path());
		assertEquals("a", http.body().get("ab_test_winner").getAsString());
		assertEquals("a", winner);
	}
	
	/**
	 * Test get email content.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestGetEmailContent() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":{\"html\":\"<p>Hi</p>\"}}");
		
		assertEquals("<p>Hi</p>", this.getMailerLite().automations().emails("100").getContent("30").content.html);
		assertEquals("GET", http.method());
		assertEquals("/automations/100/emails/30/content", http.path());
	}
	
	/**
	 * Test update email content.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestUpdateEmailContent() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":{\"id\":\"30\",\"subject\":\"Hi\"}}");
		
		assertEquals("30", this.getMailerLite().automations().emails("100").updateContent("30", "<p>Hi</p>").email.id);
		assertEquals("PUT", http.method());
		assertEquals("/automations/100/emails/30/content", http.path());
		assertEquals("<p>Hi</p>", http.body().get("html").getAsString());
	}
	
	/**
	 * Test email report stats.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestEmailReportStats() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":{\"engagement\":{\"sent\":10,\"open_rate\":{\"float\":0.5,\"string\":\"50%\"}},\"deliverability\":{\"delivered_count\":9}}}");
		
		AutomationEmailStats stats = this.getMailerLite().automations().emails("100")
			.startDate("2024-01-01").endDate("2024-01-31").shopId("8")
			.reportStats("30").stats;
		
		Map<String, String> query = http.query();
		
		assertEquals("GET", http.method());
		assertEquals("/automations/100/emails/30/report-stats", http.path());
		assertEquals("2024-01-01", query.get("start_date"));
		assertEquals("2024-01-31", query.get("end_date"));
		assertEquals("8", query.get("shop_id"));
		assertEquals(10, stats.engagement.sent);
		assertEquals("50%", stats.engagement.openRate.stringValue);
		assertEquals(9, stats.deliverability.deliveredCount);
	}
	
	/**
	 * Test email ecommerce overview stats.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestEmailEcommerceOverviewStats() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":{\"ecommerce\":{\"orders_count\":3,\"revenue\":12.5,\"average_order_value\":\"4.17\",\"conversion_rate\":\"1%\"}}}");
		
		EcommerceStats stats = this.getMailerLite().automations().emails("100")
			.startDate("2024-01-01").endDate("2024-01-31").shopId("8")
			.ecommerceOverviewStats("30").stats.ecommerce;
		
		Map<String, String> query = http.query();
		
		assertEquals("GET", http.method());
		assertEquals("/automations/100/emails/30/ecommerce-overview-stats", http.path());
		assertEquals("2024-01-01", query.get("start_date"));
		assertEquals("2024-01-31", query.get("end_date"));
		assertEquals("8", query.get("shop_id"));
		assertEquals(3, stats.ordersCount);
		assertEquals(12.5, stats.revenue);
	}
	
	/**
	 * Test email unsubscribe reasons.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestEmailUnsubscribeReasons() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":[{\"label\":\"Too many emails\",\"count\":\"2\",\"percentage\":\"50%\"}]}");
		
		UnsubscribeReason reason = this.getMailerLite().automations().emails("100").unsubscribeReasons("30").reasons[0];
		
		assertEquals("GET", http.method());
		assertEquals("/automations/100/emails/30/unsubscribe-reasons", http.path());
		assertEquals(2, reason.count);
		assertEquals("50%", reason.percentage);
	}
	
	/**
	 * Test list templates.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestListTemplates() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":[{\"id\":\"1\",\"name\":\"Welcome\",\"type\":\"welcome\",\"workflow\":{\"triggers\":[{\"type\":\"subscriber_joins_group\"}]},\"workflow_steps_description\":[\"Send a welcome email\"],\"url\":\"https://example.com\"}],\"links\":{},\"meta\":{\"total\":1}}");
		
		AutomationTemplateList list = this.getMailerLite().automations().templates()
			.filter("type", "ecommerce").filter("category", "2").limit(10).page(2).get();
		
		Map<String, String> query = http.query();
		
		assertEquals("GET", http.method());
		assertEquals("/automation-templates", http.path());
		assertEquals("ecommerce", query.get("filter[type]"));
		assertEquals("2", query.get("filter[category]"));
		assertEquals("10", query.get("limit"));
		assertEquals("2", query.get("page"));
		assertEquals("Welcome", list.templates[0].name);
		assertEquals("subscriber_joins_group", list.templates[0].workflow.getAsJsonObject().getAsJsonArray("triggers").get(0).getAsJsonObject().get("type").getAsString());
		assertEquals("Send a welcome email", list.templates[0].workflowStepsDescription[0]);
		assertEquals(1, list.meta.total);
	}
	
	/**
	 * Test select template.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestSelectTemplate() throws MailerLiteException
	{
		http.respondWith(200, AUTOMATION_JSON);
		
		assertEquals("100", this.getMailerLite().automations().selectTemplate("1").automation.id);
		assertEquals("POST", http.method());
		assertEquals("/automations/select-template/1", http.path());
	}
	
	/**
	 * Test step run counts.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestStepRunCounts() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":[{\"step_id\":\"11\",\"completed_step_runs\":4,\"in_queue_step_runs\":1}]}");
		
		assertEquals(4, this.getMailerLite().automations().reports("100").stepRunCounts().stepRunCounts[0].completedStepRuns);
		assertEquals("GET", http.method());
		assertEquals("/automations/100/steps/run-counts", http.path());
	}
	
	/**
	 * Test step run count.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestStepRunCount() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":{\"step_id\":\"11\",\"completed_step_runs\":4,\"in_queue_step_runs\":1}}");
		
		assertEquals(1, this.getMailerLite().automations().reports("100").stepRunCount("11").stepRunCount.inQueueStepRuns);
		assertEquals("GET", http.method());
		assertEquals("/automations/100/steps/run-counts/11", http.path());
	}
	
	/**
	 * Test trigger run counts.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestTriggerRunCounts() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":{\"triggers\":[{\"id\":\"1\",\"completed_runs\":3,\"queued_runs\":2}],\"manual_runs\":{\"in_progress_runs\":1,\"completed_runs\":5}}}");
		
		TriggersRunCounts counts = this.getMailerLite().automations().reports("100").triggerRunCounts().triggersRunCounts;
		
		assertEquals("GET", http.method());
		assertEquals("/automations/100/triggers/run-counts", http.path());
		assertEquals(3, counts.triggers[0].completedRuns);
		assertEquals(5, counts.manualRuns.completedRuns);
	}
	
	/**
	 * Test trigger run count.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestTriggerRunCount() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":{\"completed_runs\":3,\"queued_runs\":2}}");
		
		assertEquals(2, this.getMailerLite().automations().reports("100").triggerRunCount("1").triggerRunCount.queuedRuns);
		assertEquals("GET", http.method());
		assertEquals("/automations/100/triggers/run-counts/1", http.path());
	}
	
	/**
	 * Test A/B steps stats.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestAbStepsStats() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":[" + AB_STATS_JSON + "]}");
		
		AbTestStats stats = this.getMailerLite().automations().reports("100").abStepsStats().abTestStats[0];
		
		assertEquals("GET", http.method());
		assertEquals("/automations/100/ab-steps-stats", http.path());
		assertEquals("20", stats.stepId);
		assertEquals("b_path", stats.splitPercentages[1].path);
		assertEquals(50, stats.splitPercentages[1].value);
		assertEquals("50%", stats.stats.get("a_path").openRate.stringValue);
	}
	
	/**
	 * Test A/B step stats.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestAbStepStats() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":" + AB_STATS_JSON + "}");
		
		assertEquals(10, this.getMailerLite().automations().reports("100").abStepStats("20").abTestStats.stats.get("a_path").subscribers);
		assertEquals("GET", http.method());
		assertEquals("/automations/100/ab-steps-stats/20", http.path());
	}
	
	/**
	 * Test step subscribers.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestStepSubscribers() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":[{\"id\":\"1\",\"subscriber\":{\"id\":\"9\",\"email\":\"a@example.com\"},\"date\":\"2024-01-01 10:00:00\"}],\"links\":{},\"meta\":{}}");
		
		RunSubscriberList list = this.getMailerLite().automations().reports("100").stepSubscribers("11")
			.status("completed").limit(10).page(2).sort("-date")
			.filter("date_from", "2024-01-01").filter("date_to", "2024-01-31").filter("search", "a@").filter("id", "1")
			.get();
		
		Map<String, String> query = http.query();
		
		assertEquals("GET", http.method());
		assertEquals("/automations/100/activity/steps/11/subscribers", http.path());
		assertEquals("completed", query.get("status"));
		assertEquals("10", query.get("limit"));
		assertEquals("2", query.get("page"));
		assertEquals("-date", query.get("sort"));
		assertEquals("2024-01-01", query.get("filter[date_from]"));
		assertEquals("2024-01-31", query.get("filter[date_to]"));
		assertEquals("a@", query.get("filter[search]"));
		assertEquals("1", query.get("filter[id]"));
		assertEquals("a@example.com", list.subscribers[0].subscriber.email);
		assertNotNull(list.subscribers[0].runDate);
	}
	
	/**
	 * Test trigger subscribers.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestTriggerSubscribers() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":[],\"links\":{},\"meta\":{}}");
		
		this.getMailerLite().automations().reports("100").triggerSubscribers("1").status("queued").limit(5).get();
		
		Map<String, String> query = http.query();
		
		assertEquals("GET", http.method());
		assertEquals("/automations/100/activity/triggers/1/subscribers", http.path());
		assertEquals("queued", query.get("status"));
		assertEquals("5", query.get("limit"));
	}
	
	/**
	 * Test order activity.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestOrderActivity() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":[{\"subscriber\":{\"email\":\"a@example.com\",\"id\":\"9\"},\"total\":19.99,\"updated_at\":\"2024-01-01 10:00:00\",\"order\":\"5\",\"email\":{\"id\":\"30\"}}]}");
		
		OrderActivity order = this.getMailerLite().automations().reports("100").orderActivity()
			.timeFrame("custom").timeFrameStart("2024-01-01").timeFrameEnd("2024-01-31").ecommerceShopId("8").limit(10).page(2)
			.get().orders[0];
		
		Map<String, String> query = http.query();
		
		assertEquals("GET", http.method());
		assertEquals("/automations/100/order-activity", http.path());
		assertEquals("custom", query.get("time_frame"));
		assertEquals("2024-01-01", query.get("time_frame_start"));
		assertEquals("2024-01-31", query.get("time_frame_end"));
		assertEquals("8", query.get("ecommerce_shop_id"));
		assertEquals("10", query.get("limit"));
		assertEquals("2", query.get("page"));
		assertEquals(19.99, order.total);
		assertEquals("30", order.email.id);
	}
	
	/**
	 * Test monthly email totals.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestMonthlyEmailTotals() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":{\"engagement\":{\"sent\":10},\"deliverability\":{\"sent\":10}}}");
		
		assertEquals(10, this.getMailerLite().automations().reports("100").startDate("2024-01-01").endDate("2024-01-31").shopId("8").monthlyEmailTotals().stats.engagement.sent);
		
		Map<String, String> query = http.query();
		
		assertEquals("GET", http.method());
		assertEquals("/automations/100/monthly-totals/email", http.path());
		assertEquals("2024-01-01", query.get("start_date"));
		assertEquals("2024-01-31", query.get("end_date"));
		assertEquals("8", query.get("shop_id"));
	}
	
	/**
	 * Test monthly ecommerce totals.
	 *
	 * @throws MailerLiteException the mailer lite exception
	 */
	@Test
	public void TestMonthlyEcommerceTotals() throws MailerLiteException
	{
		http.respondWith(200, "{\"data\":{\"ecommerce\":{\"orders_count\":3,\"revenue\":12.5,\"average_order_value\":0},\"best_performing_email\":null}}");
		
		assertEquals(3, this.getMailerLite().automations().reports("100").startDate("2024-01-01").endDate("2024-01-31").shopId("8").monthlyEcommerceTotals().stats.ecommerce.ordersCount);
		
		Map<String, String> query = http.query();
		
		assertEquals("GET", http.method());
		assertEquals("/automations/100/monthly-totals/ecommerce", http.path());
		assertEquals("2024-01-01", query.get("start_date"));
		assertEquals("2024-01-31", query.get("end_date"));
		assertEquals("8", query.get("shop_id"));
	}
}
