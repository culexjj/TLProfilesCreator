package checkNews.support;

import java.io.IOException;


/**
 * This class open the Telegram desktop
 * @author Jose Javier Culebras
 * @version 1.0
 */ 

public class OpenTelegramDesktop {
	
	/**
	 * Method for opening the default browser on the url passed like parameter
	 * @param chatId (to be opened)
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public static void openTelegramDesktop(String chatId) throws IOException{
		
		String dataFolder = System.getenv("APPDATA");
		try {
			Runtime.getRuntime().exec(dataFolder + "\\Telegram Desktop\\telegram.exe -- tg://privatepost?channel="+chatId);
		} catch (IOException e) {
			
			System.out.println("WARNING : Telegram Desktop not installed in " + dataFolder);
		}
	}

}
