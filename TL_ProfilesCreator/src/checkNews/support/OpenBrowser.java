package checkNews.support;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * This class open the default system browser
 * @author Jose Javier Culebras
 * @version 1.0
 */ 
public class OpenBrowser {
	
	/**
	 * Method for opening the default browser on the url passed like parameter
	 * @param url (to be opened)
	 * @throws IOException
	 */
	public static void openBrowser(String url) throws IOException{
		
		Desktop desktop = java.awt.Desktop.getDesktop();
		
		try {
			
			URI source = new URI(url);
			desktop.browse(source);
		} catch (URISyntaxException e) {
			
			System.out.println("ERROR: URL format incorrect");
		}	
	}
	
}
