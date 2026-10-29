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
    - [Emails](#emails)
        - [Get an email](#get-an-email)
        - [Get the HTML of an email](#get-the-html-of-an-email)
        - [Get a block](#get-a-block)
        - [Insert a block](#insert-a-block)
        - [Update a block](#update-a-block)
        - [Delete a block](#delete-a-block)
        - [Move a block](#move-a-block)
        - [Validate a block](#validate-a-block)
        - [Update the design settings](#update-the-design-settings)
        - [Validate the design settings](#validate-the-design-settings)
        - [Save an email as a template](#save-an-email-as-a-template)
        - [List the versions of an email](#list-the-versions-of-an-email)
        - [Save a version of an email](#save-a-version-of-an-email)
        - [Restore a version of an email](#restore-a-version-of-an-email)
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
      <artifactId>mailerlite-java</artifactId>
      <version>0.0.4</version>
    </dependency>

Using Gradle:

    implementation 'com.mailerlite:mailerlite-java:0.0.4'


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

## Emails

These methods read and change emails built with the simple editor. The ID of the email of a campaign is in `campaign.emails[0].id`.

- IDs are strings of the digits 0 to 9. An empty or `null` ID throws `EmptyIdException`. An ID with other characters throws `InvalidIdException`. Both extend `IllegalArgumentException`, and the SDK sends no request.
- Each write needs the etag from the last response for the email, and each write returns a new etag. The etag covers the whole email, so send the writes to one email one after another. An empty or `null` etag throws `EmptyEtagException`, and the SDK sends no request.
- If the email changed after you read it, the write throws a `MailerLiteException` with `code` `409`, and `e.isVersionConflict()` is `true`. Then `e.etag` is the current etag. Get the email again, and then send the write again with the current etag.
- The API error code, for example `version_conflict`, is in `e.errorCode`. The field `e.code` is the HTTP status.
- The keys of `editorContent` are camelCase, for example `textAlign`. The SDK sends them as you give them. In a response, whole numbers are `Long` values.
- Each response has the status code in `responseStatusCode` and the headers in `headers`.

### Get an email

Returns the email with its design settings and its blocks, and the etag of the email.

```java

// import ....

class Example {

    public void example() {
		try {
			
			SingleEmailDocument response = this.getMailerLite().emails().get("123456");
			
			EmailDocument email = response.email;
			String etag = response.etag;
			
			for (EmailBlock block : email.blocks) {
				
				System.out.println(block.id + " " + block.type);
			}
			
			System.out.println(etag);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

### Get the HTML of an email

Returns the HTML of the email as a preview. The HTML is not ready to send: personalization shows as tokens such as `{$name}`, and the links have markers instead of tracking URLs. The API can return a temporary `503` error. If it does, try again later.

```java

// import ....

class Example {

    public void example() {
		try {
			
			SingleEmailHtml response = this.getMailerLite().emails().getHtml("123456");
			
			System.out.println(response.preview.html);
			
		} catch (MailerLiteException e) {
			
			if (e.code == 503) {
				
				System.out.println("Try again later");
			}
			
			e.printStackTrace();
		}
    }
}
```

### Get a block

Returns the block and its position. `placement.after` is the ID of the block before it, and `placement.before` is the ID of the block after it. Each one is `null` if there is no such block.

```java

// import ....

class Example {

    public void example() {
		try {
			
			SingleEmailBlock response = this.getMailerLite().emails().getBlock("123456", "57");
			
			EmailBlock block = response.block;
			
			System.out.println(block.type);
			System.out.println(block.editorContent);
			System.out.println(block.placement.after);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

### Insert a block

Use the block types in `EmailBlockTypes`. You cannot insert the `TITLE` type. `editorContent` is required, but it can be an empty map. The SDK sends the map as it is, also its `null` values.

Without `placement`, the API puts the block at the end of the email. The positions are in `EmailBlockPositions`. The `BEFORE` and `AFTER` positions also need a `blockId`. For `START` and `END`, the API does not use a `blockId`, so the SDK does not send it.

A `null` block or `editorContent` throws `NullPointerException`. An empty block type throws `IllegalArgumentException`.

```java

// import ....

class Example {

    public void example() {
		try {
			
			Emails emails = this.getMailerLite().emails();
			
			String emailId = "123456";
			String etag = emails.get(emailId).etag;
			
			Map<String, Object> content = Map.of(
					"attrs", Map.of("textAlign", "left"),
					"content", List.of(Map.of("type", "text", "text", "Hello")));
			
			EmailBlockInput block = new EmailBlockInput(EmailBlockTypes.PARAGRAPH, content);
			block.placement = new EmailBlockPosition(EmailBlockPositions.START);
			
			SingleEmailBlock response = emails.createBlock(emailId, etag, block);
			
			String blockId = response.block.id;
			etag = response.etag;
			
			System.out.println(blockId + " " + etag);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

### Update a block

The new `editorContent` replaces the whole content of the block. The API removes the keys that you do not send. So get the block, change its `editorContent`, and send all of it. The type and the position of the block do not change.

```java

// import ....

class Example {

    public void example() {
		try {
			
			Emails emails = this.getMailerLite().emails();
			
			SingleEmailBlock read = emails.getBlock("123456", "57");
			
			Map<String, Object> content = new HashMap<String, Object>(read.block.editorContent);
			content.put("attrs", Map.of("textAlign", "center"));
			
			SingleEmailBlock response = emails.updateBlock("123456", "57", read.etag, content);
			
			System.out.println(response.etag);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

### Delete a block

Deletes the block and the blocks in it. The response has only the new etag.

```java

// import ....

class Example {

    public void example() {
		try {
			
			EmailEtagResponse response = this.getMailerLite().emails().deleteBlock("123456", "57", "etag-from-the-last-response");
			
			System.out.println(response.etag);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

### Move a block

Moves the block to a new position at the same level. The `BEFORE` and `AFTER` positions also need a `blockId`. The response has the block with its new `placement`.

```java

// import ....

class Example {

    public void example() {
		try {
			
			EmailBlockPosition placement = new EmailBlockPosition(EmailBlockPositions.AFTER, "12");
			
			SingleEmailBlock response = this.getMailerLite().emails().moveBlock("123456", "57", "etag-from-the-last-response", placement);
			
			System.out.println(response.etag);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

### Validate a block

Checks a block before you insert it, and saves nothing. It needs no email and no etag. An invalid block is not an error: the response has `valid` set to `false` and the `errors` for each field.

```java

// import ....

class Example {

    public void example() {
		try {
			
			EmailBlockInput block = new EmailBlockInput("table", Map.of());
			
			EmailValidation response = this.getMailerLite().emails().validateBlock(block);
			
			if (!response.valid) {
				
				for (Map.Entry<String, String[]> error : response.errors.entrySet()) {
					
					System.out.println(error.getKey() + ": " + String.join(" ", error.getValue()));
				}
			}
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

### Update the design settings

Set only the fields that you want to change. The groups are `brand`, `layout`, `typography`, `button` and `footer`.

- A `null` field is not sent and does not change. An empty string unsets the value, and the email then uses the value of your account.
- All style values are strings, for example `"16"` and not `16`.
- The API stores each value that you send, also a value that the email inherits from your account. Then the email no longer follows your account for that value. So do not send back all the settings that you read.
- `footer.socialLinks` replaces the whole list. An empty list removes all the links. Keep the `linkId` of each link to keep its click history.
- `brand.mode` is one of `EmailBrandModes`. To apply a preset palette, set `brand.style` to the `identifier` of the preset from the brand styles.
- The SDK sends the etag at the top level of the body. Do not put it in the settings.
- If the API removed unsafe values, the response has `rejected` with the paths of these values.

```java

// import ....

class Example {

    public void example() {
		try {
			
			EmailSettings settings = new EmailSettings();
			settings.brand = new EmailBrandSettings();
			settings.brand.mode = EmailBrandModes.CUSTOM;
			settings.brand.backgroundColor = "#f4f4f4";
			settings.typography = new EmailTypographySettings();
			settings.typography.paragraph = new EmailParagraphStyle();
			settings.typography.paragraph.p1 = new EmailTextStyle();
			settings.typography.paragraph.p1.size = "18";
			
			SingleEmailSettings response = this.getMailerLite().emails().updateSettings("123456", "etag-from-the-last-response", settings);
			
			System.out.println(response.etag);
			
			if (response.rejected != null) {
				
				System.out.println(String.join(", ", response.rejected.keys));
			}
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

### Validate the design settings

Checks a change with the rules of the update, and saves nothing. It needs no etag. Invalid settings are not an error: the response has `valid` set to `false` and the `errors` for each field. If the update would remove unsafe values, `rejected` has their paths.

```java

// import ....

class Example {

    public void example() {
		try {
			
			EmailSettings settings = new EmailSettings();
			settings.button = new EmailButtonSettings();
			settings.button.borderRadius = "8";
			
			EmailValidation response = this.getMailerLite().emails().validateSettings("123456", settings);
			
			if (!response.valid) {
				
				System.out.println(response.errors.keySet());
			}
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

### Save an email as a template

Copies the email to a new template. The API cannot delete templates.

```java

// import ....

class Example {

    public void example() {
		try {
			
			SingleEmailTemplate response = this.getMailerLite().emails().createTemplate("123456", "Monthly newsletter");
			
			System.out.println(response.template.id);
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

### List the versions of an email

A version is a saved copy of the content and the design settings of an email. The list starts with the newest version. `current` is `true` for the version that the email has now. Writes through the API do not save versions.

```java

// import ....

class Example {

    public void example() {
		try {
			
			EmailVersionsList response = this.getMailerLite().emails().versions("123456").page(1).limit(10).get();
			
			for (EmailVersion version : response.versions) {
				
				System.out.println(version.id + " " + version.createdAt + " " + version.current);
			}
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

### Save a version of an email

Writes through the API do not save versions. If nothing changed after the last version, the API saves no new version and returns the last version. `isCreated()` is `true` for a new version (status `201`), and `false` for the last version (status `200`).

```java

// import ....

class Example {

    public void example() {
		try {
			
			SingleEmailVersion response = this.getMailerLite().emails().createVersion("123456");
			
			System.out.println(response.version.id + " " + response.isCreated());
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }
}
```

### Restore a version of an email

Puts the email back to the version, and returns the full email with the new etag. Before the restore, the API saves the current state as a version, so you can undo the restore.

```java

// import ....

class Example {

    public void example() {
		try {
			
			SingleEmailDocument response = this.getMailerLite().emails().restoreVersion("123456", "901", "etag-from-the-last-response");
			
			System.out.println(response.etag);
			
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

Change the properties in the `TestHelper` class of the `com.mailerlite.tests` package to correspond to your account details, then simply run
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
