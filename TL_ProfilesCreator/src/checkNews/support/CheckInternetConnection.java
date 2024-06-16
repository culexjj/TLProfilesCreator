package checkNews.support;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

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
		
		//Process process = null;
		//int test = 1;//False by default
		
		try {
			//process = java.lang.Runtime.getRuntime().exec("ping www.uned.es");
			//process = java.lang.Runtime.getRuntime().exec("ping -n 1 8.8.8.8");
			
			URL url = new URL("https://www.google.com");
	        URLConnection connection = url.openConnection();
	        connection.connect();
	        //System.out.println("INTERNET OK  ");
		    return true; //Connection OK 
			
		} catch (IOException e) {
			//System.out.println("INTERNET KO  ");
	        return false; //Connection KO //e.printStackTrace();
		} 
	    
		/*try {
			test = process.waitFor();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		} 
		
		
		System.out.println("TEST : " + test);
		
	    if (test == 0) { 
	    	System.out.println("INTERNET OK  ");
	        return true; //Connection OK 
	        
	    } 
	    else { 
	    	System.out.println("INTERNET KO  ");
	        return false; //Connection KO 
	    } 	*/
	    
	}
	
}
