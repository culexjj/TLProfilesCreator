package checkNews.data;



/**
 * this class is for sorting chat by name
 * @author Jose Javier Culebras
 * @version 1.0
 * 
 */
public class ChatTelegramComparator  implements java.util.Comparator<ChatTelegram> {

	
	/**
	* Order chat by alphabetical order
	*/
	public int compare(ChatTelegram a, ChatTelegram b) {
        
		String chatName1 = "";
		String chatName2 = "";
		
		chatName1 = a.getChatName();		
		chatName2 = b.getChatName();
		
		return chatName1.compareTo(chatName2);
    }
}
