package checkNews.telegram;

/**
 * Class for managing connection to Telegram. This class is final, so it can't be extended
 * @author Jose Javier Culebras
 * @version 1.0
 * */

public final class ConnectionManager {
	
	/*--------------------------*/
	/*          BUILDERS        */
	/*--------------------------*/
	
	/**
	 * Default builder
	 */
	public ConnectionManager () {
		
	}
	
	
	/*--------------------------*/
	/*    PUBLIC   METHODS      */
	/*--------------------------*/
		
	/**
	  * Method for connecting to Telegram
	  * @throws InterruptedException
	  */
	
	 public void connection() throws InterruptedException {
	       
		Telegram.connection();
	 }
	    

	/**
	* Method for disconnecting the session from Telegram
	 */
	    
	    public void quit() {
	    	
	    	Telegram.quit();
	    }
	      
}
