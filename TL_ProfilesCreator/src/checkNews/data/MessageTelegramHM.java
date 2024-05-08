package checkNews.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


/**
 * Class for store and Manage a HashMap of MESSAGES from Telegram
 * @author Jose Javier Culebras
 * @version 1.0
 * 
 */
public class MessageTelegramHM {
	
	/*--------------------------*/
	/*         VARIABLES    	*/
	/*--------------------------*/
	
	private static HashMap<Long, MessageTelegram> hashMapMessages = new HashMap<Long, MessageTelegram>(2000, (float) 0.75);// https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html -> HashMap(int initialCapacity, float loadFactor)

	
	/*--------------------------*/
	/*    PUBLIC   METHODS      */
	/*--------------------------*/
	
	/**
	 * Method for adding a Message Telegram to HashMap
	 * @param messageId (key)
	 * @param messageTelegram (value)
	 */
	public static void add(Long messageId, MessageTelegram messageTelegram) {		
	
		hashMapMessages.put(messageId, messageTelegram);
	}
	
	
	/**
	 * Method for remove all data from the hashMap
	 */
	public static void clearHM () {
		
		hashMapMessages.clear();
	}
	
	/** 
	 * Method for getting the HashMap of objects News
	 * @return, the HashMap
	 */
	public static HashMap<Long, MessageTelegram> getMessageTelegramHM() {
		
		return hashMapMessages;
	}
	
	/**
	 * 
	 * @return number of the elements from the HashMap
	 */
	public static int getNumberOfElements() {
		
		return hashMapMessages.size();
		
	}
	
	/**
	 * Method for getting all Messages which contains a specific New
	 * @param query, the New 
	 * @return, list of messages telegram
	 */
	public static ArrayList<MessageTelegram> getAllNewsMessages(String query){
		
		ArrayList<MessageTelegram> listOfMessages = new ArrayList<MessageTelegram>();
		MessageTelegram messageTelegramRec = new MessageTelegram();
		query = query.toLowerCase();
		String[] itemsQuery = query.trim().split("\\s+"); //remove white spaces at the begining of the string and split on simple string words
		int founds = 0;//Number of words which match with the filter
		
		try {
			
			Iterator<Entry<Long, MessageTelegram>> hmIterator = hashMapMessages.entrySet().iterator();
			
		    while (hmIterator.hasNext()) { 
		    	
		    	founds = itemsQuery.length;
		        @SuppressWarnings("rawtypes")
				Map.Entry hashMapElement = (Map.Entry)hmIterator.next(); 
		        messageTelegramRec = (MessageTelegram) hashMapElement.getValue();
		        
		        if (messageTelegramRec.getSource().equals(query)) {
		        	
		        	listOfMessages.add(messageTelegramRec);
		        	continue;
		        }
		        
		        String content = messageTelegramRec.getContent().toLowerCase();
		       
		        for (String item : itemsQuery) {
					
		        	if (content.contains(item)) {
						
		        		founds--; //Each time we have a match
		        	}
		        
		        if (founds == 0) {//All words match with the filter, so we add the message
					
		        	listOfMessages.add(messageTelegramRec);
					}
				}
		          
		    }
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		
		Collections.sort(listOfMessages, new MessageTelegramComparator());
		
		return listOfMessages;
	}
	
	
	/**
	 * Method for getting a sorted list of Messages
	 * @return listOfMessages
	 */
	@SuppressWarnings("rawtypes")
	public static ArrayList<MessageTelegram> getMessageTelegramList() {
		
		ArrayList<MessageTelegram> listOfMessages = new ArrayList<MessageTelegram>();
		MessageTelegram messageRec = new MessageTelegram();
		
		try {
			
			Iterator<Entry<Long, MessageTelegram>> hmIterator = hashMapMessages.entrySet().iterator();
			
		    while (hmIterator.hasNext()) { 
		    	
		    	Map.Entry hashMapElement = (Map.Entry)hmIterator.next(); 
		        messageRec = (MessageTelegram) hashMapElement.getValue();
		        listOfMessages.add(messageRec);
		    }
		} catch (NullPointerException e) {
			
			System.out.println("WARNING: no messages found");
		}
		
		Collections.sort(listOfMessages, new MessageTelegramComparator());
		
		
		return listOfMessages;
	}
	
	
	/**
	 * Method for getting a filter list of Messages
	 * The filter may be a group of words
	 * @param query (a group of words)
	 * @return listOfMessages
	 */
	@SuppressWarnings("rawtypes")
	public static ArrayList<MessageTelegram> getMessageTelegramFilterListOld(String query) {
		
		ArrayList<MessageTelegram> listOfMessages = new ArrayList<MessageTelegram>();
		MessageTelegram messageTelegramRecord = new MessageTelegram();
		query = query.toLowerCase(); 
		String[] itemsQuery = query.split("\\s+"); //split on simple string words using space as separator.
		int founds = 0; //Number of words which match with the filter
		
		try {
			
			Iterator<Entry<Long, MessageTelegram>> hmIterator = hashMapMessages.entrySet().iterator();
			
		    while (hmIterator.hasNext()) { 
		    	
		    	founds = itemsQuery.length;
		    	Map.Entry hashMapElement = (Map.Entry)hmIterator.next(); 
		        messageTelegramRecord = (MessageTelegram) hashMapElement.getValue();
		        String content = messageTelegramRecord.getContent().toLowerCase();
		        
		        for (String item : itemsQuery) {
					
		        	if (content.contains(item)) {
						
		        		founds--; //Each time we have a match
		        	}
		        
		        if (founds == 0) {//All words match with the filter, so we add the message
					
		        	listOfMessages.add(messageTelegramRecord);
					}
				}		        
		    }
		} catch (NullPointerException e) {
			
			System.out.println("WARNING: no messages found");
		}
		
		Collections.sort(listOfMessages, new MessageTelegramComparator());
		
		return listOfMessages;
	}
	
	/**
	 * Method for getting all MessagesTelegram send by a senderId
	 * @param query (the senderId)
	 * @return, list of messages telegram
	 */
	@SuppressWarnings("rawtypes")
	public static ArrayList<MessageTelegram> getAllUserMessages(Long query) {
		
		ArrayList<MessageTelegram> listOfMessages = new ArrayList<MessageTelegram>();
		MessageTelegram messageTelegramRecord = new MessageTelegram();
		
		try {
			
			Iterator<Entry<Long, MessageTelegram>> hmIterator = hashMapMessages.entrySet().iterator();
			
		    while (hmIterator.hasNext()) { 
		    	
		        Map.Entry hashMapElement = (Map.Entry)hmIterator.next(); 
		        messageTelegramRecord = (MessageTelegram) hashMapElement.getValue();
		        Long senderId = messageTelegramRecord.getSenderId();      
					
		        if (senderId.equals(query)) {
		        	
		        	listOfMessages.add(messageTelegramRecord);
				}   
		    }		    
		} catch (NullPointerException e) {
			
			e.printStackTrace();
		}
		
		Collections.sort(listOfMessages, new MessageTelegramComparator());
		
		return listOfMessages;	
	}

	
	/**
	 * Method for getting the HM value of a message
	 * @param messageId (key)
	 * @return value
	 */
	public static MessageTelegram getMessageTelegram(Long messageId) {
		
		MessageTelegram value = hashMapMessages.get(messageId);  //get(Object key) Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key.
		return value;
	}
}
