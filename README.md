# TLProfilesCreator
The Telegram-ProfilesCreator program is used for creating 'News broadcaster profiles' from Social Networks users (Telegram).

# Abstract

The dissemination of news on social networks is a widely spread phenomenon and plays a fundamental role in informing society. The ease of access to social networks, together with the great speed of content propagation in them, makes them an interesting element for study and research, especially when they play a role as a 'catalyst' or amplification of news.

Frequently, social media users endure varying degrees of infoxication, or information overload, that exceed the individual's processing capacity. This loss of control over information leads to accepting as good almost any news that is received through social networks without carrying out a minimum critical reflection on them, becoming mere automatons programmed to continue turning, increasingly faster, the wheel of the propagation of content.

In this context, information flow analysis tools contribute to an efficient analysis of news dissem-ination and the identification of users which share them on social networks. From the academic and scientific world, numerous works have addressed, and continue to do so, this area of research; Meanwhile, the different government institutions, both national and supranational, are addressing this phenomenon at a regulatory and legislative level. 

The ambition of this thesis is the development of a software tool that allows creating sets of data, or datasets, of users’ profiles who share news on the Telegram social network. Specifically, the application allows users to retrieve news from the Internet, search for profiles of users who are sharing them and generate datasets by collecting information about these users. The standard JSON structured data format is used to create files for both the datasets and the rest of the data managed by the application: Internet news and Telegram messages.

The results obtained during the functional tests of the application meet the main objective of this work: to generate datasets of profiles from users who share news, and with the specific objec-tives: to retrieve news from the Internet search engines or from RSS services, to search for mes-sages in Telegram chats whose contents are news URL and produce dataset using JSON’ standard.  On the other hand, it is important to highlight that Telegram's limitations about retrieving information impose an external restriction on the system that limits message recovery capabilities.

# Objectives

The general objective of this work is the design and development of a software tool capable of: retrieving news from the Internet, identifying the retrieved news from its URL or headline, then searching for those news items among the messages in the available Telegram chats, and obtaining public data from the users who shared them. With the information retrieved, datasets will be created using a standard structured data format.

# System Description

The developed desktop application allows retrieving news from the Internet in different ways, for example, from RSS feeds or by searching directly for the news title or URL in the GOOGLE search engine.
The Internet search retrieves the news headline, the URL, and, in the case of RSS feeds, the news synopsis. This information is displayed in the application in a tabular format, and the application records the following attributes: date when the news was retrieved, title, URL, synopsis, and source (Internet, RSS).

The application allows searching for messages in Telegram chats, of which the user is a member, using the URL or headline of the retrieved news. Message searches in Telegram are displayed in the application in a tabular format, and the application records the following attributes: message date and time, senderId, messageId, chatId, urlQuery, message content, and message type (text, photo, video). In addition, it is possible to view the chats of which the user is a member, search for public chats, and join them.

The different tabular views for displaying information are initially sorted in reverse chronological order, and the system also allows filtering data and sorting it by each table attribute.

The application offers two operating modes: an API for batch task execution, and a graphical user interface. Data persistence for the different data types—news, messages, and datasets—is handled using structured JSON text files.

Additionally, the system allows:

Viewing the retrieved news in the system’s default web browser.
Viewing Telegram messages, if the Telegram Desktop client is installed on the host system.
Viewing statistical information.

With the information retrieved when performing message searches on Telegram, two types of datasets can be created:

User-type dataset: composed of the user’s data plus a list of all messages associated with that user. Figure 2 shows a ‘compact’ view of a user-type dataset.
Message-type dataset: composed of a message, the URL or headline of the news, and a list of data of all users who have shared it. Figure 3 shows a ‘compact’ view of a message-type dataset.

# TDLib: https://github.com/tdlib/td
It is a Telegram client available to developers with the purpose of assisting in the creation of custom applications that use the Telegram platform. The client is available on GitHub and is responsible for managing network connections, encryption, and local data storage.

# Installation Manual

Description
The file TL_ProfilesCreator3.1.zip contains everything needed to use the application on Windows platforms.

Below is an explanation of the contents:

datafiles: default folder for exporting NEWS- and MESSAGES-type files.
dataset: default folder for exporting dataset-type files.
image: initial screen image.
support: libraries, documentation, and support software.
dll: libraries required for the application to function.
documentation: user manual.
jdk: installation package jdk-20_windows-x64.
Telegram Desktop: installation package tsetup-x64.5.0.1.
tools: Dependencies v1.11.1.0. Software for verifying dependencies.
Chromedriver: chromedriver.exe file.
TLProfilesCreator_3.1.jar: application.
startGUI.cmd: command file to run the application in graphical mode.

Requirements
The following elements are required for the application to function correctly:

Java JDK 20 or higher.
tdjni.dll and dependencies.

New dependencies (July 2025)
Due to changes introduced by Google in its search engine, the previous JSoup-based system has been complemented with a dynamic one using Selenium. For proper operation, it is necessary to have:

ChromeDriver (compatible with the version of Google Chrome installed on the system).
Google Chrome (updated browser recommended).
Selenium WebDriver (included in the project libraries).

The following elements are optional:

Telegram Desktop.

Running the application
To start the application, run the following command:

java -jar TLProfilesCreator.jar [<options>] or execute startGUI.cmd.
