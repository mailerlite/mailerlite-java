<a href="https://www.mailerlite.com"><img src="https://app.mailerlite.com/assets/images/logo-color.png" width="200px"/></a>

MailerLite Java SDK

[![MIT licensed](https://img.shields.io/badge/license-MIT-blue.svg)](./LICENSE)

# Table of Contents

- [Installation](#installation)
- [Usage](#usage)
    - [Campaigns](#campaigns)
        - [Get a list of campaigns](#get-a-list-of-campaigns)
        - [Get a campaign](#get-a-campaign)
        - [Create a campaign](#create-a-campaign)
        - [Update a campaign](#update-a-campaign)
        - [Schedule a campaign](#schedule-a-campaign)
        - [Cancel a ready campaign](#cancel-a-ready-campaign)
        - [Delete a campaign](#delete-a-campaign)
        - [Get subscribers activity for a campaign](#get-subscribers-activity-for-an-campaign)


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

## Campaigns

### Get a list of campaigns

```java

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.exceptions.MailerLiteException;

Class Example
{
    public void exampleMethod()
    {
        MailerLite ml = new MailerLite();
        ml.setToken("Your API token");
		
		try {
			
			CampaignsList campaigns = ml.campaigns().retriever().filter("status", "sent").get();
			
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

import com.mailerlite.sdk.MailerLite;
import com.mailerlite.sdk.exceptions.MailerLiteException;

Class Example
{
    public void exampleMethod()
    {
        MailerLite ml = new MailerLite();
        ml.setToken("Your API token");

		try {
			
			SingleCampaign campaign = ml.campaigns().retriever().getSingle("campaign id");
			
			System.out.println(campaign.id);
			System.out.println(campaign.createdAt.toString());
			
		} catch (MailerLiteException e) {
			
			e.printStackTrace();
		}
    }

}

```