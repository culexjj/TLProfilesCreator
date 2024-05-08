package checkNews;

import java.awt.EventQueue;

import checkNews.api.BatchMode;
import checkNews.gui.Controller;
import checkNews.gui.View;
import checkNews.search.InternetSearch;
import checkNews.support.IOManager;
import checkNews.support.gui.SplashWindow;
import checkNews.telegram.ConnectionManager;
import checkNews.telegram.QueryManager;



/**
 * Main
 * @author Jose Javier Culebras
 * @version 1.0.
 * The "SocialNetworksProfileCreator" program is used for creating 'News broadcaster profiles' from Social Networks users (Telegram).
 */
public class ProfileCreator {

	public static void main(String[] args) {
		
		/*------------------------------------------*/
		/*  VARIABLES DECLARATION & INITIALIZATION	*/
		/*------------------------------------------*/						
		ConnectionManager connectionManager = new ConnectionManager(); //Used for BatchMode
		QueryManager queryManager = new QueryManager(); //Used for BatchMode		
		IOManager ioManager = new IOManager(); //Used for BatchMode
		InternetSearch internetSearch = new InternetSearch(); //Used for BatchMode
		View view = new View();
		
					
		/*** START ***/
		
		/* Telegram Connection */
		try {
			connectionManager.connection();
		} catch (InterruptedException e) {

			System.out.println("Connection refused.");
		}
		
		/* Check arguments */
		if (args.length != 0) {
			try {
				switch (args[0]) {
				
					case "-?": {
						
						BatchMode.help();
						System.exit(0);
						break;
					}
					case "-SI": {

						BatchMode.searchInternetNews(internetSearch, ioManager, args[1], args[2] );
						System.exit(0);
						break;
					}
					case "-SIL": {

						BatchMode.searchInternetNewsList(internetSearch, ioManager, args[1], args[2] );
						System.exit(0);
						break;
					}
					case "-SRSS": {
		
						BatchMode.searchInternetNewsRSS(internetSearch, ioManager, args[1], args[2] );
						System.exit(0);
						break;
					}
					case "-SRSSL": {
						
						BatchMode.searchInternetNewsRSSList(internetSearch, ioManager, args[1], args[2] );
						System.exit(0);
						break;
					}
					case "-SM": {
				
						BatchMode.searchMessages(queryManager, ioManager, args[1], args[2], args[3] );
						System.exit(0);
						break;
					}
					case "-SML": {
						
						BatchMode.searchMessagesList(queryManager, ioManager, args[1], args[2]);
						System.exit(0);
						break;
					}
					case "-SAM": {
						
						BatchMode.searchAllMessages(queryManager, ioManager, args[1], args[2] );
						System.exit(0);
						break;
					}
					case "-SAML": { //Search Messages on all chats
						
						BatchMode.searchAllMessagesList(queryManager, ioManager, args[1], args[2] );
						System.exit(0);
						break;
					}
					
					default:
				 
						System.out.println("WARNING: command not supported"); 
		                 System.exit(0);
		 				 break;			
				}
			}  catch (ArrayIndexOutOfBoundsException e) {
				
				System.out.println("WARNING: not enough arguments"); 
				System.exit(1);
			}
		}
		else {
			
			/* Start GUI */	
			Controller controller = new Controller(queryManager, ioManager, internetSearch, view);	
			view.setControl(controller);	
			new SplashWindow(); //Welcome window
			EventQueue.invokeLater(new Runnable() { public void run(){ view.start();} });
		}
	
	}
}
