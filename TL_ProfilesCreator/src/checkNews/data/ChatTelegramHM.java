package checkNews.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Class for keeping and Managing a HashMap of Chats
 * @author Jose Javier Culebras
 * @version 1.0
 */

public class ChatTelegramHM {
	

	/*--------------------------*/
	/*         VARIABLES    	*/
	/*--------------------------*/
	
	private static HashMap<Long, ChatTelegram> hashMapChats = new HashMap<Long, ChatTelegram>(2000, (float) 0.75);// https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html -> HashMap(int initialCapacity, float loadFactor)
		
	
	/*--------------------------*/
	/*    PUBLIC   METHODS      */
	/*--------------------------*/
	
	/**
	 * Method for adding a Chat from Telegram to the HashMap
	 * @param chatId (key)
	 * @param chat (value)
	 */
	public static void add(Long chatId, ChatTelegram chat) {
		
		hashMapChats.put(chatId, chat);		
	}
	
	
	/** 
	 * Method for getting the HashMap of objects ChatTelegram
	 * @return hashMapChats
	 */
	public static HashMap<Long, ChatTelegram> getChatsTelegramHM() {
		
		return hashMapChats;
	}
	
	
	/**
	 * 
	 * @return number of the elements from the HashMap
	 */
	public static int getNumberOfElements() {
		
		return hashMapChats.size();
		
	}
	
	
	/**
	 * Method for checking if the user is already member of a chat
	 * @param chatId
	 * @return the status
	 */
	public static boolean checkChat(Long chatId) {
		
		if (hashMapChats.containsKey(chatId)) {
			
			ChatTelegramStatus status = hashMapChats.get(chatId).getStatus();
			
			if (status.toString().equals("MEMBER")) {
			
				return true;
			} else {
			
				return false;
			}
		}
		
		return false;
	}
	
	
	
	/**
	 * Method for getting a sorted list of all Chats
	 * @return list of chats
	 */
	public static ArrayList<ChatTelegram> getChatTelegramList() {
		
		ArrayList<ChatTelegram> listOfChats = new ArrayList<ChatTelegram>();
		ChatTelegram chatRecord = new ChatTelegram();
		
		try {
			
			Iterator<Entry<Long, ChatTelegram>> hmIterator = hashMapChats.entrySet().iterator();
			
		    while (hmIterator.hasNext()) { 
		    	
		    	@SuppressWarnings("rawtypes")
				Map.Entry hashMapElement = (Map.Entry)hmIterator.next(); 
		    	chatRecord = (ChatTelegram) hashMapElement.getValue();
		        listOfChats.add(chatRecord);
		    }
		} catch (NullPointerException e) {
			
			System.out.println("NO CHATS FOUND! ");
		}
		
		Collections.sort(listOfChats, new ChatTelegramComparator());
		
		return listOfChats;
	}
	
}
