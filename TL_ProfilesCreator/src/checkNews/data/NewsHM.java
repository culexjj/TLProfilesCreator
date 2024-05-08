package checkNews.data;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Class for keeping and  Managing a HashMap of NEWS 
 * @author Jose Javier Culebras
 * @version 0.9 
 */
public class NewsHM {
	
	/*--------------------------*/
	/*         VARIABLES    	*/
	/*--------------------------*/
	
	private static HashMap<String, News> hashMapNews = new HashMap<String, News>(2000, (float) 0.75);// https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html -> HashMap(int initialCapacity, float loadFactor)
	
	
	/*--------------------------*/
	/*    PUBLIC   METHODS      */
	/*--------------------------*/
	
	/**
	 * If the key  already exist on the hashMap is not added to avoid a new mapping action
	 * The flag repeated is set = true
	 * @param url (key)
	 * @param news (value)
	 */
	public static void add(String url, News news) {
		
		if (!(hashMapNews.containsKey(url))) {
			
			hashMapNews.put(url.toLowerCase(), news);
		} 
	}
	
	
	/**
	 * Method for remove all data from the hashMap
	 */
	public static void clearHM () {
		
		hashMapNews.clear();
	}
	
	
	/** 
	 * Method for getting the HashMap of objects News
	 * @return hashMapNews
	 */
	public static HashMap<String, News> getNewsHM() {
		
		return hashMapNews;
	}
	
	
	/**
	 * 
	 * @return number of the elements from the HashMap
	 */
	public static int getNumberOfElements() {
		
		return hashMapNews.size();
		
	}
	
	
	/**
	 * Method for getting a sorted list of all News
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static ArrayList<News> getNewsList() {
		
		ArrayList<News> listOfNews = new ArrayList<News>();
		News newsRecord = new News();
		
		try {
			
			Iterator<Entry<String, News>> hmIterator = hashMapNews.entrySet().iterator();
			
		    while (hmIterator.hasNext()) { 
		    	
		    	Map.Entry hashMapElement = (Map.Entry)hmIterator.next(); 
		    	newsRecord = (News) hashMapElement.getValue();
		        listOfNews.add(newsRecord);
		    }
		} catch (NullPointerException e) {
			
			System.out.println("WARNING: no messages found");
		}
		
		Collections.sort(listOfNews, new NewsComparator());
		
		return listOfNews;
	}
	
	
	/**
	 * Method for getting a sorted and filter list of News
	 * The filter may be a group of words
	 * @param query (a group of words)
	 * @return listOfNews
	 */
	@SuppressWarnings("rawtypes")
	public static ArrayList<News> getNewsFilterListOld(String query) {
		
		ArrayList<News> listOfNews = new ArrayList<News>();
		News newsRecord = new News();
		query = query.toLowerCase(); 
		String[] itemsQuery = query.split("\\s+"); //split on simple string words using space as separator.
		int founds = 0; //Number of words which match with the filter
		
		try {
			
			Iterator<Entry<String, News>> hmIterator = hashMapNews.entrySet().iterator();
			
		    while (hmIterator.hasNext()) { 
		    	
		    	founds = itemsQuery.length; //set counter
		    	Map.Entry hashMapElement = (Map.Entry)hmIterator.next(); 
		        newsRecord = (News) hashMapElement.getValue();
		        String title = newsRecord.getTitle().toLowerCase(); 
		        
		        for (String item : itemsQuery) {
					
		        	if (title.contains(item)) { //Each time we have a match
		        		
		        		founds--; //update counter
		        }
		        
		        	if (founds == 0) { //success, all words found
		        	
		        		listOfNews.add(newsRecord);
		        	}
		        }
		    }
		} catch (NullPointerException e) {
			
			System.out.println("WARNING: no messages found");
		}
		
		Collections.sort(listOfNews, new NewsComparator());
		
		return listOfNews;
	}
	
}
	
    

