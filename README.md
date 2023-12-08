<a href="https://www.mailerlite.com"><img src="https://app.mailerlite.com/assets/images/logo-color.png" width="200px"/></a>

MailerLite Java SDK

[![MIT licensed](https://img.shields.io/badge/license-MIT-blue.svg)](./LICENSE)

# Table of Contents

- [Installation](#installation)
- [Usage](#usage)
    - [Subscribers](#subscribers)
        - [Get a list of subscribers](#get-a-list-of-subscribers)
        - [Get a single subscriber](#get-a-single-subscriber)
        - [Count all subscribers](#count-all-subscribers)
        - [Create a subscriber](#create-a-subscriber)
        - [Update a subscriber](#update-a-subscriber)
        - [Delete a subscriber](#delete-a-subscriber)
    - [Groups](#groups)
        - [Get a list of groups](#get-a-list-of-groups)
        - [Create a group](#create-a-group)
        - [Update a group](#update-a-group)
        - [Delete a group](#delete-a-group)
        - [Get subscribers belonging to a group](#get-subscribers-belonging-to-a-group)
        - [Assign subscriber to a group](#assign-subscribers-to-a-group)
    - [Segments](#segments)
        - [Get a list of segments](#get-a-list-of-segments)
        - [Update a segment](#update-a-segment)
        - [Delete a segment](#delete-a-segment)
        - [Get subscribers belonging to a segment](#get-subscribers-belonging-to-a-segment)
    - [Fields](#fields)
        - [Get a list of fields](#get-a-list-of-fields)
        - [Create a field](#create-a-field)
        - [Update a field](#update-a-field)
        - [Delete a field](#delete-a-field)
    - [Automations](#automations)
        - [Get a list of automations](#get-a-list-of-automations)
        - [Get an automation](#get-an-automation)
        - [Get subscribers activity for an automation](#get-subscribers-activity-for-an-automation)
    - [Campaigns](#campaigns)
        - [Get a list of campaigns](#get-a-list-of-campaigns)
        - [Get a campaign](#get-a-campaign)
        - [Create a campaign](#create-a-campaign)
        - [Update a campaign](#update-a-campaign)
        - [Schedule a campaign](#schedule-a-campaign)
        - [Cancel a ready campaign](#cancel-a-ready-campaign)
        - [Delete a campaign](#delete-a-campaign)
        - [Get subscribers activity for a campaign](#get-subscribers-activity-for-an-campaign)
    - [Forms](#forms)
        - [Get a list of forms](#get-a-list-of-forms)
        - [Get a form](#get-a-form)
        - [Update a form](#update-a-form)
        - [Delete a form](#delete-a-form)
        - [Get subscribers of a form](#get-subscribers-of-a-form)
    - [Batching](#batching)
        - [Create a new batch](#create-a-new-batch)
    - [Webhooks](#webhooks)
        - [Get a list of webhooks](#get-a-list-of-webhooks)
        - [Get a webhook](#get-a-webhook)
        - [Create a webhook](#update-a-webhook)
        - [Update a webhook](#update-a-webhook)
        - [Delete a webhook](#delete-a-webhook)

# Installation

Using Maven:

    <dependency>
      <groupId>com.mailerlite</groupId>
      <artifactId>sdk</artifactId>
      <version>1.0.0</version>
    </dependency>

Using Gradle:

    implementation 'com.mailerlite.sdk:1.0.0'


# Usage

Start using the sdk by creating an instance of the `MailerLite` object and passing your API token into it like this:

```java
	public MailerLite getMailerLite()
	{
		MailerLite ml = new MailerLite();
		ml.setToken("Your API token");
		
		return ml;
	}
```

## Subscribers

### Get a list of subscribers

```java

// import ....

class Example {

    public void example() {
        try {
			
			SubscribersList subscribers = this.getMailerLite().subscribers().retriever().filter("status", "active").get();
			
			for (Subscriber s : subscribers.subscribers) {
				
				System.out.println(s.id);
				System.out.println(s.email);
				System.out.println(s.createdAt.toString());
			}
			
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

### Get a single subscriber

```java

// import ....

class Example {

    public void example() {
		try {
						
			Subscriber subscriber = this.getMailerLite().subscribers().retriever().getSingle("subscriber id").subscriber;
			
			System.out.println(subscriber.email);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

### Count all subscribers

```java

// import ....

class Example {

    public void example() {
		try {
			
			SubscriberCount count = this.getMailerLite().subscribers().retriever().filter("status", "active").count();
			
			System.out.println(count.count);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

### Create a subscriber

```java

// import ....

class Example {

    public void example() {
		try {
			
			Subscriber subscriber = this.getMailerLite().subscribers().builder().email("test+email@mailerlite.com")
					.addField("name", "test name")
					.create()
					.subscriber;
			
			System.out.println(subscriber.email);
			System.out.println(subscriber.fields.get("name"));
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

### Update a subscriber

```java

// import ....

class Example {

    public void example() {
		try {
			
			SubscribersList subscribers = this.getMailerLite().subscribers().retriever().filter("status", "active").get();
			
			Subscriber subscriber = this.getMailerLite().subscribers().builder()
					.addField("name", "test name2")
					.update(subscribers.subscribers[0].id)
					.subscriber;
			
			System.out.println(subscriber.email);
			System.out.println(subscriber.fields.get("name"));
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

## Groups

### Get a list of groups

```java

// import ....

class Example {

    public void example() {
		try {
			
			GroupsList groups = this.getMailerLite().groups().retriever().get();
			
			for (Group g : groups.groups) {
				
				System.out.println(g.id);
				System.out.println(g.name);
			}
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

### Create a group

```java

// import ....

class Example {

    public void example() {
		try {
			
			SingleGroup group = this.getMailerLite().groups().builder().create("new group name");
			
			System.out.println(group.group.id);
			System.out.println(group.group.name);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

### Update a group

```java

// import ....

class Example {

    public void example() {
		try {
			
			GroupsList groups = this.getMailerLite().groups().retriever().get();
			
			SingleGroup group = this.getMailerLite().groups().builder().update(groups.groups[0].id, "updated group name");
			
			System.out.println(group.group.id);
			System.out.println(group.group.name);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

### Delete a group

```java

// import ....

class Example {

    public void example() {
		try {
			
			GroupsList groups = this.getMailerLite().groups().retriever().get();
			
			MailerLiteResponse deleteResponse = this.getMailerLite().groups().delete(groups.groups[0].id);
			
			System.out.println(deleteResponse.responseStatusCode);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

### Get subscribers belonging to a group

```java

// import ....

class Example {

    public void example() {
		try {
			
			GroupsList groups = this.getMailerLite().groups().retriever().get();
			
			GroupSubscribersList subscribers = this.getMailerLite().groups().subscribers(groups.groups[0].id).get();
			
			for (Subscriber subscriber : subscribers.subscribers) {
				System.out.println(subscriber.email);
			}
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

### Assign subscriber to a group

```java

// import ....

class Example {

    public void example() {
		try {
			
			GroupsList groups = this.getMailerLite().groups().retriever().get();
			SubscribersList subscribers = this.getMailerLite().subscribers().retriever().filter("status", "active").get();
			
			SingleGroup group = this.getMailerLite().groups().subscribers(groups.groups[0].id).assign(subscribers.subscribers[0].id);
			
			System.out.println(group.group.name);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

### Unassign subscriber from a group

```java

// import ....

class Example {

    public void example() {
		try {
			
			GroupsList groups = this.getMailerLite().groups().retriever().get();
			GroupSubscribersList subscribers = this.getMailerLite().groups().subscribers(groups.groups[0].id).get();
			
			MailerLiteResponse response = this.getMailerLite().groups().subscribers(groups.groups[0].id).unassign(subscribers.subscribers[0].id);
			
			System.out.println(response.responseStatusCode);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

## Segments

### Get a list of segments

```java

// import ....

class Example {

    public void example() {
		try {
			
			SegmentsList segments = this.getMailerLite().segments().retriever().get();
			
			for (Segment s : segments.segments) {
				
				System.out.println(s.id);
				System.out.println(s.name);
			}
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

### Update a segment

```java

// import ....

class Example {

    public void example() {
		try {
			
			SegmentsList segments = this.getMailerLite().segments().retriever().get();
			
			SingleSegment segment = this.getMailerLite().segments().update(segments.segments[0].id, "New segment name");
			
			System.out.println(segment.segment.name);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

### Delete a segment

```java

// import ....

class Example {

    public void example() {
		try {
			
			SegmentsList segments = this.getMailerLite().segments().retriever().get();
			
			MailerLiteResponse response = this.getMailerLite().segments().delete(segments.segments[0].id);
			
			System.out.println(response.responseStatusCode);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

### Get subscribers belonging to a segment

```java

// import ....

class Example {

    public void example() {
		try {
			
			SegmentsList segments = this.getMailerLite().segments().retriever().get();
			
			SegmentSubscribersList list = this.getMailerLite().segments().subscribers(segments.segments[0].id).get();
						
			for (Subscriber s : list.subscribers) {
				
				System.out.println(s.email);
			}
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

## Fields

### Get a list of fields

```java

// import ....

class Example {

    public void example() {
		try {
			
			FieldList fields = this.getMailerLite().fields().retriever().get();
			
			for (Field f : fields.fields) {
				
				System.out.println(f.id);
				System.out.println(f.name);
			}
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

### Create a field

```java

// import ....

class Example {

    public void example() {
		try {
			
			SingleField field = this.getMailerLite().fields().builder().create("test field", "text");
			
			System.out.println(field.field.id);
			System.out.println(field.field.name);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

### Update a field

```java

// import ....

class Example {

    public void example() {
		try {
						
			SingleField field = this.getMailerLite().fields().builder().update("field id", "new field name");
			
			System.out.println(field.field.id);
			System.out.println(field.field.name);
			
		} catch (MailerLiteException e) {
			e.printStackTrace();
		}
    }
}
```

### Delete a field

```java

// import ....

class Example {

    public void example() {
		try {
			
			MailerLiteResponse response = this.getMailerLite().fields().delete("field id");
			
			System.out.println(response.responseStatusCode);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

## Automations

### Get a list of automations

```java

// import ....

class Example {

    public void example() {
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
		}
    }
}
```

### Get an automation

```java

// import ....

class Example {

    public void example() {
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
		}
    }
}
```

### Get subscribers activity for an automation

```java

// import ....

class Example {

    public void example() {
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
		}
    }
}
```

## Campaigns

### Get a list of campaigns

```java

// import ....

class Example {

    public void example() {
		try {
			
			CampaignsList campaigns = this.getMailerLite().campaigns().retriever().filter("status", "sent").get();
			
			for (Campaign c : campaigns.campaigns) {
				
				System.out.println(c.id);
				System.out.println(c.createdAt.toString());
			}
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

### Get a campaign

```java

// import ....

class Example {

    public void example() {
		try {
			
			CampaignsList campaigns = this.getMailerLite().campaigns().retriever().filter("status", "sent").get();
			
			Campaign campaign = this.getMailerLite().campaigns().retriever().getSingle(campaigns.campaigns[0].id).campaign;
			
			System.out.println(campaign.id);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

### Create a campaign

```java

// import ....

class Example {

    public void example() {
		EmailBase email = new EmailBase();
		email.content = "<div>Test email content</div>";
		email.from = "from email";
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
		}
    }
}
```

### Update a campaign

```java

// import ....

class Example {

    public void example() {
		try {
			
			EmailBase email = new EmailBase();
			email.content = "<div>Test email content</div>";
			email.from = "from email";
			email.fromName = "test from name";
			email.subject = "test email subject";

			SingleCampaign newCampaign = this.getMailerLite()
                .campaigns()
                .builder()
                .name(name)
                .email(email)
                .type("regular")
                .groupId("group id")
                .create();
			
			SingleCampaign updatedCampaign = this.getMailerLite().campaigns().builder()
					.name("Updated campaign name")
					.email(email)
					.type("regular")
					.update(newCampaign.campaign.id);
			
            System.out.println(updatedCampaign.name);

		} catch (MailerLiteException e) {
			e.printStackTrace();
		}
    }
}
```

### Schedule a campaign

```java

// import ....

class Example {

    public void example() {
		try {
			
			SingleCampaign newCampaign =  = this.getMailerLite()
                .campaigns()
                .builder()
                .name(name)
                .email(email)
                .type("regular")
                .groupId("group id")
                .create();
			
			CampaignScheduler scheduler = this.getMailerLite().campaigns().scheduler();
			
			scheduler.scheduleSettings()
			.date("2023-03-01")
			.hours("09:00")
			.minutes("00:00");
			
			SingleCampaign campaign = scheduler
			.delivery(CampaignDelivery.SCHEDULED)
			.schedule(newCampaign.campaign.id);
			
		} catch (MailerLiteException e) {
			e.printStackTrace();
		}
    }
}
```

### Cancel a ready campaign

```java

// import ....

class Example {

    public void example() {
		try {
			
			SingleCampaign newCampaign = this.getMailerLite()
                .campaigns()
                .builder()
                .name("campaign to cancel")
                .email(email)
                .type("regular")
                .groupId("group id")
                .create();
			
			CampaignScheduler scheduler = this.getMailerLite().campaigns().scheduler();
			
			scheduler.scheduleSettings()
			.date("2023-03-01")
			.hours("09")
			.minutes("00");
			
			SingleCampaign campaign = scheduler
			.delivery(CampaignDelivery.SCHEDULED)
			.schedule(newCampaign.campaign.id);
			
			SingleCampaign canceledCampaign = this.getMailerLite().campaigns().scheduler().cancel(campaign.campaign.id);
			
		} catch (MailerLiteException e) {
			e.printStackTrace();
		}
    }
}
```

### Delete a campaign

```java

// import ....

class Example {

    public void example() {
		try {
			
			SingleCampaign newCampaign = this.createCampaign("Campaign to delete");
			
		
			MailerLiteResponse response = this.getMailerLite().campaigns().delete(newCampaign.campaign.id);
			
		} catch (MailerLiteException e) {
			e.printStackTrace();
		}
    }
}
```

### Get subscribers activity for a campaign

```java

// import ....

class Example {

    public void example() {
		try {
			
			CampaignsList campaigns = this.getMailerLite().campaigns().retriever().filter("status", "sent").get();
			
			CampaignSubscriberActivityList activity = this.getMailerLite().campaigns().subscriberActivity().get(campaigns.campaigns[0].id);
			
		} catch (MailerLiteException e) {
			e.printStackTrace();
		}
    }
}
```

## Forms

### Get a list of forms

```java

// import ....

class Example {

    public void example() {
		try {
			
			FormsList forms = this.getMailerLite().forms().retriever().get("embedded");
			
			for (Form f : forms.forms) {
				
				System.out.println(f.id);
				System.out.println(f.name);
			}
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

### Get a form

```java

// import ....

class Example {

    public void example() {
		try {
			
			FormsList forms = this.getMailerLite().forms().retriever().get("embedded");
			
			SingleForm f = this.getMailerLite().forms().retriever().getSingle(forms.forms[0].id);
			
			System.out.println(f.form.id);
			System.out.println(f.form.name);
			
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

### Update a form

```java

// import ....

class Example {

    public void example() {
		try {
					
			FormsList forms = this.getMailerLite().forms().retriever().get("embedded");
			
			SingleForm form = this.getMailerLite().forms().update(forms.forms[0].id, "new form name");
			
			System.out.println(form.form.id);
			System.out.println(form.form.name);
			
		} catch (MailerLiteException e) {
			e.printStackTrace();
		}
    }
}
```

### Delete a form

```java

// import ....

class Example {

    public void example() {
		try {
			
			FormsList forms = this.getMailerLite().forms().retriever().get("embedded");
			
			MailerLiteResponse response = this.getMailerLite().forms().delete(forms.forms[0].id);
			
			System.out.println(response.responseStatusCode);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

### Get subscribers of a form

```java

// import ....

class Example {

    public void example() {
		try {
			
			FormsList forms = this.getMailerLite().forms().retriever().get("embedded");
			
			SubscribersList list = this.getMailerLite().forms().subscribers().get(forms.forms[0].id);
			
			for (Subscriber sub : list.subscribers) {
				System.out.println(sub.id);
				System.out.println(sub.email);
			}
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

## Webhooks

### Get a list of webhooks

```java

// import ....

class Example {

    public void example() {
		try {
			
			WebhooksList webhooks = this.getMailerLite().webhooks().retriever().get();
			
			for (Webhook w : webhooks.webhooks) {
				
				System.out.println(w.id);
				System.out.println(w.name);
				System.out.println(w.url);
			}
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

### Get a webhook

```java

// import ....

class Example {

    public void example() {
		try {
			
			WebhooksList webhooks = this.getMailerLite().webhooks().retriever().get();
			
			SingleWebhook webhook = this.getMailerLite().webhooks().retriever().getSingle(webhooks.webhooks[0].id);
		
			System.out.println(webhook.webhook.id);
			System.out.println(webhook.webhook.name);
			System.out.println(webhook.webhook.url);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

### Create a webhook

```java

// import ....

class Example {

    public void example() {
		try {
			
			SingleWebhook webhook = this.getMailerLite()
					.webhooks()
					.builder()
					.addEvent("subscriber.added_to_group")
					.name("Add to group")
					.url("https://example.com")
					.enabled(true)
					.create();
			
			System.out.println(webhook.webhook.id);
			System.out.println(webhook.webhook.name);
			System.out.println(webhook.webhook.url);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

### Update a webhook

```java

// import ....

class Example {

    public void example() {
		try {
			
			WebhooksList webhooks = this.getMailerLite().webhooks().retriever().get();
			
			SingleWebhook webhook = this.getMailerLite().webhooks().builder().enabled(false).update(webhooks.webhooks[0].id);
			
			System.out.println(webhook.webhook.id);
			System.out.println(webhook.webhook.name);
			System.out.println(webhook.webhook.enabled);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

### Delete a webhook

```java

// import ....

class Example {

    public void example() {
		try {
			
			WebhooksList webhooks = this.getMailerLite().webhooks().retriever().get();
			
			MailerLiteResponse deleteResponse = this.getMailerLite().webhooks().delete(webhooks.webhooks[0].id);
			
			System.out.println(deleteResponse.responseStatusCode);

		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

# Testing

Change the properties in the `TestHelper` class of the `com.mailerlite.sdk.tests` package to correspond to your account details, then simply run
```
mvn test
```

<a name="support-and-feedback"></a>

# Support and Feedback

In case you find any bugs, submit an issue directly here in GitHub.

You are welcome to create SDK for any other programming language.

If you have any trouble using our API or SDK feel free to contact our support by
email [info@mailerlite.com](mailto:info@mailerlite.com)

The official API documentation is at [https://developers.mailerlite.com](https://developers.mailerlite.com)

<a name="license"></a>

# License

[The MIT License (MIT)](LICENSE)
