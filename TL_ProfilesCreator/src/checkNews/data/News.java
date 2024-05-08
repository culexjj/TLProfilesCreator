package checkNews.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * this class describe a News from the internet
 * @author Jose Javier Culebras
 * @version 1.0
 * 
 */

public class News {
	

	/*--------------------------*/
	/*  VARIABLES DECLARATION	*/
	/*--------------------------*/
	
	private String date; 
	private String title;
	private String url;
	private String description; //The item synopsis.
	private NewsType type; //Type of the News Source INTERNET, RSS, DEFAULT
		

	/*--------------------------*/
	/*          BUILDERS        */
	/*--------------------------*/
	
	/**
	 * News builder
	 */
	public News () {
		
	}
	
	/**
	 * News builder 
	 * @param title
	 * @param url
	 * @param description
	 * @param type
	 */
	public News (String title, String url, String description, NewsType type) {
		
		LocalDate now = LocalDate.now();		
		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String formattedDate = now.format(pattern); //17-02-2022
		this.date = formattedDate;
		this.title = title;
		this.url = url;
		this.description = description;
		this.type = type;
	}
				
	/**
	 * News builder use for Json tasks
	 * @param date
	 * @param title
	 * @param url
	 * @param description
	 * @param type
	 */
	public News (String date, String title, String url, String description, NewsType type) {
				
		this.date = date;
		this.title = title;
		this.url = url; 
		this.description = description;
		this.type = type;
	}

	
	/*--------------------------*/
	/*    PUBLIC   METHODS      */
	/*--------------------------*/
	
	/**
	 * 
	 * @return title
	 */
	public String getTitle() {
		
		return title;
	}
	
	
	/**
	 * 
	 * @return url
	 */
	public String getUrl() {
		
		return url;
	}
	
	
	/**
	 * 
	 * @return date
	 */
	public String getDate() {

		return date;
	}
	

	/**
	 * 
	 * @param date
	 */
	public void setDate(String date) {
		
		this.date = date;
	}
	
	
	/**
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		
		this.title = title;
	}
	
	
	/**
	 * 
	 * @param url
	 */
	public void setUrl(String url) {
		
		this.url = url;
	}	
	
	
	/**
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		
		this.description = description;
	}
	
	
	/**
	 * 
	 * @return description  (the item synopsis) 
	 */
	public String getDescription() {
					
		return description;
	}
	
	
	/**
	 * 
	 * @param type
	 */
	public void setType(NewsType type) {
					
		this.type = type;
	}
	
	
	/**
	 * 
	 * @return INTERNET, RSS, DEFAULT 
	 */
	public NewsType getType() {
					
		return type;
	}
			
}
