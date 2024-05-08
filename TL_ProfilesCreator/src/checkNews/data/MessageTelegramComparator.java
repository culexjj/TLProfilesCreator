package checkNews.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * this class is for sorting Message from Telegram by date
 * @author Jose Javier Culebras
 * @version 1.0
 * 
 */

public class MessageTelegramComparator implements java.util.Comparator<MessageTelegram>{
	
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	
	/**
	* messages reverse chronological order
	*/
	public int compare(MessageTelegram a, MessageTelegram b) {
        
		Date date1 = null;
		Date date2 = null;
		
		try {
			
			date1 = dateFormat.parse(a.getDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		try {
			
			date2 = dateFormat.parse(b.getDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return date2.compareTo(date1);
    }
}
