package checkNews.support;

import java.io.IOException;

/**
 * Class for checking the internet connection. 
 * @author Jose Javier Culebras
 * @version 1.0
 * 
 */
public  class CheckInternetConnection {
	
	/*--------------------------*/
	/*    PUBLIC   METHODS      */
	/*--------------------------*/
	
	/**
	 * Method for checking the internet connection
	 * @return true if the connection is successful
	 */
	@SuppressWarnings("deprecation")
	public static boolean checkConnection() {
		
		Process process = null;
		int test = 1;//False by default
		
		try {
			process = java.lang.Runtime.getRuntime().exec("ping www.uned.es");
		} catch (IOException e) {
			e.printStackTrace();
		} 
	    
		try {
			test = process.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		
	    if (test == 0) { 
	        return true; //Connection OK 
	    } 
	    else { 
	        return false; //Connection KO 
	    } 	  
	}
	
}
