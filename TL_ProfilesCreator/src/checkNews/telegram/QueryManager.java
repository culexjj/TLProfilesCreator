package checkNews.telegram;




import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.concurrent.ConcurrentMap;

import checkNews.data.ChatTelegram;
import checkNews.data.ChatTelegramHM;
import checkNews.data.ChatTelegramStatus;
import checkNews.data.DataSetMessage;
import checkNews.data.DataSetMessageHM;
import checkNews.data.DataSetUser;
import checkNews.data.DataSetUserHM;
import checkNews.data.MessageTelegram;
import checkNews.data.MessageTelegramHM;
import checkNews.data.MessageType;
import checkNews.data.UserTelegram;
import checkNews.support.CheckInternetConnection;
import checkNews.telegram.TdApi.Message;
import checkNews.telegram.TdApi.User;
import checkNews.telegram.Telegram.OrderedChat;

/**
* Class for managing queries to Telegram. This class is final, so it can't be extended
* @author Jose Javier Culebras
* @version 1.0
*
*/
public final  class QueryManager {
	
	private int sizeOfMessagesList = 0;
	private Message[] list = null; //List of Messages Telegram
	private long[] publicChats = null; //List of public Telegram chats
	private User user = null; //A Telegram user
	private int numberOfMessages = 0;
	private int numberOfMessagesNew = 0;
	private int numberOfChats = 0;
	private int numberOfChatsNew = 0;
	
	
	
	/*--------------------------*/
	/*          BUILDERS        */
	/*--------------------------*/
	
	/**
	 * QueryManager builder
	 */
	public QueryManager () {
		
	}

	/*--------------------------*/
	/*    PUBLIC   METHODS      */
	/*--------------------------*/
	
	/**
	 * Method for getting messages which contains the search string on a specific chat
	 * The Telegrams API limit the output to 100 messages 
	 * https://core.telegram.org/tdlib/docs/classtd_1_1td__api_1_1search_chat_messages.html -> "The maximum number of messages to be returned; must be positive and can't be greater than 100."
	 * @param query, string to look for
	 * @param chat, chat ID
	 *  
	 */
	public void searchChatMessages(String query, String chat) {
		
		int time = 0; 
    	Telegram.searchChatMessages(query, chat, 100); //async call
    	    	
		while (!(Telegram.getControl()) && (time < 51)) { //if after  5 seconds there is not reply, we exit
			
			try {
				
				time ++;		
				Thread.sleep(100);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
		
		try {
			
			Telegram.setControl(false); //Set lock to false for next query
			list = Telegram.getChatMessages().messages;	
			sizeOfMessagesList = list.length;	
			formatMessages(list, query);
		}
		catch(NullPointerException e) {
			
			if (CheckInternetConnection.checkConnection() == false ) { //Check Connection to the internet
				
				System.out.println ("ERROR: no internet connection");
				return;
			}
			
			System.out.println("WARNING: not reply");
			return;
		}
	}
	

	/**
	 * Method for getting messages from all chats which contains the search string 
	 * The Telegrams API limit the output to 100 messages 
	 * https://core.telegram.org/tdlib/docs/classtd_1_1td__api_1_1search_chat_messages.html -> "The maximum number of messages to be returned; must be positive and can't be greater than 100."
	 * @param query (string to look for)
	 *  
	 */
	public void searchAllChatMessages(String query) {
		
		int time = 0; 
		Telegram.searchAllChatMessages(query, 100); //async call
		
		while (!(Telegram.getControl()) && (time < 51)) { //if after  5 seconds there is not reply, we exit
				
			try {
					
				time ++;
				Thread.sleep(100);
			} catch (InterruptedException e) {
					
				e.printStackTrace();
			}
		}
		
		try {
		
			Telegram.setControl(false); //volvemos a establer el valor a falso para la siguiente query
			list = Telegram.getAllChatMessages().messages;	
			sizeOfMessagesList = list.length;	
			formatMessages(list, query);
		}
		catch(NullPointerException e) {
			
			if (CheckInternetConnection.checkConnection() == false ) { //Check Connection to the internet
				
				System.out.println ("ERROR: no internet connection");
				return;
			}

			System.out.println("WARNING: not reply");
			return;
		}
	}
	
		
	/**
	 * Method for getting the list of chats which the user belongs
	 * @param value, max number of chats. 
	 * It seems Telegram's API may have some issues managing a big number. So far with value = 20 the output is right. 
	 * https://core.telegram.org/tdlib/docs/classtd_1_1td__api_1_1load_chats.html -> "The maximum number of chats to be loaded.
	 * For optimal performance, the number of loaded chats is chosen by TDLib and can be smaller than the specified limit,
	 * even if the end of the list is not reached."
	 *   
	 */
	public void getChatList(int value) {
		
		Telegram.getMainChatList(value);
		
		java.util.Iterator<OrderedChat> iter = Telegram.getIter();
		ConcurrentMap<Long, TdApi.Chat> chats = Telegram.getChats();
		NavigableSet<OrderedChat> mainChatList = Telegram.getChatList();
		
		for (int i = 0; i < value && i < mainChatList.size(); i++) {
			
            long chatId = iter.next().chatId;
            TdApi.Chat chatNumber = chats.get(chatId);
            
            synchronized (chatNumber) {
            	 
                ChatTelegram chatRecord = new ChatTelegram(chatId, chatNumber.title, ChatTelegramStatus.MEMBER);
                ChatTelegramHM.add(chatId, chatRecord);            		
            }
        }
	}
	
	
	/**
	 * Method for getting the chatId an d title of all public chats that match the query
	 * Returns nothing if the length of the searched username prefix is less than 5. Excludes private chats with contacts and chats from the chat list from the results. 
	 * https://tdlibx.github.io/td/docs/org/drinkless/td/libcore/telegram/TdApi.SearchPublicChats.html
	 * @param query
	 */
	public void searchPublicGroups(String query) {
		
		if (query.length() < 4) {
			
			System.out.println("INFO: the search string should have at least 4 characters");
			return;
		}
		
		int time = 0; 
		numberOfChats = ChatTelegramHM.getNumberOfElements();
		Telegram.searchPublicGroups(query); //Asynchronous query
    	
		while (!(Telegram.getControl()) && (time < 51)) { //if after  5 seconds there is not reply, we exit
			
			try {
				
				time ++;			
				Thread.sleep(100);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
		
		try {
			
			Telegram.setControl(false); //Set control to false in order to get it ready for next query
			publicChats = Telegram.getPublicChats().chatIds;
			
		}
		catch(NullPointerException e) {
			
			if (CheckInternetConnection.checkConnection() == false ) { //Check Connection to the internet
				
				System.out.println ("ERROR: no internet connection");
				return;
			}
			
			System.out.println("WARNING: not reply");
			return;
		}
		
		try { //for each value on publicChats we get the tittle
			
			for (long chatId : publicChats) {
		
				formatPublicGroups(chatId); 
		    }
		} 
		catch(NullPointerException e) {
			
			if (CheckInternetConnection.checkConnection() == false ) { //Check Connection to the internet
				
				System.out.println ("ERROR: no internet connection");
				return;
			}
			
			System.out.println("WARNING: not reply");
			return;
		}
		
		numberOfChatsNew = ChatTelegramHM.getNumberOfElements();
		System.out.println("INFO: " + (numberOfChatsNew - numberOfChats)+ " chats found");
	}
	
	
	/**
	 * Method for adding the user to a chat
	 * @param query, the chatId
	 */
	public void addUserToChat(String query) {
		
		int time = 0; 
		Telegram.setError(false); //Set error to false in order to get it ready fo the query
		Telegram.joinUserToChat(query); //Asynchronous query
		
		while (!(Telegram.getControl()) && (time < 51)) { //If after 6 seconds there is not reply we stop the loop
			
			try {
				
				time ++;
				Thread.sleep(100);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
		
		if (Telegram.getError()) { //If 0, there is an error with the senderId
			
			if (CheckInternetConnection.checkConnection() == false ) { //Check Connection to the internet
				
				System.out.println ("ERROR: no internet connection");
				return;
			}
			
			System.out.println("WARNING: chatId incorrect");
			Telegram.setError(false); //Set control to false in order to get it ready for next query
			return;
		}
		
			
		Telegram.setControl(false); //Set control to false in order to get it ready for next query
		
	}
	
	
	/**
	 * Method for getting user information
	 * @param query (the sendeId)
	 */
	public void getUser(Long query) {
		
		try {
		
			UserTelegram userTelegram = getUserInfo(query);		
			Long senderId = userTelegram.getSenderId();
			
			if (senderId == 0) { //If 0, there is an error with the senderId
				
				System.out.println("WARNING: senderId incorrect");
				return;
			}
		
			ArrayList<MessageTelegram> listOfMessages = MessageTelegramHM.getAllUserMessages(query);		
			DataSetUser dataSet = new DataSetUser(userTelegram, listOfMessages);
			DataSetUserHM.add(senderId, dataSet);
		
		} catch (NullPointerException e) {
			
			if (CheckInternetConnection.checkConnection() == false ) { //Check Connection to the internet
				
				System.out.println ("ERROR: no internet connection");
				return;
			}
			
			System.out.println("WARNING: senderId incorrect");
			return;
		}
	}
	

	

	
	/**
	 * Method for getting information from a message
	 * @param query (the url/keyword)
	 */
	public void getMessageInfo(String query) {
	
		Long senderId = null;
		ArrayList<MessageTelegram> listOfMessages = MessageTelegramHM.getAllNewsMessages(query);
		
		if (listOfMessages.size() == 0) {
	    	
			System.out.println("WARNING: message incorrect");
			return;
	    }
		
		ArrayList<UserTelegram> listOfUsers = new ArrayList<UserTelegram>();
		UserTelegram userTelegram;
		
		Iterator<MessageTelegram> arrayIterator = listOfMessages.iterator();
		
		while (arrayIterator.hasNext()) {
			
			senderId = ((MessageTelegram) arrayIterator.next()).getSenderId();
			
			if (!((""+senderId).startsWith("-"))) { //If senderId starts with '-' is a basic/super group so, it's not possible to get information about it
				
				userTelegram = getUserInfo(senderId); 
				listOfUsers.add(userTelegram);
			}
			
		}
		
		DataSetMessage dataSet = new DataSetMessage(query, listOfUsers);
		DataSetMessageHM.add(query, dataSet);
	}
	
	
	/**
	 * Method for multiple searching Messages
	 * @param list (the list of url to search)
	 */
	public void searchAllMessagesList(ArrayList<String> list) {
		
		if (CheckInternetConnection.checkConnection() == false ) { //Check Connection to the internet
			
			System.out.println ("ERROR: no internet connection");
			return;
		}
		
		ArrayList<String> queryList = list;
		
		if (queryList.size() == 0) {
			
			System.out.println("WARNING: check selection "); 
			return;
		}
		
		for (String query : queryList) {
	        
			if (!(query.equals(""))) {
				
				searchAllChatMessages(query);
			}
		}	
	}
	
	
	/*--------------------------*/
	/*    PRIVATE METHODS       */
	/*--------------------------*/
	
	
	/**
	 *  Method for getting a list of users information
	 * @param userId (the senderId)
	 */
	@SuppressWarnings("unlikely-arg-type")
	private UserTelegram getUserInfo(Long userId) {
				
		int time = 0; 
		Telegram.getUserInfo(userId); //Asynchronous query
		
		while (!(Telegram.getControl()) && (time < 51)) { //If after 6 seconds there is not reply we stop the loop
			
			try {
				
				time ++;				
				Thread.sleep(100);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
		
		Telegram.setControl(false); //Set control to false in order to get it ready for next query
		
		user = Telegram.getUser();
		Long senderId = user.id;
		
		if (senderId.equals("0")) { //Control, if senderId incorrect Telegram returns '0'
			
			return null;
		}
		
		String firstName = user.firstName;
		String lastName = user.lastName;
		String[] userNames = {""};
		
		try {
			
			userNames = user.usernames.activeUsernames;//List of active usernames; the first one must be shown as the primary username.
		} 
		catch (NullPointerException e) { //Ignored, it is not a mistake
		}
		
		String userName = userNames[0];
		
		boolean isFake = user.isFake; //True, if many users reported this user as a fake account.
		boolean isScam = user.isScam; //True, if many users reported this user as a scam.
		boolean isVerified = user.isVerified; //True, if the user is verified.
		boolean isPremium = user.isPremium; 
		
		UserTelegram userTelegram = new UserTelegram(firstName, lastName, userName, senderId, isFake, isScam, isVerified, isPremium);
		return userTelegram;	
		
	}
	
	
	/**
	 * Method for  managing and setting format of the search output
	 * The content can be a photo, a video, a text
	 * 
	 */	

	private void formatMessages(Message[] list, String query)  {
		
		Long messageId = null;
		Long senderId = null;
		Long chatId = null;
		MessageType type = MessageType.DEFAULT;
		String content = "";

		numberOfMessages = MessageTelegramHM.getNumberOfElements();

		/* Iterate the array */
		for (int i = 0; i < sizeOfMessagesList ; i++) {            
			
			messageId = list[i].id;
					
			String sender = list[i].senderId.toString(); //Get senderId  Value
			String[] tokensSender = sender.split(" "); 
			String result = tokensSender[5].substring(0, tokensSender[5].length() - 3); //remove CR/LF and last }
			senderId = Long.valueOf(result).longValue(); //Convert to Long
								
			long messageDate = list[i].date; //Unix format date
			Date tmpDate = new Date(messageDate*1000L); //Miliseconds 
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); //Date format
			String date = dateFormat.format(tmpDate);									
			
			chatId = list[i].chatId; //Long value to String
					
			/* This way of getting the text message doesn't work if the text has "" itself, otherwise works fine
			 It should be a problem since we are looking for urls and it isn't a allowed character
			 a url doesn't allow --> blank space nor next list of characters ( . , : & % $ / ( ) = ? ¿ " !)
			 */
			String messageText = list[i].content.toString();
			String prefix = "ContentMessageText {\r\n" + "  text = FormattedText {"; //Prefix to remove 
			String text = messageText.substring(messageText.indexOf(prefix) + prefix.length()); 
			String[] tokensContent = text.split("\"");
						
			int constructor = list[i].content.getConstructor(); //the constructor number of the object is used for setting the right action
						
			try {
			if ( constructor == -448050478)  {
				
				type = MessageType.PICTURE;
				if (tokensContent.length > 25) {
					if (tokensContent[25].equals("W") || tokensContent[25].equals("w") || tokensContent[25].equals("y")){
						
						content =tokensContent[33]; 
					} else {
						
						content =tokensContent[25];
					}													
				} else {
						if (tokensContent[9].equals("m") ||tokensContent[9].equals("x") ) {
							content =tokensContent[17];
						} else {
							content =tokensContent[9];
						}	
				}
				
					MessageTelegram message = new MessageTelegram(messageId, senderId, date, chatId, content, type, query);
					MessageTelegramHM.add(messageId, message);
					continue;
			} else if  ( constructor == -1053465942)  {
												
				type = MessageType.TEXT;
				content = tokensContent[1];
				MessageTelegram message = new MessageTelegram(messageId, senderId, date, chatId, content, type, query);
				MessageTelegramHM.add(messageId, message);
				continue;
			} else if  ( constructor == -1237516229)  {
			
				type = MessageType.VIDEO;
				if (tokensContent[17].equals("")) {
				
					content = tokensContent[1];
				} else {
				
					content = tokensContent[17];
				}
				
				MessageTelegram message = new MessageTelegram(messageId, senderId, date, chatId, content, type, query);
				MessageTelegramHM.add(messageId, message);
				continue;
			} else if ( constructor == 596945783) {
												
				type = MessageType.DEFAULT;
				content = tokensContent[17];
				MessageTelegram message = new MessageTelegram(messageId, senderId, date, chatId, content, type, query );
				MessageTelegramHM.add(messageId, message);
				continue;
			} else {
			
				type = MessageType.DEFAULT;
				content = tokensContent[1] ; 
				MessageTelegram message = new MessageTelegram(messageId, senderId, date, chatId, content, type, query);
				MessageTelegramHM.add(messageId, message);
			}
		}	 catch (ArrayIndexOutOfBoundsException e) {
			
			//System.out.println ("ERROR PARSING ");
			//System.out.println ("Constructor : " + constructor);
			//System.out.println ("MessageId :" + messageId);
			}
		}
		
		numberOfMessagesNew = MessageTelegramHM.getNumberOfElements();
		System.out.println("INFO: " + (numberOfMessagesNew-numberOfMessages)  + " messages found");
		sizeOfMessagesList = 0; //Reset size
	}

	
	/**
	 * Method for  getting the title of a chat
	 * @param chatId
	 */
	private void formatPublicGroups(Long chatId) {
		
		int time = 0; 		
		Telegram.searchChat(chatId); //Asynchronous query
		
		while (!(Telegram.getControl()) && (time < 21)) { //If after 1 seconds there is not reply we stop the loop
			try {
				time ++;				
				Thread.sleep(50); // 0,05 seconds
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	
		Telegram.setControl(false); //Set control to false in order to get it ready for next query
		
		ChatTelegram chatRec = new ChatTelegram(chatId, Telegram.getChat().title, ChatTelegramStatus.NO_MEMBER);
        ChatTelegramHM.add(chatId, chatRec);
	}
	
}
