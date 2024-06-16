package checkNews.gui;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.SwingWorker;
import java.awt.event.ActionEvent;
import checkNews.data.ChatTelegramHM;
import checkNews.data.MessageTelegramHM;
import checkNews.data.NewsHM;
import checkNews.search.InternetSearch;
import checkNews.support.CheckInternetConnection;
import checkNews.support.IOManager;
import checkNews.support.OpenBrowser;
import checkNews.support.OpenTelegramDesktop;
import checkNews.telegram.QueryManager;


/**
 * This class manage interactions bewteen view and model
 * @author Jose Javier Culebras
 * @version 1.0
 * 
 */ 

public class Controller implements ActionListener {
	
	/*--------------------------*/
	/*  VARIABLES DECLARATION	*/
	/*--------------------------*/
	
	private QueryManager queryManager;		
	private IOManager ioManager;
	private InternetSearch internetSearch;	
	private View view;
	
	
	/*--------------------------*/
	/*          BUILDERS        */
	/*--------------------------*/
	
	public Controller(QueryManager queryManager, IOManager ioManager, InternetSearch internetSearch,  View view) {
		
		super();
		this.queryManager = queryManager;
		this.ioManager = ioManager;
		this.internetSearch = internetSearch;
		this.view = view;
	}

	
	/**
	 * Method for set the action when an event is fire on view
	 */
	public void actionPerformed(ActionEvent evento) {
		
		String inputForm = ""; //Parameter number 1 for querying the model
		String rightButtonValue = "";
		
		if ( evento.getActionCommand().equals("DELETENEWS") ) {			
			int value = view.askConfirmation("This operation can't be undone. Do you want to continue?","WARNING");			
			if (value == 0) { //If Yes, overwrite				
				view.deleteAllNews();
				NewsHM.clearHM();
			} else {
				return;
			}
		} else if ( evento.getActionCommand().equals("SHOWNEWS") ) {			
			view.showNews();
		} else if ( evento.getActionCommand().equals("SHOWNEWSFILTER") ) {			
			view.showNewsFilter();
		} else if ( evento.getActionCommand().equals("SEARCHNEWS") ) {
			if (checkConnection() == false ) return; //Check Connection to the internet
			inputForm = view.inputOneParameter("Search String");	
			if (inputForm == null || inputForm.equals("")) {
				return; //if user cancel or close the dialog box, return
			}
			getNews(inputForm);
			view.updateModelNews();
		} else if ( evento.getActionCommand().equals("SEARCHNEWSRSS") ) {
			if (checkConnection() == false ) return; //Check Connection to the internet
			inputForm = view.inputOneParameter("RSS");			
			if (inputForm == null || inputForm.equals("")) {		
				return; //if user cancel or close the dialog box, return
			}	
			getNewsRSS(inputForm);
			view.updateModelNews();
		} else if ( evento.getActionCommand().equals("SEARCHNEWSRSSFILTER") ) {			
			if (checkConnection() == false ) return; //Check Connection to the internet
			String[] values = view.inputTwoParameter("RSS","Filter");			
			if (values[0].equals("cancelByUser")) {	    		
	    		return; //if cancel or close by user, return	
	    	}			
			if (values[0].equals("") || values[1].equals("")) {	    		
				view.showInformation("A parameter is missing");
	    		return; //if no url or filter, return	
	    	}		
			getNewsRSSFilter(values[0], values[1]);
			view.updateModelNews();		
		} else if ( evento.getActionCommand().equals("SAVENEWSTOFILE") ) {			
			String rootPath = (".\\datafiles");		
			String filePath = view.fileSaveDialog(rootPath, "noProposal" );  			
			saveNewsToAFile(filePath); 
		} else if ( evento.getActionCommand().equals("SHOWMESSAGES") ) {			
			view.showMessages();
		} else if ( evento.getActionCommand().equals("SHOWMESSAGESFILTER") ) {			
			view.showMessagesFilter();
		} else if ( evento.getActionCommand().equals("SHOWCHATS") ) {
			if (checkConnection() == false ) return; //Check Connection to the internet
			getChatList();
			view.getChatList();	
		} else if ( evento.getActionCommand().equals("SEARCHPUBLICCHATS") ) {
			if (checkConnection() == false ) return; //Check Connection to the internet
			inputForm = view.inputOneParameter("Chat name to search");			
			if (inputForm == null || inputForm.equals("")) { //If cancel or no answer			
				return;
			} 						
			searchPublicGroups(inputForm);					
			view.updateModelChatTelegram();
		} else if ( evento.getActionCommand().equals("JOINTOCHAT") ) {
			if (checkConnection() == false ) return; //Check Connection to the internet
			inputForm = view.inputOneParameter("Chat Id");				
			if (inputForm == null || inputForm.equals("")) { //If cancel or no answer					
				return;
			} 				
			addUserToChat(inputForm);	
			getChatList();
			view.getChatList();
		} else if ( evento.getActionCommand().equals("SEARCHMESSAGES") ) {
			if (checkConnection() == false ) return; //Check Connection to the internet
			/*if (CheckInternetConnection.checkConnection() == false ) { //Check Connection to the internet
				System.out.println ("ERROR: no internet connection");
				return;
			}*/
			String[] values = view.inputTwoParameter("Search string","ChatId");			
			if (values[0].equals("cancelByUser")) {	    		
	    		return; //if cancel or close by user, return	
	    	}			
			if (values[0].equals("") || values[1].equals("")) {	    		
				view.showInformation("A parameter is missing");
	    		return; //if no search string  or chatId, return	
	    	}			
			try {		    	
		        @SuppressWarnings("unused")
				Long number = Long.parseLong(values[1]);
		    } catch (NumberFormatException e) {		    	
		    	System.out.println ("WARNING : chatId incorrect");
		        return ;
		    }			
			searchChatMessages(values[0], values[1]);
			view.updateModelMessage();
		} else if ( evento.getActionCommand().equals("SEARCHMESSAGESALLCHAT") ) {
			if (checkConnection() == false ) return; //Check Connection to the internet
			getChatList(); //Needed for set the right name of the chat			
			if (rightButtonValue.equals("")) { //Standard search				
				inputForm = view.inputOneParameter("Search String");
				if (inputForm == null || inputForm.equals("")) { //If cancel or no answer			
					return; 
				}				
				searchAllChatMessages(inputForm);				
			} 
			view.updateModelMessage();
		} else if ( evento.getActionCommand().equals("SAVEMESSAGESTOFILE") ) {			
			String rootPath = (".\\datafiles");		
			String filePath = view.fileSaveDialog(rootPath, "noProposal" );  			
			saveMessagesToAFile(filePath); 
		} else if ( evento.getActionCommand().equals("DELETEMESSAGES") ) {			
			int value = view.askConfirmation("This operation can't be undone. Do you want to continue?","WARNING");			
			if (value == 0) { //If Yes, overwrite				
				view.deleteAllMessageTelegram();
				MessageTelegramHM.clearHM();
			} else {
				return;
			}
		} else if ( evento.getActionCommand().equals("DELETECHATS") ) {			
			view.deleteAllChats();
		} else if ( evento.getActionCommand().equals("SHOWDATASETS") ) {			
			view.showDataSets();
		} else if ( evento.getActionCommand().equals("SHOWDATASETSFILTERA") ) {			
			view.showDataSetsFilterA();
		} else if ( evento.getActionCommand().equals("SHOWDATASETSFILTERB") ) {			
			view.showDataSetsFilterB();
		} else if ( evento.getActionCommand().equals("CREATEDATASET") ) {
			if (checkConnection() == false ) return; //Check Connection to the internet
			String[] values = {"","",""};
			values = view.createDataSet();			
			if (values[0].equals(".") && values[2].equals(".") && values[0].equals(".")) {				
				return;
			}			
			if (values[2].equals("A")) {				
				createDataSetUser(values);			
			} else {				
				CreateDataSetMessageTask createDataSet = new CreateDataSetMessageTask (values); //This task is execute in the background 
				createDataSet.execute();
			}			
			view.showDataSets();
		} else if ( evento.getActionCommand().equals("DELETEDATASETS") ) {			
			view.deleteAllDataSet();
		} else if ( evento.getActionCommand().equals("LOADNEWSFILE") ) {			
			String rootPath = (".\\datafiles");
			String filePath = view.fileOpenDialog(rootPath);
			loadNewsfile(filePath);			
			view.loadNewsFile();
		} else if ( evento.getActionCommand().equals("LOADMESSAGESFILE") ) {			
			getChatList();			
			String rootPath = (".\\datafiles");
			String filePath = view.fileOpenDialog(rootPath);
			loadMessagesFile(filePath);
			view.loadMessagesFile();			
		} else if ( evento.getActionCommand().equals("SHOWMESSAGESCONSOLE") ) {			
			view.startConsoleOutput();
		} else if ( evento.getActionCommand().equals("STATISTICALDATA") ) {			
			view.startTotalFigures();
		} else if ( evento.getActionCommand().equals("RSSLIST") ) {			
			view.startRSS();
		} else if ( evento.getActionCommand().equals("CHATSLIST") ) {			
			view.startChatList();
		} else if ( evento.getActionCommand().equals("ABOUT") ) {			
			view.startHelp();
		} else if ( evento.getActionCommand().equals("OPENBROWSER") ) {
			if (checkConnection() == false ) return; //Check Connection to the internet
			//view.openBrowser();
			ArrayList<String> newsSeleccion = new ArrayList<String>();
			newsSeleccion = view.openBrowser();
			openBrowser(newsSeleccion);
		} else if ( evento.getActionCommand().equals("OPENTELEGRAMWEB") ) {
			if (checkConnection() == false ) return; //Check Connection to the internet
			String[] values = view.openTelegramWeb();
			if (values == null) return;
			if (values[0].equals("") && values[1].equals("")) { //if no chatId or msgId, return
				return;
			}
			openTelegramWeb(values); 			
		} else if ( evento.getActionCommand().equals("SHOWCHATSFILTER") ) {			
			getChatList();
			view.showChatsFilter();
		} else if ( evento.getActionCommand().equals("MESSAGESBYTYPE") ) {			
			view.startMessagesByType();
		} else if ( evento.getActionCommand().equals("MESSAGESBYHOUR") ) {			
			view.startMessagesByHour();
		} else if ( evento.getActionCommand().equals("NEWSBYTYPE") ) {			
			view.startNewsByType();
		} else if ( evento.getActionCommand().equals("MESSAGESBYDAY") ) {			
			view.startMessagesByDay();
		} else if ( evento.getActionCommand().equals("SEARCHMESSAGESALLCHATRB") ) {
			if (checkConnection() == false ) return; //Check Connection to the internet
			rightButtonValue = view.getRightButtonValue();
			getChatList(); //Needed for set the right name of the chat			
			if (view.GetNewsMultipleRowSeleccionSize() <=1) {				
				searchAllChatMessages(rightButtonValue);
				view.setRightButtonValue(); //Set value to "" for next action
			} else {			
				ArrayList<String> list = view.GetNewsMultipleRowSeleccion();
				SearchAllMessagesListTask searchList = new SearchAllMessagesListTask (list, queryManager); //This task is execute in the background 	  
				searchList.execute();					
				view.setNewsMultipleRowSeleccion(); //clear array for next action
				view.setRightButtonValue(); //Set value to "" for next action				
			}
		} else if ( evento.getActionCommand().equals("CREATEDATASETRB") ) {
			if (checkConnection() == false ) return; //Check Connection to the internet
			String[] values = {"","",""};
			values = view.createDataSetRB();			
			if (values[0].equals(".") && values[2].equals(".") && values[0].equals(".")) {				
				return;
			}			
			if (values[2].equals("A")) {				
				createDataSetUser(values);			
			} else {				
				CreateDataSetMessageTask createDataSet = new CreateDataSetMessageTask (values); //This task is execute in the background 
				createDataSet.execute();
			}			
			view.showDataSets();			
		} else if ( evento.getActionCommand().equals("JOINTOCHATRB") ) {
			if (checkConnection() == false ) return; //Check Connection to the internet
			rightButtonValue = view.getRightButtonValue();			
			addUserToChat(rightButtonValue);
			view.setRightButtonValue(); //Set value to "" for next action			
			getChatList();
			view.getChatList();
		}		 		
	}
	
	
	/*--------------------------*/
	/*    PRIVATE METHODS       */
	/*--------------------------*/
	
	/**
	 * Method for checking the connection to internet
	 * @return the status of the connection
	 */
	private boolean checkConnection(){
		
		if (CheckInternetConnection.checkConnection() == false ) { //Check Connection to the internet
			System.out.println ("ERROR: no internet connection");
			return false;
		} else {
			return true;
		}
	}
	
	
	/**
	 * Method for opening the local browser 
	 * @param newsArray (the url list)
	 */
	private void openBrowser(ArrayList<String> newsArray) {
		
		for (String url : newsArray) {
	        
			try {
				
				OpenBrowser.openBrowser(url);
			} catch (IOException e) {
				
				//e.printStackTrace();
			}
		}	
	}
	
	
	/**
	 * Method for getting the link for a message. If there is no info about the message the chat is open instead
	 * @param values (the chatId and the messageId)
	 */
	private void openTelegramWeb(String[] values) {
		
		String chatId = "";
		String messageId = "";
		String link = "";
		
		try {
			
			chatId = values[0];
			messageId = values[1];
			
		} catch (ArrayIndexOutOfBoundsException e) {
			
			//e.printStackTrace();
		}
		
		link = queryManager.getMessageLink(chatId, messageId);
		
		if (link == null || link.equals("")) { //if no Telegram link, open the chat
			try {
				OpenTelegramDesktop.openTelegramDesktop(chatId.substring(4));
				System.out.println ("INFO: message not found, opening chat");
			} catch (IOException e) {
				
				//e.printStackTrace();
			}
			
		} else {
			
			String[] linkArray = link.split("/");
			int arraySize = linkArray.length;
			
			try {
				//OpenBrowser.openBrowser(link);
				OpenTelegramDesktop.openTelegramDesktop(chatId.substring(4),linkArray[arraySize-1]);
			} catch (IOException e) {
				
				//e.printStackTrace();
			}	
		}		
	}
	
	
	/**
	 * Method for searching  news on the internet
	 * @param query
	 */
	private void getNews(String query) {
				
		internetSearch.searchNewsByTitle(query);
	}
	
	
	/**
	 * Method for fetching  News from a RSS url
	 * @param internetSearch
	 */
	private void getNewsRSS(String rss) {
		
		internetSearch.searchNewsByRSS(rss);
	}
	
	
	/**
	 * Method for writing a file of News
	 * @param filePath
	 */
	private void saveNewsToAFile(String filePath) {
		
		if (!(filePath.equals(""))) { //If not true, the  user not cancel or close the dialog box
				
			ioManager.writeNewsJsonFile(filePath); 
		}
	}
	
	
	/**
	 * Method for writing a file of Messages
	 * @param filePath
	 */
	private void saveMessagesToAFile(String filePath) {
		
		if (!(filePath.equals(""))) { //If not true, the  user not cancel or close the dialog box
				
			ioManager.writeMessageTelegramJsonFile(filePath);
		}
	}
	
	
	/**
	 * Method for fetching  News from a RSS url which match the filter (one word)
	 * @param internetSearch
	 */
	private void getNewsRSSFilter(String rss, String filter) {
		
		internetSearch.searchNewsByRSS(rss, filter);
	}
	
	
	/**
	 * Method for getting the list of chats 
	 * https://core.telegram.org/tdlib/docs/classtd_1_1td__api_1_1load_chats.html -> "The maximum number of chats to be loaded.
	 * For optimal performance, the number of loaded chats is chosen by TDLib and can be smaller than the specified limit,
	 * even if the end of the list is not reached."
	 * 
	 * @param queryManager
	 */
	private void getChatList() {
		
		queryManager.getChatList(999); //Number of chats to get. Set to 999 (see notes)
    }
	
	
	/**
	 * Method for searching public chats 
	 * @param queryManager
	 */
	private void searchPublicGroups(String name) {
		
		queryManager.searchPublicGroups(name);
	}
	
	
	/**
	 * Method for join to a public chat
	 * @param chatId
	 */	
	private void addUserToChat(String chatId) {
		
		boolean control = true;
		
		try {
			
			control = ChatTelegramHM.checkChat(Long.valueOf(chatId).longValue());
		} catch (NumberFormatException e) {
			
			System.out.println("WARNING: chatId incorrect");
			return;
		}
		
		if (control == false) { //If we are not member, we join to the chat
			
			queryManager.addUserToChat(chatId);		
		}
	}
	
		
	/**
	 * Method for getting messages which contains the search string on a specific chat
	 * @param query
	 * @param chatId
	 */
	private void searchChatMessages(String query, String chatId) {
		
		queryManager.searchChatMessages(query, chatId);
	}
	
	/**
	 * Method for getting messages which contains the search string on all chats
	 * @param query
	 */
	private void searchAllChatMessages(String query) {
		
		queryManager.searchAllChatMessages(query);
	}
	
	
	/**
	 * Method for creating a User Dataset
	 * @param values
	 */
	private void createDataSetUser(String[] values) {
		
		if (values[0].startsWith("-")) {
			
			System.out.println("WARNING: the selected user is a groupId, no data available ");
			return;
		}
		
		try {
			
			queryManager.getUser(Long.valueOf(values[0]).longValue());
		} catch (NumberFormatException e) {
			
			System.out.println("WARNING: senderId incorrect");
			return;
			
		}
		
		ioManager.writeDataSetUserJsonFile(Long.valueOf(values[0]).longValue(), values[1]);	
		
	}
	
	/**
	 * Method for creating a Message Dataset
	 * @param values
	 */
	private void createDataSetMessage(String[] values) {
		
		queryManager.getMessageInfo(values[0]);
		ioManager.writeDataSetMessageJsonFile(values[0], values[1]);
	}
	
	/**
	 * Method for reading a file of news
	 * @param filePath
	 */
	private void loadNewsfile(String filePath) {
		
		if (!(filePath.equals(""))) {
			
			ioManager.readNewsJsonFile(filePath);
		} 	
	}
	
	
	/**
	 * Method for reading a file of Messages
	 * @param filePath
	 */
	private void loadMessagesFile(String filePath) {
		
		if (!(filePath.equals(""))) {
			
			ioManager.readMessageTelegramJsonFile(filePath);
		} 	
	}

	/*--------------------------*/
	/*      INNER  CLASSES      */
	/*--------------------------*/
	
	/**
	 * Class for doing a messages list search in the background
	 */
	private class SearchAllMessagesListTask extends SwingWorker<Void, Void> {
        
		/*--------------------------*/
		/*  VARIABLES DECLARATION	*/
		/*--------------------------*/
		private ArrayList<String> NewsMultipleRowSeleccion;
		private QueryManager queryManager;
		
		
		/*--------------------------*/
		/*          BUILDERS        */
		/*--------------------------*/
		
		public SearchAllMessagesListTask (ArrayList<String> lista, QueryManager queryManager )  {
			this.NewsMultipleRowSeleccion = lista;
			this.queryManager = queryManager;
		}
        
		
		/*--------------------------*/
		/*        METHODS           */
		/*--------------------------*/
		
		/**
		 * Run the task in the background
		 */
		@Override
		protected Void  doInBackground() throws Exception {
		
			queryManager.searchAllMessagesList(NewsMultipleRowSeleccion);
			return null;
		}
		
		/**
		 *  This method is execute when the task in done.
		 */
		@Override 
        protected void done() {
			
            view.updateModelMessage();
        } 
	}
	
	
	/**
	 * Class for creating a messages dataset in the background
	 */
	private class CreateDataSetMessageTask extends SwingWorker<Void, Void> {
        
		/*--------------------------*/
		/*  VARIABLES DECLARATION	*/
		/*--------------------------*/
		private String[] values;
		
		
		/*--------------------------*/
		/*          BUILDERS        */
		/*--------------------------*/
		
		public CreateDataSetMessageTask (String[] values )  {
			
			this.values = values;
		}
        
		
		/*--------------------------*/
		/*        METHODS           */
		/*--------------------------*/
		
		/**
		 * Run the task in the background
		 */
		@Override
		protected Void  doInBackground() throws Exception {
		
			createDataSetMessage(values);
			return null;
		}
		
		/**
		 *  This method is execute when the task in done.
		 */
		@Override 
        protected void done() {
			
            view.showDataSets();
        } 
	}
	
}

