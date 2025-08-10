Adaptation of the Search System Due to Google Changes (July 2025)

During the system’s development phase, the JSoup library was initially used to perform Google News searches, retrieve the HTML document, and parse it. However, in mid-January 2025, Google introduced significant changes to its HTML structure and its protection mechanisms against automated access. These changes include generating part of the content with JavaScript, and JSoup does not execute JavaScript. As a result, the selectors used (.yuRUbf a) stopped working correctly.

These changes made it impossible to maintain the previous behavior, as searches returned empty documents or were directly blocked by the protection mechanisms. Faced with this limitation, in July 2025 it was decided to replace the JSoup-based search mechanism with a dynamic solution based on Selenium.

Selenium allows simulating real web browser interaction, largely bypassing the restrictions imposed by Google. The new implementation opens a browser session in silent, non-visible mode (headless mode), navigates to the results page, and dynamically extracts the titles and URLs of the news items.

Additionally, the DuckDuckGo search engine was integrated, since Google continued to identify the system’s requests as illegitimate and block them.

This solution is currently used as the main source for retrieving news, alongside the original RSS-based techniques, which have not been affected and remain valid.
