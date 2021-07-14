---
header-id: managing-liferay-with-liferay-connected-services
---

# Managing Liferay with Liferay Connected Services

[TOC levels=1-4]

![EE Only Feature](../../images/ee-feature-web.png)

| **Note:** LCS is deprecated and will be shut down on December 31, 2021. 
| Customers who activate LCS are advised to replace it with our latest activation
| key type which is suitable for virtualized environments. 
|
| For further information, please see [Changes to Liferay Product Activation](https://help.liferay.com/hc/en-us/articles/4402347960845-Changes-to-Liferay-Product-Activation).

Liferay Connected Services (LCS) is a set of online tools and services that lets
you manage and monitor your Liferay Portal instances. LCS can help you install
fix packs, monitor your instances' performance, activate your instances, and
help you manage your Liferay Portal subscriptions. In other words, LCS is like a
butler for the mansion that is Liferay Portal. Even better, the features of LCS
work regardless of whether your instance is on a single discreet server or
distributed across a cluster. It's like having a single butler that can serve
several mansions at once! You can find more information about LCS on its
[official product page](http://www.liferay.com/products/liferay-connected-services).

Before going any further, you should make sure that your Liferay Portal
instances meet the requirements for LCS--you must be running Liferay Portal 6.2
EE.

Also, you should take note of a few key terms used throughout this guide:

- **Project:** Represents a group of users belonging to a company or
  organization. For example, a project can consist of all the users from a
  project team or business unit, or it can include the entire company.
- **Environment:** Represents a physical cluster of servers or a virtual or
  logical aggregation of servers.
- **Server:** Describes a concrete portal instance. It can be a standalone
  server or a cluster node.

As you go through this guide, you'll cover the following sections on LCS:

- LCS Preconfiguration
- Connecting to LCS
- Using LCS

You'll get started with the configuration steps required to use LCS with Liferay
Portal.
