package checkNews.api;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import checkNews.data.ChatTelegram;
import checkNews.data.ChatTelegramHM;
import checkNews.search.InternetSearch;
import checkNews.support.CheckInternetConnection;
import checkNews.support.IOManager;
import checkNews.telegram.QueryManager;

/**
 * This class manage logic for the batch mode
 * @author Jose Javier Culebras
 * @version 1.0
 */ 

public class BatchMode {
	
	/*--------------------------*/
	/*    PUBLIC  METHODS       */
	/*--------------------------*/
	
	/**
	 * help menu. Description of the batch mode options
	 */
	public static void help() {
		
		System.out.println("DESCRIPTION:");
		System.out.println("	The \"Telegram-ProfilesCreator\" program is used for creating 'News broadcaster profiles' from Social Networks users (Telegram). ");
		System.out.println("	The java Classes derived from TDLib https://github.com/tdlib/  are under Boost Software License - Version 1.0 - August 17th, 2003");
		System.out.println("	Two types of profiles may be generated (USER | MESSAGE)");
		System.out.println("	USER: show all Telegram messages send by a Telegram user\r\n"
				+ "		\r\n"
				+ "		USER: \r\n"
				+ "		SENDER ID | FIRSTNAME | LASTNAME | USERNAME | FAKE | SCAM | VERIFIED | PREMIUM\r\n"
				+ "		9999999999 | Jose Javier | apellido | username  | false | false | false | false\r\n"
				+ "		LIST OF MESSAGES: \r\n"
				+ "		ID: DATE | MESSAGE ID | SENDER ID | CHAT ID | MENSAJE\r\n"
				+ "		1: 29/01/2024 09:14:23 | 6320816128 | 9999999999 | -1001248984999 | Hoy lunes bailamos  en la plaza mayor de 19 a 21.30 horas será el último bailé del año\r\n"
				+ "		2: 27/01/2024 16:48:16 | 6316621824 | 9999999999 | -1001248984999 | Mañana domingo debido al buen tiempo y para no coincidir con el pasea swing hemos decidido cancelar el evento\r\n"
				+ "		");	
	
		System.out.println("	MESSAGE: show all Telegram users who have sent the same message \r\n"
				+ "		\r\n"
				+ "		MESSAGE:\r\n"
				+ "		https://www.madforswing.es/evento/pasea-swing-11/\r\n"
				+ "		LIST OF USERS: \r\n"
				+ "		SENDER ID | FIRSTNAME | LASTNAME | USERNAME | FAKE | SCAM | VERIFIED | PREMIUM\r\n"
				+ "		1: 11111111 | Marta | GQ | Opti | false | false | false | false\r\n"
				+ "		2: 22222222 | Manu |  | jujuju | false | false | false | false\r\n"
				+ "		");	
		
		System.out.println("");	
		
		System.out.println("USAGE:");
		System.out.println("	[] | [-?] | [<options>] ");
		System.out.println("		[] GUI mode.");
		System.out.println("		[-?] help.");
		System.out.println("		[<options>]  Batch mode.");
		
		System.out.println("<options>");
		System.out.println("	-SI \"text string\" \"output file\", fetch news on the internet.");
		System.out.println("	-SIL \"input file\" \"output file\", read queries from a file and fetch news on the internet.");
		System.out.println("	-SRSS \"url RSS\" \"output file\", fetch news from a RSS service.");
		System.out.println("	-SRSSL \"RSS input file\" \"output file\", read RSS sources from a file and fetch news from a RSS service.");
		System.out.println("	-SM \"text string\" \"chad_id\" \"output file\", fetch messages from the specific \"chat id \" on Telegram.");
		System.out.println("	-SML \"input file\" \"output file\" , read News from a file and fetch messages from every chat the user belongs (till 100 messages for each chat).");
		System.out.println("	-SAM \"text string\" \"output file\", fetch messages from all user chats on Telegram  (till 100 messages).");
		System.out.println("	-SAML \"input file\" \"output file\", read News from a file and fetch Messages from all user chats on Telegram (till 100 messages).");
		System.out.println("EXAMPLES:");
		System.out.println("	-SI \"madrid breaking news\" \"c:\\temp\\news_madrid.json\"");
		System.out.println("	-SIL \"c:\\temp\\source.txt\" \"c:\\temp\\outputFile.json\"");
		System.out.println("	-SM \"https://www.uned.es/\" \"-1001248984999\" \".\\streamUned\\telegram\\messages_uned.json\"");
	}

	
	/**
	 * Method for doing a Google search, output fixed  to 25 results. Used on batch mode
	 * @param internetSearch
	 * @param ioManager
	 * @param query (the string to look for)
	 * @param path (the path for saving the output file)
	 */
	public static void searchInternetNews(InternetSearch internetSearch, IOManager ioManager, String query, String path ) {
		
		if (checkConnection() == false ) return; //Check Connection to the internet
		internetSearch.searchNewsByTitle(query);
		ioManager.writeNewsJsonFile(path);
	}
	
	
	/**
	 * Method for getting messages from each chats the user belongs from a file with a list of url
	 * @param queryManager
	 * @param ioManager
	 * @param sourcePath (the list of url to look for)
	 * @param path (the path for saving the output file)
	 */
	public static void searchMessagesList(QueryManager queryManager, IOManager ioManager, String sourcePath, String path ) {
		
		if (checkConnection() == false ) return; //Check Connection to the internet
		ArrayList<String> queryList = new ArrayList<String>();
		queryList = readSourcePath(sourcePath);
		
		queryManager.getChatList(999);
		ArrayList<ChatTelegram> chatList = new ArrayList<ChatTelegram>();
		chatList = ChatTelegramHM.getChatTelegramList();
		
		if (queryList.size() == 0) {
			
			System.out.println("WARNING: check source file"); 
			return;
		}
		
		for (String query : queryList) { //for each url
	        
			if (!(query.equals(""))) {
				
				for(ChatTelegram chat : chatList) { //look for on all chats the user belongs
					
					Long chatId = chat.getChatId();
					queryManager.searchChatMessages(query, chatId.toString());	
				}
			}
		}
			
		ioManager.writeMessageTelegramJsonFile(path);
	}
	
	
	/**
	 * Method for doing a Google search, output fixed  to 25 results. Used on batch mode
	 * @param internetSearch
	 * @param ioManager
	 * @param sourcePath (the list of queries  to look for)
	 * @param path (the path for saving the output file)
	 */
	public static void searchInternetNewsList(InternetSearch internetSearch, IOManager ioManager, String sourcePath, String path ) {
		
		if (checkConnection() == false ) return; //Check Connection to the internet
		ArrayList<String> queryList = new ArrayList<String>();
		queryList = readSourcePath(sourcePath);
		
		if (queryList.size() == 0) {
			
			System.out.println("WARNING: check source file"); 
			return;
		}
		
		for (String query : queryList) {
        
			if (!(query.equals(""))) {
				
				internetSearch.searchNewsByTitle(query);
			}
		}
		
		ioManager.writeNewsJsonFile(path);
	}
	
	
	/**
	 * Method for fetching News from a RSS source. Used on batch mode
	 * @param internetSearch
	 * @param ioManager
	 * @param query (url from the source)
	 * @param path
	 */
	public static void searchInternetNewsRSS(InternetSearch internetSearch, IOManager ioManager, String query, String path ) {
		
		if (checkConnection() == false ) return; //Check Connection to the internet
		internetSearch.searchNewsByRSS(query);
		ioManager.writeNewsJsonFile(path);
	}
	
	
	/**
	 * Method for getting messages from all chats which contains the search string . Used on batch mode
	 * The Telegrams API limit the output to 100 messages 
	 * https://core.telegram.org/tdlib/docs/classtd_1_1td__api_1_1search_chat_messages.html -> "The maximum number of messages to be returned; must be positive and can't be greater than 100."
	 * @param queryManager
	 * @param ioManager
	 * @param sourcePath (the list of News to look for)
	 * @param path 
	 */
	public static void searchAllMessagesList(QueryManager queryManager, IOManager ioManager, String sourcePath, String path ) {
		
		if (checkConnection() == false ) return; //Check Connection to the internet
		ArrayList<String> queryList = new ArrayList<String>();
		queryList = readSourcePath(sourcePath);
		
		if (queryList.size() == 0) {
			
			System.out.println("WARNING: check source file"); 
			return;
		}
		
		for (String query : queryList) {
	        
			if (!(query.equals(""))) {
				
				queryManager.searchAllChatMessages(query);
			}
			
		}
		
		ioManager.writeMessageTelegramJsonFile(path);
	}
	
	
	/**
	 * Method for fetching News from a RSS source. Used on batch mode
	 * @param internetSearch
	 * @param ioManager
	 * @param sourcePath (the list of RSS to look for)
	 * @param path (the path for saving the output file)
	 */
	public static void searchInternetNewsRSSList(InternetSearch internetSearch, IOManager ioManager, String sourcePath, String path ) {
		
		if (checkConnection() == false ) return; //Check Connection to the internet
		ArrayList<String> queryList = new ArrayList<String>();
		queryList = readSourcePath(sourcePath);
		
		if (queryList.size() == 0) {
			
			System.out.println("WARNING: check source file"); 
			return;
		}
		
		for (String query : queryList) {
	        
			if (!(query.equals(""))) {
				
				internetSearch.searchNewsByRSS(query);
			}
			
		}
		
		ioManager.writeNewsJsonFile(path);
	}
	
	
	/**
	 * Method for getting messages which contains the search string on a specific chat. Used on batch mode
	 * The Telegrams API limit the output to 100 messages 
	 * https://core.telegram.org/tdlib/docs/classtd_1_1td__api_1_1search_chat_messages.html -> "The maximum number of messages to be returned; must be positive and can't be greater than 100."
	 * @param queryManager
	 * @param ioManager
	 * @param query (search string)
	 * @param chat (chatId)
	 * @param path (the path for saving the output file)
	 */
	public static void searchMessages(QueryManager queryManager, IOManager ioManager, String query, String chat, String path ) {
		
		if (checkConnection() == false ) return; //Check Connection to the internet
		queryManager.searchChatMessages(query, chat);		
		ioManager.writeMessageTelegramJsonFile(path);
	}
	
	
	/**
	 * Method for getting messages from all chats which contains the search string . Used on batch mode
	 * The Telegrams API limit the output to 100 messages 
	 * https://core.telegram.org/tdlib/docs/classtd_1_1td__api_1_1search_chat_messages.html -> "The maximum number of messages to be returned; must be positive and can't be greater than 100."
	 * @param queryManager
	 * @param ioManager
	 * @param query (search string)
	 * @param path (the path for saving the output file)
	 */
	public static void searchAllMessages(QueryManager queryManager, IOManager ioManager, String query, String path ) {
		
		if (checkConnection() == false ) return; //Check Connection to the internet
		queryManager.searchAllChatMessages(query);		
		ioManager.writeMessageTelegramJsonFile(path);
	}
	
	
	/**
	 * method for reading an input source file 
	 * @param sourcePath (the path of the file)
	 * @return A list of queries
	 */
	private static ArrayList<String> readSourcePath(String sourcePath) {
		
		//if (checkConnection() == false ) return; //Check Connection to the internet
		ArrayList<String> queryList  = new ArrayList<String>();
		
		try {
			
			BufferedReader reader = new BufferedReader( new FileReader(sourcePath));
			String line = reader.readLine();
			
			while (line != null) {
				queryList.add(line);
	            line = reader.readLine();
	        }
			
			reader.close();
			
		} catch (FileNotFoundException e) {
			
			System.out.println("ERROR: check file " + sourcePath);    
		} catch (IOException e) {
			
			System.out.println("ERROR: file reading error"); 
		}
				
		return queryList;
	}
	
	
	/**
	 * Method for checking the connection to internet
	 * @return the status of the connection
	 */
	private static boolean checkConnection(){
		
		if (CheckInternetConnection.checkConnection() == false ) { //Check Connection to the internet
			System.out.println ("ERROR: no internet connection");
			return false;
		} else {
			return true;
		}
	}
		
}
