package checkNews.search;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.net.URLDecoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import checkNews.data.News;
import checkNews.data.NewsHM;
import checkNews.data.NewsType;
//import checkNews.support.CheckInternetConnection;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
	
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
	
	import java.time.Duration;
	import java.util.List;


/**
 * Class for fetching News using RSS sources or standard google searches
 * requires org.jsoup in module-info.java  and  jsoup-1.16.1.jar.  Set on 'properties-java build path-libraries-module path'
 * Since July'25 Selenium & Duck Duck go is used for searching news (https://rayobyte.com/blog/google-update-2025/?utm_source=chatgpt.com)
 * requires org.seleniumhq in module-info.java and selenium*.jar + ChromeDriver.exe.
 * @author Jose Javier Culebras
 * @version 1.1 
 * 
 */

public class InternetSearch {
	
	
	/*--------------------------*/
	/*  VARIABLES DECLARATION	*/
	/*--------------------------*/ 
	
	private int numberOfNews = 0;
	private int numberOfNewsNew = 0;
	
	
	/*--------------------------*/
	/*          BUILDERS        */
	/*--------------------------*/
	
	/**
	 * InternetSearch builder
	 */
	public InternetSearch() {

	}
	
	
	/*--------------------------*/
	/*    PUBLIC   METHODS      */
	/*--------------------------*/
	
	/**
	 * In Spring 2025 Google has changed the structure of its search results, and is actively reinforcing measures against automated scraping
	 * (such as blocking IPs, displaying captchas, or dynamically changing HTML with JavaScript), making tools like Jsoup (which doesn't run JavaScript)
	 * no longer work for this purpose. Method for doing a Google search, output fixed  to 25 results
	 * A list of different listAgents is used for avoid issues with Google search engine
	 * @param query (the string to look for)
	 * @deprecated
	 */
	public void searchNewsByTitleDeprecated(String query) {
		
		Document documentHTML = null; 
		NewsType type = NewsType.INTERNET;
		String description = "";
		numberOfNews = NewsHM.getNumberOfElements(); //Counter. NUmber of News
		
		String search = "https://www.google.es/search?q="+query+"&num="+25+"&gl=es&hl=es"; //Output -> 25 elements
		
		String listAgents[] = {"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.3	27.72",
		"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/16.6 Safari/605.1.1	20.04",
		"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.3	8.99",
		"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.1.2 Safari/605.1.1	8.99",
		"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.2 Safari/605.1.1	7.49",
		"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.0 Safari/605.1.1	6.55",
		"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:121.0) Gecko/20100101 Firefox/121.	5.24",
		"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.3	3.0",
		"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36 Edg/120.0.0.	3.0",
		"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.3	2.25",
		"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36 OPR/106.0.0.	1.5",
		"Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:109.0) Gecko/20100101 Firefox/115.	1.5",
		"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36 Agency/99.8.2237.3	0.75",
		"Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:109.0) Gecko/20100101 Firefox/115.	0.75",
		"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.65 Safari/537.3	0.75",
		"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/117.	0.75",
		"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36 Edg/117.0.2045.4	0.75"};
		
		int browserAgent = (int)(Math.random()*listAgents.length); //A random user agents 
		
		try { 
			
			documentHTML = Jsoup.connect(search).userAgent(listAgents[browserAgent]).get();//Fetching content from google
		} catch (IOException e) {
			
			System.out.println("WARNING: error searching news");
			
			/*if (CheckInternetConnection.checkConnection() == false ) { //Check Connection to the internet
			
				System.out.println ("ERROR: no internet connection");
			}*/
			 
			return;
		}
		
	    for (Element element : documentHTML.select(".yuRUbf a")){ //Parsing by Select <div class="yuRUbf"> container
	    	
	    	String title = element.text();
	    	String url = element.attr("href");
	        News aNew = new News(title, url, description, type);
	        NewsHM.add(url.toLowerCase(), aNew);
	    }

	    numberOfNewsNew = NewsHM.getNumberOfElements();
		System.out.println("INFO: " + (numberOfNewsNew-numberOfNews) + " news found" );
	}
	
	
	
	/**
	 * Method for doing a search using DuckDuckGo
	 * @param query (the string to look for)
	 */
	public void searchNewsByTitle(String query) {

        NewsType type = NewsType.INTERNET;
        String description = "";
        numberOfNews = NewsHM.getNumberOfElements();

        /* WebDriver configuration */
        //System.setProperty("webdriver.chrome.driver", "D:\\TFG\\CheckNews\\libraries\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", new File("chromedriver/chromedriver.exe").getAbsolutePath());
                
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1200,800");
        //options.addArguments("--lang=es");
        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.7204.169 Safari/537.36");

        WebDriver driver = new ChromeDriver(options); //Driver Object
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); //Waiting for results till 10 seconds

        try {
        	for (int offset = 0; offset < 30; offset += 10) { //loop for getting +10 results
        	    String url = "https://html.duckduckgo.com/html/?q=" + query.replace(" ", "+") + "&s=" + offset;
        	    driver.get(url);
            
	            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".result__title")));
	
	            List<WebElement> results = driver.findElements(By.cssSelector(".result__title"));
	            for (WebElement result : results) {
	                try {
	                    WebElement link = result.findElement(By.tagName("a"));
	                    String title = link.getText();
	                    String href = link.getAttribute("href");
	                    
	                    //* URL decoding /
	                    String decodedUrl = href;
	                    if (href.contains("uddg=")) {
	                        int start = href.indexOf("uddg=") + 5;
	                        int end = href.indexOf("&", start);
	                        if (end == -1) end = href.length();
	                        String encoded = href.substring(start, end);
	                        decodedUrl = URLDecoder.decode(encoded, StandardCharsets.UTF_8);
	                    }
	                                     
	                    News aNew = new News(title, decodedUrl, description, type);
	                    NewsHM.add(decodedUrl.toLowerCase(), aNew);
	
	                } catch (Exception e) {
	                    
	                }
	            }
        	}

        } catch (Exception e) {
            System.out.println("WARNING: error searching news");
            e.printStackTrace();
        } finally {
            driver.quit();
        }

        numberOfNewsNew = NewsHM.getNumberOfElements();
        System.out.println("INFO: " + (numberOfNewsNew - numberOfNews) + " news found"); 
    }	
	
	
	/**
	 * Method for fetching News from a RSS source,
	 * It is needed to create a InputStream object for setting a specific charset (UTF-8)
	 * the description of the news may content HTML code  https://www.rssboard.org/rss-specification
	 * So, all HTML codification is removed from the description of the news
	 * @param rss (url from the source)
	 */
	@SuppressWarnings("deprecation")
	public void searchNewsByRSS(String rss) {
			
		String strURL = rss;
		InputStream inStream = null;
		Document documentHTML = null; 
		String url = "";
		NewsType type = NewsType.RSS;
		String description = "";
		numberOfNews = NewsHM.getNumberOfElements(); //Counter. NUmber of News
		
		try {
			
			inStream = new URL(strURL).openStream();
		} catch (MalformedURLException e) {
			
			System.out.println("ERROR: URL format incorrect");
			return;
			
		} catch (IOException e) {
			
			System.out.println("WARNING: error searching news");
			
			/*if (CheckInternetConnection.checkConnection() == false ) { //Check Connection to the internet
				
				System.out.println ("ERROR: no internet connection");
			}*/
			
			return;
		}
		
		try {
			
			documentHTML = Jsoup.parse(inStream, "UTF-8", strURL); //Fetching content from the feed service and parse it with a specific charset
		} catch (IOException e) {
			
			System.out.println("WARNING: error searching news");
			
			/*if (CheckInternetConnection.checkConnection() == false ) { //Check Connection to the internet
				
				System.out.println ("ERROR: no internet connection");
			}*/

			return;
		}
		
		documentHTML.charset(StandardCharsets.UTF_8); 
		
		for (Element element : documentHTML.select("item")){ //parsing by element
			
	    	String title = element.select("title").text();
	    	title = title.replace("<![CDATA[", "");  title = title.replace("]]>", "");  
	   
	    	/*the description contains the text (entity-encoded HTML is allowed) <- https://www.rssboard.org/rss-specification. So, all HTML codification is removed */
	    	description = element.select("description").text().replaceAll("\\<.*?\\>", "");
	    	description = description.replace("&quot;","\"");description = description.replace("&aacute;","á");description = description.replace("&eacute;","é");
	    	description = description.replace("&iacute;","í");description = description.replace("&oacute;","ó");description = description.replace("&uacute;","ú");
	    	description = description.replace("&ntilde;","ñ");description = description.replace("&#160;"," ");description = description.replace("&nbsp;", " ");
	    	
	    	String[] itemLines = element.toString().split("\r\n|\n|\r");
	    	
	    	for (String line : itemLines) {
	    		
	    		if (line.startsWith(" <link>")) { //link of the News
	    			
					   url = line.substring(7);
					   break;
				   }
	    	}
	    	
	    	News aNew = new News(title, url, description, type);
	        NewsHM.add(url.toLowerCase(), aNew);
	    }
	  
		numberOfNewsNew = NewsHM.getNumberOfElements();
		System.out.println("INFO: " + (numberOfNewsNew-numberOfNews) + " news found" );
	}
	
	
	
	/**
	 * RSS search with filter
	 * The filter has to be an unique string
	 * It is needed to create a InputStream object for setting a specific charset (UTF-8)
	 * the description of the news may content HTML code  https://www.rssboard.org/rss-specification
	 * So, all HTML codification is removed from the description of the news 
	 * @param rss (url from the source)
	 * @param query (filter)
	 */
	@SuppressWarnings("deprecation")
	public void searchNewsByRSS(String rss, String query) {
		
		String strURL = rss;
		InputStream inStream = null;
		Document documentXML = null; 
		String url = "";
		NewsType type = NewsType.RSS;
		String description = "";
		numberOfNews = NewsHM.getNumberOfElements(); //Counter. NUmber of News
		
		try {
			
			inStream = new URL(strURL).openStream();
		} catch (MalformedURLException e) {
			
			System.out.println("ERROR: URL format incorrect");
			return;
		} catch (IOException e) {
			
			System.out.println("WARNING: error searching news");
			
			/*if (CheckInternetConnection.checkConnection() == false ) { //Check Connection to the internet
				
				System.out.println ("ERROR: no internet connection");
			}*/
			
			return;
		}
		
		try {
			
			documentXML = Jsoup.parse(inStream, "UTF-8", strURL); //Fetching content from the feed service and parse it with a specific charset
		} catch (IOException e) {
			
			System.out.println("WARNING: error searching news");
			
			/*if (CheckInternetConnection.checkConnection() == false ) { //Check Connection to the internet
				
				System.out.println ("ERROR: no internet connection");
			}*/
			
			return;
		}
		
		documentXML.charset(StandardCharsets.UTF_8);
		
		for (Element element : documentXML.select("item")){ //parsing by element
	    	String title = element.select("title").text();
	    	
	    	if (title.toLowerCase().contains(query.toLowerCase())) {
	    		
	    		//the description contains the text (entity-encoded HTML is allowed) <- https://www.rssboard.org/rss-specification. So, all HTML codification is removed
	    		description = element.select("description").text().replaceAll("\\<.*?\\>", "");
	    		description = description.replace("&quot;","\"");description = description.replace("&aacute;","á");description = description.replace("&eacute;","é");
		    	description = description.replace("&iacute;","í");description = description.replace("&oacute;","ó");description = description.replace("&uacute;","ú");
		    	description = description.replace("&ntilde;","ñ");description = description.replace("&#160;"," ");description = description.replace("&nbsp;", " ");
	    		
		    	String[] itemLines = element.toString().split("\r\n|\n|\r");
	    		
	    		for (String line : itemLines) {
	    			
		    		if (line.startsWith(" <link>")) { //link of the News
		    			
					   url = line.substring(7);
					   break;
					}
		    	}
		    	
		        News aNew = new News(title, url, description, type);
		        NewsHM.add(url.toLowerCase(), aNew);
	    	} else {
	    	
	    		continue;
	    	}
	    }
		
		numberOfNewsNew = NewsHM.getNumberOfElements();
		System.out.println("INFO: " + (numberOfNewsNew-numberOfNews) + " news found" );
	}
	
}
